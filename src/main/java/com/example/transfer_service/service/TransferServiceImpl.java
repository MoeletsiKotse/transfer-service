package com.example.transfer_service.service;

import com.example.transfer_service.config.util.Status;
import com.example.transfer_service.dto.BatchTransferRequest;
import com.example.transfer_service.dto.TransferResponse;
import com.ledger.openapi.ApiClient;
import com.ledger.openapi.ApiException;
import com.ledger.openapi.ApiResponse;
import com.ledger.openapi.api.LedgerControllerApi;
import com.ledger.openapi.model.LedgerDTO;
import com.ledger.openapi.model.TransferRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.ledger.openapi.model.LedgerDTO.StatusEnum.*;

@Service
@Slf4j
public class TransferServiceImpl implements TransferService {

    private static String AUTH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmciLCJpYXQiOjE3NTY4MDA0MjAsImV4cCI6MTc1NjgwNDAyMH0.hxzciDaYB7AI2JiQJ9AS0pIi2bnGsB5uzRsdJg4xjFw";

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final LedgerControllerApi ledgerControllerApi;

    @Autowired
    public TransferServiceImpl(ApiClient apiClient) {
        this.ledgerControllerApi = new LedgerControllerApi(apiClient);
    }

    @Override
    public TransferResponse transfers(TransferRequest request) {

        var transferId = UUID.randomUUID().toString();

        ApiResponse<List<LedgerDTO>> res = null;
        try {
            res = Objects.requireNonNull(this.ledgerControllerApi.ledgerTransferWithHttpInfo(
                    /*AUTH_TOKEN,*/
                    transferId, request
            ));
        } catch (ApiException e) {
            return new TransferResponse(transferId, Status.FAILURE.name());
        }

        if (res.getStatusCode() == 200) {
            return new TransferResponse(transferId, Status.SUCCESS.name());
        }

        return new TransferResponse(transferId, Status.FAILURE.name());

    }

    @Override
    public ResponseEntity<TransferResponse> getTransfer(String transferId) {

        ApiResponse<List<LedgerDTO>> res = null;
        try {
            res = this.ledgerControllerApi.getLedgerWithHttpInfo(transferId);
        } catch (ApiException e) {
            return ResponseEntity.ok(new TransferResponse(transferId, Status.FAILURE.name()));
        }

        if (res.getStatusCode() == 200) {
            var status = IN_PROGRESS.name();
            if (res.getData().stream().allMatch(r -> {
                assert r.getStatus() != null;
                return r.getStatus().equals(COMPLETED);
            })) {
                status = COMPLETED.name();
            } else if (res.getData().stream().allMatch(r -> {
                assert r.getStatus() != null;
                return r.getStatus().equals(NEW);
            })) {
                status = NEW.name();
            }
            return ResponseEntity.ok(new TransferResponse(transferId, status));
        }

        return ResponseEntity.ok(new TransferResponse(transferId, Status.FAILURE.name()));
    }

    @Override
    public List<TransferResponse> batchTransfer(BatchTransferRequest batchTransferRequest) throws ApiException {

        var completableFuture = batchTransferRequest.transferRequests().stream()
                .map(request -> CompletableFuture.supplyAsync(
                        () -> transfers(request), executorService
                )).toList();

        return completableFuture.stream().map(
                CompletableFuture::join
        ).toList();
    }
}

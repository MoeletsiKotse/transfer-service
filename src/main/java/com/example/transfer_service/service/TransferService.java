package com.example.transfer_service.service;

import com.example.transfer_service.dto.BatchTransferRequest;
import com.example.transfer_service.dto.TransferResponse;
import com.ledger.openapi.ApiException;
import com.ledger.openapi.model.TransferRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransferService {
    TransferResponse transfers(TransferRequest request) throws ApiException;

    ResponseEntity<TransferResponse> getTransfer(String transferId) throws ApiException;

    List<TransferResponse> batchTransfer(BatchTransferRequest batchTransferRequest) throws ApiException;
}

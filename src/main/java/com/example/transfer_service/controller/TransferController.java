package com.example.transfer_service.controller;

import com.example.transfer_service.dto.BatchTransferRequest;
import com.example.transfer_service.dto.TransferResponse;
import com.example.transfer_service.service.TransferService;
import com.ledger.openapi.ApiException;
import com.ledger.openapi.model.TransferRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transfer")
@CrossOrigin
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController( TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping()
    private ResponseEntity<TransferResponse> tranfers(@Valid TransferRequest request) throws ApiException {
        return ResponseEntity.ok(transferService.transfers(request));
    }

    @PostMapping("batch")
    private ResponseEntity<List<TransferResponse>> batchTransfer(@Valid BatchTransferRequest batchTransferRequest) throws ApiException {
        return ResponseEntity.ok(this.transferService.batchTransfer(batchTransferRequest));
    }

    @GetMapping("/{id}")
    private ResponseEntity<TransferResponse> getTransfer(@PathVariable String id) throws ApiException {
        return this.transferService.getTransfer(id);
    }


}

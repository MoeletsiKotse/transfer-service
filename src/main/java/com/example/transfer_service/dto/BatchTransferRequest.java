package com.example.transfer_service.dto;

import com.ledger.openapi.model.TransferRequest;

import java.util.List;

public record BatchTransferRequest(List<TransferRequest> transferRequests) {
}

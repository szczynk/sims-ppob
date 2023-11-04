package com.szczynk.simsppob.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szczynk.simsppob.model.request.PaymentRequest;
import com.szczynk.simsppob.model.request.TopupRequest;
import com.szczynk.simsppob.model.response.BalanceResponse;
import com.szczynk.simsppob.model.response.PaymentResponse;
import com.szczynk.simsppob.model.response.TransactionResponse;
import com.szczynk.simsppob.model.response.WebResponse;
import com.szczynk.simsppob.service.TransactionService;

import jakarta.validation.Valid;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/balance")
    public WebResponse<BalanceResponse> getBalance(@AuthenticationPrincipal UserDetails userDetails) {

        // using email
        BalanceResponse response = transactionService.getBalance(userDetails.getUsername());

        return WebResponse.<BalanceResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @PostMapping("/topup")
    public WebResponse<BalanceResponse> topup(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TopupRequest request) {

        // using email
        BalanceResponse response = transactionService.topup(
                userDetails.getUsername(),
                request);

        return WebResponse.<BalanceResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @PostMapping("/transaction")
    public WebResponse<PaymentResponse> payment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PaymentRequest request) {

        // using email
        PaymentResponse response = transactionService.payment(
                userDetails.getUsername(),
                request.getServiceCode());

        return WebResponse.<PaymentResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @GetMapping("/transaction/history")
    public WebResponse<List<TransactionResponse>> getAllTransactions(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> limit) {

        // using email
        List<TransactionResponse> response = transactionService.getAllTransactions(
                userDetails.getUsername(), page, limit);

        return WebResponse.<List<TransactionResponse>>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }
}

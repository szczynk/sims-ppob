package com.szczynk.simsppob.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.szczynk.simsppob.exception.BadRequest;
import com.szczynk.simsppob.exception.ResourceNotFound;
import com.szczynk.simsppob.model.Balance;
import com.szczynk.simsppob.model.Transaction;
import com.szczynk.simsppob.model.User;
import com.szczynk.simsppob.model.request.TopupRequest;
import com.szczynk.simsppob.model.response.BalanceResponse;
import com.szczynk.simsppob.model.response.PaymentResponse;
import com.szczynk.simsppob.model.response.TransactionResponse;
import com.szczynk.simsppob.repository.BalanceRepository;
import com.szczynk.simsppob.repository.ServiceRepository;
import com.szczynk.simsppob.repository.TransactionRepository;
import com.szczynk.simsppob.repository.UserRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final BalanceRepository balanceRepository;
    private final TransactionRepository transactionRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public TransactionService(
            BalanceRepository balanceRepository,
            TransactionRepository transactionRepository,
            ServiceRepository serviceRepository,
            UserRepository userRepository) {
        this.balanceRepository = balanceRepository;
        this.transactionRepository = transactionRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    public BalanceResponse getBalance(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("user"));

        Balance balance = balanceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFound("balance"));

        return BalanceResponse
                .builder()
                .balance(balance.getBalanceAmount())
                .build();
    }

    @Transactional
    public BalanceResponse topup(String email, TopupRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("user"));

        Balance balance = balanceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFound("balance"));

        Long newBalance = balance.getBalanceAmount() + request.getTopUpAmount();
        balance.setBalanceAmount(newBalance);

        balanceRepository.save(balance);

        String desc = "Top Up Balance";

        if (request.getDescription() != null) {
            desc = request.getDescription();
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionType("TOPUP");
        newTransaction.setDescription(desc);
        newTransaction.setTotalAmount(request.getTopUpAmount());
        newTransaction.setCreatedOn(new Date());
        newTransaction.setUserId(user.getId());

        transactionRepository.save(newTransaction);

        return BalanceResponse
                .builder()
                .balance(balance.getBalanceAmount())
                .build();
    }

    @Transactional
    public PaymentResponse payment(String email, String serviceCode) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("user"));

        Balance balance = balanceRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFound("balance"));

        com.szczynk.simsppob.model.Service service = serviceRepository.findById(serviceCode)
                .orElseThrow(() -> new ResourceNotFound("balance"));

        if (balance.getBalanceAmount() < service.getServiceTariff()) {
            throw new BadRequest("Balance Kurang");
        }

        Long newBalance = balance.getBalanceAmount() - service.getServiceTariff();
        balance.setBalanceAmount(newBalance);

        balanceRepository.save(balance);

        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionType("PAYMENT");
        newTransaction.setDescription("Pembayaran " + service.getServiceName());
        newTransaction.setTotalAmount(service.getServiceTariff());
        newTransaction.setCreatedOn(new Date());
        newTransaction.setServiceCode(service.getServiceCode());
        newTransaction.setUserId(user.getId());

        transactionRepository.save(newTransaction);

        return PaymentResponse
                .builder()
                .invoiceNumber(newTransaction.getInvoiceNumber())
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .transactionType(newTransaction.getTransactionType())
                .totalAmount(newTransaction.getTotalAmount())
                .createdOn(newTransaction.getCreatedOn())
                .build();

    }

    public List<TransactionResponse> getAllTransactions(
            String email,
            Optional<Integer> page,
            Optional<Integer> limit) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("user"));

        int pageSize = limit.orElse(0);
        int pageNumber = page.orElse(0);

        List<TransactionResponse> responses = new ArrayList<>();

        if (pageSize == 0) {
            Iterable<Transaction> transactions = this.transactionRepository.findByUserId(user.getId());

            transactions.forEach(
                    tx -> responses.add(new TransactionResponse(
                            tx.getInvoiceNumber(),
                            tx.getTransactionType(),
                            tx.getDescription(),
                            tx.getTotalAmount(),
                            tx.getCreatedOn())));

            return responses;
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Transaction> transactions = this.transactionRepository.findByUserId(user.getId(), pageable);

        transactions.forEach(
                tx -> responses.add(new TransactionResponse(
                        tx.getInvoiceNumber(),
                        tx.getTransactionType(),
                        tx.getDescription(),
                        tx.getTotalAmount(),
                        tx.getCreatedOn())));

        return responses;
    }

}

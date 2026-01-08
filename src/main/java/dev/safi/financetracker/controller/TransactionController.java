package dev.safi.financetracker.controller;

import dev.safi.financetracker.dto.ApiResponse;
import dev.safi.financetracker.model.Transaction;
import dev.safi.financetracker.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<Transaction>> getAllTransactions(@RequestParam(required = false) String type) {

        if(type != null) {
            return new ApiResponse<>(
                    "success",
                    "Transactions fetched successfully",
                    service.getTransactionsByType(type)
        );
        }

        return new ApiResponse<>(
                "success",
                "Transactions fetched successfully",
                service.getAllTransactions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> getTransactionById(@PathVariable int id) {
        return service.getTransactionById(id)
                .map(tx -> ResponseEntity.ok(
                        new ApiResponse<>("success", "Transaction found", tx)
                ))
                .orElse(ResponseEntity.status(404).body(
                        new ApiResponse<>("error", "Transaction not found", null)
                ));
    }

    @PostMapping
    public ApiResponse<Transaction> addTransaction(@RequestBody Transaction transaction) {
        return new ApiResponse<>(
                "success",
                "Transaction added successfully",
                service.addTransaction(transaction)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTransaction(@PathVariable int id) {

        boolean deleted = service.deleteTransaction(id);

        if(deleted) {
            return new ApiResponse<>("success", "Transaction deleted", null);
        }
        return new ApiResponse<>("error", "Transaction not found", null);
    }
}

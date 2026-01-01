package dev.safi.financetracker.controller;

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
    public List<Transaction> getAllTransactions(@RequestParam(required = false) String type) {

        if(type != null) {
            return service.getTransactionsByType(type);
        }

        return service.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        return service.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return service.addTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable int id) {
        boolean deleted = service.deleteTransaction(id);
        if(deleted) {
            return ResponseEntity.ok("Transaction deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}

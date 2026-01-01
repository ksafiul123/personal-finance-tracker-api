package dev.safi.financetracker.service;

import dev.safi.financetracker.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public Optional<Transaction> getTransactionById(int id) {
        return transactions.stream()
                .filter(t -> t.getId() == id).
                findFirst();
    }

    public Transaction addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    public boolean deleteTransaction(int id){
        return transactions.removeIf(t -> t.getId() == id);
    }

    public List<Transaction> getTransactionsByType(String type) {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }
}


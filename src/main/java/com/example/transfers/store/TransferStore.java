package com.example.transfers.store;

import com.example.transfers.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransferStore {
    private final List<Transfer> unused = new ArrayList<>();

    public void addTransfer(Transfer tr) {
        unused.add(tr);
    }

    public List<Transfer> getTransfers() {
        return unused;
    }

    public void removeTransfer(Transfer tr) {
        unused.remove(tr);
    }
}

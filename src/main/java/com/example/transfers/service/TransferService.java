package com.example.transfers.service;
import com.example.transfers.model.Transfer;
import com.example.transfers.store.TransferStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService {
    private  TransferStore transferStore;
    public Result calculateOptimalTransfers(List<Transfer> availableTransfers, int maxWeight, int maxBoxWeight) {
        if (maxWeight < 0 || maxBoxWeight < 0) {
            throw new IllegalArgumentException("Maximum weight cant be negative");
        }
        int n = availableTransfers.size();
        int[][] dp = new int[n + 1][maxBoxWeight + 1];

        for (int i = 1; i <= n; i++) {
            Transfer transfer = availableTransfers.get(i - 1);
            for (int w = 0; w <= maxBoxWeight; w++) {
                if (transfer.getWeight() <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - transfer.getWeight()] + transfer.getCost());
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        List<Transfer> selectedTransfers = new ArrayList<>();
        int totalWeight = 0, totalCost = dp[n][maxBoxWeight];
        for (int i = n, w = maxBoxWeight; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                Transfer transfer = availableTransfers.get(i - 1);
                selectedTransfers.add(transfer);
                w -= transfer.getWeight();
                totalWeight += transfer.getWeight();
            }
            else {
                transferStore.addTransfer(availableTransfers.get(i - 1));
            }
        }
        if(maxWeight - maxBoxWeight >= 0) {
            maxWeight -= maxBoxWeight;
            Result newRest = calculateOptimalTransfers(transferStore.getTransfers(), maxWeight, maxBoxWeight);
            if(!newRest.getSelectedTransfers().isEmpty()) {
                for(Transfer tr : newRest.getSelectedTransfers()) {
                    transferStore.removeTransfer(tr);
                }
                selectedTransfers.addAll(newRest.getSelectedTransfers());
                totalWeight += newRest.getTotalWeight();
                totalCost += newRest.getTotalCost();
            }
        }

        return new Result(selectedTransfers, totalCost, totalWeight);
    }

    public static class Result {
        private final List<Transfer> selectedTransfers;
        private final int totalCost;
        private final int totalWeight;
        public Result(List<Transfer> selectedTransfers, int totalCost, int totalWeight) {
            this.selectedTransfers = selectedTransfers;
            this.totalCost = totalCost;
            this.totalWeight = totalWeight;
        }
        public List<Transfer> getSelectedTransfers() {
            return selectedTransfers;
        }
        public int getTotalCost() {
            return totalCost;
        }
        public int getTotalWeight() {
            return totalWeight;
        }
    }
}

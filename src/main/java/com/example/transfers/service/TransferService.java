package com.example.transfers.service;
import com.example.transfers.model.Transfer;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService {

    public Result calculateOptimalTransfers(List<Transfer> availableTransfers, int maxWeight) {
        if (maxWeight < 0) {
            throw new IllegalArgumentException("Maximum weight cant be negative");
        }
        int n = availableTransfers.size();
        int[][] dp = new int[n + 1][maxWeight + 1];

        for (int i = 1; i <= n; i++) {
            Transfer transfer = availableTransfers.get(i - 1);
            for (int w = 0; w <= maxWeight; w++) {
                if (transfer.getWeight() <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - transfer.getWeight()] + transfer.getCost());
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        List<Transfer> selectedTransfers = new ArrayList<>();
        int totalWeight = 0, totalCost = dp[n][maxWeight];
        for (int i = n, w = maxWeight; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                Transfer transfer = availableTransfers.get(i - 1);
                selectedTransfers.add(transfer);
                w -= transfer.getWeight();
                totalWeight += transfer.getWeight();
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

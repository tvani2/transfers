package com.example.transfers.controller;
import com.example.transfers.model.Transfer;
import com.example.transfers.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/calculate")
    public TransferService.Result calculateTransfers(@RequestBody Request request) {
        return transferService.calculateOptimalTransfers(request.getAvailableTransfers(), request.getMaxWeight(), request.getBoxWeight());
    }

    public static class Request {
        private int maxWeight, boxWeight;
        private List<Transfer> availableTransfers;

        public int getMaxWeight() {
            return maxWeight;
        }

        public int getBoxWeight() { return boxWeight; }

        public List<Transfer> getAvailableTransfers() {
            return availableTransfers;
        }

    }
}
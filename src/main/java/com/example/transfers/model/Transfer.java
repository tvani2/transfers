package com.example.transfers.model;

public class Transfer {
    private int weight;
    private int cost;

    public Transfer(int weight, int cost) {
        this.weight = weight;
        this.cost = cost;
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
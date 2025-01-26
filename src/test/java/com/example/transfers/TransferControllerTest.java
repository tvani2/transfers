package com.example.transfers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testNormalCase() throws Exception {
        String requestBody = """
            {
                "maxWeight": 15,
                "availableTransfers": [
                    { "weight": 5, "cost": 10 },
                    { "weight": 10, "cost": 20 },
                    { "weight": 3, "cost": 5 },
                    { "weight": 8, "cost": 15 }
                ]
            }
        """;

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(30))
                .andExpect(jsonPath("$.totalWeight").value(15))
                .andExpect(jsonPath("$.selectedTransfers.length()").value(2));
    }

    @Test
    public void testEmptyList() throws Exception {
        String requestBody = """
            {
                "maxWeight": 15,
                "availableTransfers": []
            }
        """;

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(0))
                .andExpect(jsonPath("$.totalWeight").value(0))
                .andExpect(jsonPath("$.selectedTransfers.length()").value(0));
    }

    @Test
    public void testZeroCapacity() throws Exception {
        String requestBody = """
            {
                "maxWeight": 0,
                "availableTransfers": [
                    { "weight": 5, "cost": 10 },
                    { "weight": 7, "cost": 14 }
                ]
            }
        """;

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(0))
                .andExpect(jsonPath("$.totalWeight").value(0))
                .andExpect(jsonPath("$.selectedTransfers.length()").value(0));
    }

    @Test
    public void testNoTransferFits() throws Exception {
        String requestBody = """
            {
                "maxWeight": 10,
                "availableTransfers": [
                    { "weight": 20, "cost": 40 },
                    { "weight": 15, "cost": 30 }
                ]
            }
        """;

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(0))
                .andExpect(jsonPath("$.totalWeight").value(0))
                .andExpect(jsonPath("$.selectedTransfers.length()").value(0));
    }
}

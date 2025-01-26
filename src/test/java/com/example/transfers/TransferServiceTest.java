package com.example.transfers;
import com.example.transfers.model.Transfer;
import com.example.transfers.service.TransferService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    @Test
    void testNormalCase() {
        TransferService service = new TransferService();
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(10, 20),
                new Transfer(3, 5),
                new Transfer(8, 15)
        );

        TransferService.Result result = service.calculateOptimalTransfers(transfers, 15);

        assertEquals(30, result.getTotalCost());
        assertEquals(15, result.getTotalWeight());
        assertEquals(2, result.getSelectedTransfers().size());
    }

    @Test
    void testExactCapacityMatch() {
        TransferService service = new TransferService();
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(7, 14),
                new Transfer(3, 6)
        );

        TransferService.Result result = service.calculateOptimalTransfers(transfers, 15);

        assertEquals(30, result.getTotalCost());
        assertEquals(15, result.getTotalWeight());
        assertEquals(3, result.getSelectedTransfers().size());
    }

    @Test
    void testEmptyList() {
        TransferService service = new TransferService();
        List<Transfer> transfers = Collections.emptyList();

        TransferService.Result result = service.calculateOptimalTransfers(transfers, 15);

        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getTotalWeight());
        assertTrue(result.getSelectedTransfers().isEmpty());
    }

    @Test
    void testZeroCapacity() {
        TransferService service = new TransferService();
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(7, 14)
        );

        TransferService.Result result = service.calculateOptimalTransfers(transfers, 0);

        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getTotalWeight());
        assertTrue(result.getSelectedTransfers().isEmpty());
    }

    @Test
    void testSingleTransferFits() {
        TransferService service = new TransferService();
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(10, 20)
        );

        TransferService.Result result = service.calculateOptimalTransfers(transfers, 5);

        assertEquals(10, result.getTotalCost());
        assertEquals(5, result.getTotalWeight());
        assertEquals(1, result.getSelectedTransfers().size());
    }

    @Test
    void testAllTransfersFit() {
        TransferService service = new TransferService();
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(7, 14),
                new Transfer(3, 6)
        );

        TransferService.Result result = service.calculateOptimalTransfers(transfers, 50);

        assertEquals(30, result.getTotalCost());
        assertEquals(15, result.getTotalWeight());
        assertEquals(3, result.getSelectedTransfers().size());
    }

    @Test
    void testNoTransferFits() {
        TransferService service = new TransferService();
        List<Transfer> transfers = Arrays.asList(
                new Transfer(20, 40),
                new Transfer(15, 30)
        );

        TransferService.Result result = service.calculateOptimalTransfers(transfers, 10);

        assertEquals(0, result.getTotalCost());
        assertEquals(0, result.getTotalWeight());
        assertTrue(result.getSelectedTransfers().isEmpty());
    }

    @Test
    void testNegativeCapacity() {
        TransferService service = new TransferService();
        List<Transfer> transfers = Arrays.asList(
                new Transfer(5, 10),
                new Transfer(7, 14)
        );

        assertThrows(IllegalArgumentException.class, () -> service.calculateOptimalTransfers(transfers, -5));
    }

    @Test
    void testNullTransfers() {
        TransferService service = new TransferService();
        assertThrows(NullPointerException.class, () -> service.calculateOptimalTransfers(null, 15));
    }

    @Test
    void testTransfersWithZeroCostAndWeight() {
        TransferService service = new TransferService();
        List<Transfer> transfers = Arrays.asList(
                new Transfer(0, 0),
                new Transfer(5, 10)
        );

        TransferService.Result result = service.calculateOptimalTransfers(transfers, 15);

        assertEquals(10, result.getTotalCost());
        assertEquals(5, result.getTotalWeight());
        assertEquals(1, result.getSelectedTransfers().size());
    }
}

package com.bayzdelivery.jobs;

import com.bayzdelivery.service.DeliveryServiceImpl;
import java.util.ArrayList;
import java.util.List;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DelayedDeliveryNotifierTest {

    @Mock
    private DeliveryServiceImpl deliveryService;

    @InjectMocks
    private DelayedDeliveryNotifier delayedDeliveryNotifier;

    @Test
    void testCheckDelayedDeliveries() {
        LogCaptor logCaptor = LogCaptor.forClass(DelayedDeliveryNotifier.class);

        when(deliveryService.getDelayedDelivery(anyInt())).thenReturn(null);
        delayedDeliveryNotifier.checkDelayedDeliveries();
        assertEquals(0, logCaptor.getInfoLogs().size());

        List<Long> idList = new ArrayList<>();
        idList.add(2L);
        when(deliveryService.getDelayedDelivery(anyInt())).thenReturn(idList);
        delayedDeliveryNotifier.checkDelayedDeliveries();
        assertEquals(1, logCaptor.getInfoLogs().size());
        assertTrue(logCaptor.getInfoLogs().get(0).contains("Customer support team is notified!"));
    }
}

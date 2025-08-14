package com.bayzdelivery.jobs;

import com.bayzdelivery.service.DeliveryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class DelayedDeliveryNotifier {

    @Autowired
    DeliveryService deliveryService;

    private static final int DELAYED_DELIVERY_INTERVAL_IN_MINUTES = 45;

    private static final Logger LOGGER = LoggerFactory.getLogger(DelayedDeliveryNotifier.class);

    /**
     *  Use this method for the TASK 3
     */
    @Scheduled(fixedDelay = 30000)
    public void checkDelayedDeliveries() {
        List<Long> delayedDeliveryIdList = deliveryService.getDelayedDelivery(DELAYED_DELIVERY_INTERVAL_IN_MINUTES);

        if (delayedDeliveryIdList != null && !delayedDeliveryIdList.isEmpty()) {
            notifyCustomerSupport();
        }
    }


    /**
     * This method should be called to notify customer support team
     * It just writes notification on console but it may be email or push notification in real.
     * So that this method should run in an async way.
     */
    @Async
    public void notifyCustomerSupport() {
        LOGGER.info("There are some delayed deliveries. Customer support team is notified!");
    }
}

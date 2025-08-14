package com.bayzdelivery.service;

import com.bayzdelivery.model.Delivery;
import java.util.List;

public interface DeliveryService {

  Delivery save(Delivery delivery);

  Delivery findById(Long deliveryId);

  Boolean isAgentAlreadyDelivering(final Delivery delivery);

  List<Long> getDelayedDelivery(final Integer duration);
}

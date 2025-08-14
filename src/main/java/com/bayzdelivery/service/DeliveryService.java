package com.bayzdelivery.service;

import com.bayzdelivery.model.Delivery;

public interface DeliveryService {

  Delivery save(Delivery delivery);

  Delivery findById(Long deliveryId);

  Boolean isAgentAlreadyDelivering(final Delivery delivery);
}

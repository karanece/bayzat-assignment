package com.bayzdelivery.service;

import com.bayzdelivery.model.DeliveryPerson;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.model.Delivery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

  @Autowired
  DeliveryRepository deliveryRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImpl.class);

  private static final BigDecimal fivePercent = new BigDecimal("0.05");
  private static final BigDecimal fiftyPercent = new BigDecimal("0.5");

  public Delivery save(Delivery delivery) {
    // calculate and set commission
    return deliveryRepository.save(setCommission(delivery));
  }

  public Delivery findById(Long deliveryId) {
    Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
    return optionalDelivery.orElse(null);
  }

  public Boolean isAgentAlreadyDelivering(final Delivery delivery) {
    if (delivery.getDeliveryMan() == null) {
      return Boolean.FALSE;
    }

    Long id = deliveryRepository.isAgentAlreadyDelivering(delivery.getDeliveryMan().getId(), delivery.getStartTime());

    if (id == null) {
      return Boolean.FALSE;
    }

    return Boolean.TRUE;
  }

  // `duration` will be in minutes
  public List<Long> getDelayedDelivery(final Integer duration) {
    return deliveryRepository.findDelayedDelivery(duration);
  }

  // Find top `k` high commission earning delivery agents
  public List<DeliveryPerson> findTopKAgentsWithMostCommission(final Integer limit,
                                                               final String startTime,
                                                               final String endTime) {

    Instant instantStartTime = stringToInstantConvertor(startTime);
    Instant instantEndTime = stringToInstantConvertor(endTime);

    return deliveryRepository.findTopKAgentsWithMostCommission(limit, instantStartTime, instantEndTime);
  }

  //Visible for testing
  Delivery setCommission(Delivery delivery) {
    if (delivery.getCommission() != null && !delivery.getCommission().equals(new BigDecimal("0.0"))) {
      return delivery;
    }

    //Commission = OrderPrice * 0.05 + Distance * 0.5
    BigDecimal commission = delivery.getPrice().multiply(fivePercent).add(delivery.getDistance().multiply(fiftyPercent));

    delivery.setCommission(commission);

    return delivery;
  }

  Instant stringToInstantConvertor(final String instantTimeString) {

    try {
      return Instant.parse(instantTimeString);
    } catch (final DateTimeParseException ex) {
      LOGGER.error("The give time is not in correct format:- yyyy-MM-dd HH:mm:ss.SSSSSS Z , {}", instantTimeString);
    }

    return null;
  }

}

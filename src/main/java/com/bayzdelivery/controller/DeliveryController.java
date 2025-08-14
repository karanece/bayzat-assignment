package com.bayzdelivery.controller;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.DeliveryPerson;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bayzdelivery.service.DeliveryService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

  @Autowired
  DeliveryService deliveryService;

  @PostMapping()
  public ResponseEntity<Delivery> createNewDelivery(@RequestBody Delivery delivery) {
    // HTTP status code 409 - Attempting to create a resource (e.g., a user account, a file)
    // that already exists and is required to be unique

    if (delivery.getId() == null && Boolean.TRUE.equals(deliveryService.isAgentAlreadyDelivering(delivery))) {
      return ResponseEntity.status(409).build();
    }

    Delivery createdDelivery = deliveryService.save(delivery);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdDelivery.getId())
            .toUri();

    return ResponseEntity.created(uri)
            .body(createdDelivery);
  }

  @GetMapping(path = "/{delivery-id}")
  public ResponseEntity<Delivery> getDeliveryById(@PathVariable(name =" delivery-id") Long deliveryId) {
    Delivery delivery = deliveryService.findById(deliveryId);

    if (delivery != null){
      return ResponseEntity.ok(delivery);
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping(path = "/topKAgents")
  public ResponseEntity<List<DeliveryPerson>>
  findTopKAgentsWithHighestCommission(@RequestParam(value = "limit", defaultValue = "3") final Integer limit,
                                      @RequestParam("startTime") final String startTime,
                                      @RequestParam("endTime") final String endTime) {

    List<DeliveryPerson> entities  = deliveryService.findTopKAgentsWithMostCommission(limit, startTime, endTime);

    if (entities != null) {
      return ResponseEntity.ok(entities);
    }
    return ResponseEntity.notFound().build();
  }

}

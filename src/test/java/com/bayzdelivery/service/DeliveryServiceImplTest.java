package com.bayzdelivery.service;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.model.UserRole;
import com.bayzdelivery.repositories.DeliveryRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Test
    void testFindById() {
        Person deliveryAgent = new Person();
        deliveryAgent.setId(1L);
        deliveryAgent.setEmail("abc@xyz.com");
        deliveryAgent.setName("Agent1");
        deliveryAgent.setRole(UserRole.DELIVERY_AGENT);
        deliveryAgent.setRegistrationNumber("100001");

        Person customer = new Person();
        customer.setId(2L);
        customer.setEmail("xyz@abc.com");
        customer.setName("User1");
        customer.setRole(UserRole.CUSTOMER);
        customer.setRegistrationNumber("100002");

        Delivery delivery = new Delivery();
        delivery.setId(1L);
        delivery.setDistance(new BigDecimal("10.56"));
        delivery.setDeliveryMan(deliveryAgent);
        delivery.setCustomer(customer);
        delivery.setPrice(new BigDecimal("15.85"));
        delivery.setCommission(new BigDecimal("5.13"));
        delivery.setStartTime(Instant.now());
        delivery.setEndTime(Instant.now().plus(15, ChronoUnit.MINUTES));

        when(deliveryRepository.findById(delivery.getId())).thenReturn(Optional.of(delivery));
        Delivery actualDelivery = deliveryService.findById(delivery.getId());

        assertNotNull(actualDelivery);
        assertEquals(delivery.getId(), actualDelivery.getId());
        assertEquals(delivery.getCommission(), actualDelivery.getCommission());

        when(deliveryRepository.findById(delivery.getId())).thenReturn(Optional.empty());
        actualDelivery = deliveryService.findById(delivery.getId());

        assertNull(actualDelivery);
    }

    @Test
    void testSave() {
        Person deliveryAgent = new Person();
        deliveryAgent.setId(1L);
        deliveryAgent.setEmail("abc@xyz.com");
        deliveryAgent.setName("Agent1");
        deliveryAgent.setRole(UserRole.DELIVERY_AGENT);
        deliveryAgent.setRegistrationNumber("100001");

        Person customer = new Person();
        customer.setId(2L);
        customer.setEmail("xyz@abc.com");
        customer.setName("User1");
        customer.setRole(UserRole.CUSTOMER);
        customer.setRegistrationNumber("100002");

        Delivery delivery01 = new Delivery();
        delivery01.setId(2L);
        delivery01.setDistance(new BigDecimal("10.56"));
        delivery01.setDeliveryMan(deliveryAgent);
        delivery01.setCustomer(customer);
        delivery01.setPrice(new BigDecimal("15.85"));
        delivery01.setStartTime(Instant.now());
        delivery01.setEndTime(Instant.now().plus(15, ChronoUnit.MINUTES));

        Delivery delivery02 = new Delivery();
        delivery02.setId(delivery01.getId());
        delivery02.setDistance(delivery01.getDistance());
        delivery02.setDeliveryMan(delivery01.getDeliveryMan());
        delivery02.setCustomer(delivery01.getCustomer());
        delivery02.setCommission(new BigDecimal("5.13"));
        delivery02.setPrice(delivery01.getPrice());
        delivery02.setStartTime(delivery01.getStartTime());
        delivery02.setEndTime(delivery01.getEndTime());

        when(deliveryRepository.save(delivery01)).thenReturn(delivery02);

        Delivery actualDelivery = deliveryService.save(delivery01);
        assertEquals(delivery02.getCommission(), actualDelivery.getCommission());
    }

    @Test
    void testCommissionCalc() {
        Person deliveryAgent = new Person();
        deliveryAgent.setId(1L);
        deliveryAgent.setEmail("abc@xyz.com");
        deliveryAgent.setName("Agent1");
        deliveryAgent.setRole(UserRole.DELIVERY_AGENT);
        deliveryAgent.setRegistrationNumber("100001");

        Person customer = new Person();
        customer.setId(2L);
        customer.setEmail("xyz@abc.com");
        customer.setName("User1");
        customer.setRole(UserRole.CUSTOMER);
        customer.setRegistrationNumber("100002");

        Delivery delivery01 = new Delivery();
        delivery01.setId(2L);
        delivery01.setDistance(new BigDecimal("10.56"));
        delivery01.setDeliveryMan(deliveryAgent);
        delivery01.setCustomer(customer);
        delivery01.setPrice(new BigDecimal("15.85"));
        delivery01.setStartTime(Instant.now());
        delivery01.setEndTime(Instant.now().plus(15, ChronoUnit.MINUTES));

        Delivery actualDelivery = deliveryService.setCommission(delivery01);
        assertEquals(new BigDecimal("6.0725"), actualDelivery.getCommission());
    }
}

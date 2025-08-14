package com.bayzdelivery.service;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.DeliveryPerson;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.model.UserRole;
import com.bayzdelivery.repositories.DeliveryRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

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

        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.of(delivery));
        Delivery actualDelivery = deliveryService.findById(delivery.getId());

        assertNotNull(actualDelivery);
        assertEquals(delivery.getId(), actualDelivery.getId());
        assertEquals(delivery.getCommission(), actualDelivery.getCommission());

        when(deliveryRepository.findById(anyLong())).thenReturn(Optional.empty());
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

        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery02);

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

    @Test
    void testIsAgentAlreadyDelivering() {
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
        delivery01.setCustomer(customer);
        delivery01.setPrice(new BigDecimal("15.85"));
        delivery01.setStartTime(Instant.now());

        Boolean actualResult = deliveryService.isAgentAlreadyDelivering(delivery01);
        assertFalse(actualResult);

        delivery01.setDeliveryMan(deliveryAgent);

        when(deliveryRepository.isAgentAlreadyDelivering(anyLong(), any(Instant.class))).thenReturn(delivery01.getId());
        actualResult = deliveryService.isAgentAlreadyDelivering(delivery01);
        assertTrue(actualResult);

        when(deliveryRepository.isAgentAlreadyDelivering(anyLong(), any(Instant.class))).thenReturn(null);
        actualResult = deliveryService.isAgentAlreadyDelivering(delivery01);
        assertFalse(actualResult);
    }

    @Test
    void testGetDelayedDelivery() {
        when(deliveryRepository.findDelayedDelivery(anyInt())).thenReturn(null);
        List<Long> actualResult = deliveryService.getDelayedDelivery(45);
        assertNull(actualResult);

        List<Long> idList = new ArrayList<>();
        idList.add(2L);
        when(deliveryRepository.findDelayedDelivery(anyInt())).thenReturn(idList);
        actualResult = deliveryService.getDelayedDelivery(45);
        assertEquals(1, actualResult.size());
        assertEquals(2L, actualResult.get(0));
    }

    @Test
    void testFindTopKCommission() {
        DeliveryPerson deliveryPerson01 = factory.createProjection(DeliveryPerson.class);
        deliveryPerson01.setId(1L);
        deliveryPerson01.setName("Agent01");
        deliveryPerson01.setAvgCommission(new BigDecimal("8.26"));
        deliveryPerson01.setCommission(new BigDecimal("5.53"));
        deliveryPerson01.setStartTime(Instant.now().minus(60, ChronoUnit.MINUTES));
        deliveryPerson01.setEndTime(Instant.now().minus(50, ChronoUnit.MINUTES));

        DeliveryPerson deliveryPerson02 = factory.createProjection(DeliveryPerson.class);
        deliveryPerson02.setId(2L);
        deliveryPerson02.setName("Agent02");
        deliveryPerson02.setCommission(new BigDecimal("8.57"));
        deliveryPerson02.setAvgCommission(new BigDecimal("8.26"));
        deliveryPerson02.setStartTime(Instant.now().minus(40, ChronoUnit.MINUTES));
        deliveryPerson02.setEndTime(Instant.now().minus(30, ChronoUnit.MINUTES));

        DeliveryPerson deliveryPerson03 = factory.createProjection(DeliveryPerson.class);
        deliveryPerson03.setId(2L);;
        deliveryPerson03.setName("Agent02");
        deliveryPerson03.setCommission(new BigDecimal("7.47"));
        deliveryPerson03.setAvgCommission(new BigDecimal("8.26"));
        deliveryPerson03.setStartTime(Instant.now().minus(20, ChronoUnit.MINUTES));
        deliveryPerson03.setEndTime(Instant.now().minus(5, ChronoUnit.MINUTES));

        DeliveryPerson deliveryPerson04 = factory.createProjection(DeliveryPerson.class);
        deliveryPerson04.setId(1L);
        deliveryPerson04.setName("Agent01");
        deliveryPerson04.setCommission(new BigDecimal("11.47"));
        deliveryPerson04.setAvgCommission(new BigDecimal("8.26"));
        deliveryPerson04.setStartTime(Instant.now().minus(30, ChronoUnit.MINUTES));
        deliveryPerson04.setEndTime(Instant.now());

        List<DeliveryPerson> deliveryPersonList = new ArrayList<>();
        deliveryPersonList.add(deliveryPerson01);
        deliveryPersonList.add(deliveryPerson02);
        deliveryPersonList.add(deliveryPerson03);
        deliveryPersonList.add(deliveryPerson04);

        when(deliveryRepository.findTopKAgentsWithMostCommission(anyInt(),
                                                                 any(Instant.class),
                                                                 any(Instant.class)))
                .thenReturn(deliveryPersonList);
        System.out.println(Instant.now().toString());
        List<DeliveryPerson> actualDeliveryPersonList = deliveryService.findTopKAgentsWithMostCommission(4,
                                                         Instant.now().minus(120, ChronoUnit.MINUTES).toString(),
                                                         Instant.now().toString());
        assertNotNull(actualDeliveryPersonList);
        assertEquals(4, actualDeliveryPersonList.size());
    }
}

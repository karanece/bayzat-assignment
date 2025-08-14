package com.bayzdelivery.service;

import com.bayzdelivery.model.Person;
import com.bayzdelivery.model.UserRole;
import com.bayzdelivery.repositories.PersonRepository;
import java.util.ArrayList;
import java.util.List;
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
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void testGetAll() {
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

        List<Person> personList = new ArrayList<>();
        personList.add(deliveryAgent);
        personList.add(customer);

        when(personRepository.findAll()).thenReturn(personList);
        List<Person> actualPersonList = personService.getAll();
        assertEquals(actualPersonList.size(), 2);
    }

    @Test
    void testFindById() {
        Person customer = new Person();
        customer.setId(2L);
        customer.setEmail("xyz@abc.com");
        customer.setName("User1");
        customer.setRole(UserRole.CUSTOMER);
        customer.setRegistrationNumber("100002");

        when(personRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        Person actualPerson = personService.findById(customer.getId());

        assertNotNull(actualPerson);
        assertEquals(actualPerson.getId(), customer.getId());
        assertEquals(actualPerson.getName(), customer.getName());
        assertEquals(actualPerson.getEmail(), customer.getEmail());
        assertEquals(actualPerson.getRegistrationNumber(), customer.getRegistrationNumber());
        assertEquals(actualPerson.getRole(), customer.getRole());

        when(personRepository.findById(customer.getId())).thenReturn(Optional.empty());
        actualPerson = personService.findById(customer.getId());

        assertNull(actualPerson);
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

        when(personRepository.save(deliveryAgent)).thenReturn(deliveryAgent);
        Person actualPerson = personService.save(deliveryAgent);
        assertEquals(actualPerson.getId(), deliveryAgent.getId());
        assertEquals(actualPerson.getRole(), deliveryAgent.getRole());

        when(personRepository.save(customer)).thenReturn(customer);
        actualPerson = personService.save(customer);
        assertEquals(actualPerson.getId(), customer.getId());
        assertEquals(actualPerson.getRole(), customer.getRole());
    }
}

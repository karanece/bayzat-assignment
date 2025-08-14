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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        assertEquals(2, actualPersonList.size());
    }

    @Test
    void testFindById() {
        Person customer = new Person();
        customer.setId(2L);
        customer.setEmail("xyz@abc.com");
        customer.setName("User1");
        customer.setRole(UserRole.CUSTOMER);
        customer.setRegistrationNumber("100002");

        when(personRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        Person actualPerson = personService.findById(customer.getId());

        assertNotNull(actualPerson);
        assertEquals(customer.getId(), actualPerson.getId());
        assertEquals(customer.getName(), actualPerson.getName());
        assertEquals(customer.getEmail(), actualPerson.getEmail());
        assertEquals(customer.getRegistrationNumber(), actualPerson.getRegistrationNumber());
        assertEquals(customer.getRole(), actualPerson.getRole());

        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());
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

        when(personRepository.save(any(Person.class))).thenReturn(deliveryAgent);
        Person actualPerson = personService.save(deliveryAgent);
        assertEquals(deliveryAgent.getId(), actualPerson.getId());
        assertEquals(deliveryAgent.getRole(), actualPerson.getRole());

        when(personRepository.save(any(Person.class))).thenReturn(customer);
        actualPerson = personService.save(customer);
        assertEquals(customer.getId(), actualPerson.getId());
        assertEquals(customer.getRole(), actualPerson.getRole());
    }
}

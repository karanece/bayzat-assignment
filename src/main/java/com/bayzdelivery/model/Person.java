package com.bayzdelivery.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "person")
public class Person implements Serializable{

  @Serial
  private static final long serialVersionUID = 432154291451321L;

  public Person() {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name")
  String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  @NotNull
  private UserRole role;

  @NotNull
  @Email
  @Column(name = "email")
  String email;

  @NotNull
  @Column(name = "registration_number")
  String registrationNumber;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(final UserRole role) {
    this.role = role;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Person other = (Person) obj;

    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }

    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }

    if (role == null) {
      if (other.role != null) {
        return false;
      }
    } else if (!role.getRole().equals(other.role.getRole())) {
      return false;
    }

    if (registrationNumber == null) {
      return other.registrationNumber == null;
    } else {
      return registrationNumber.equals(other.registrationNumber);
    }  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", name=" + name + ", role=" + role.getRole() + ", email=" + email + ", registrationNumber=" + registrationNumber + "]";
  }

}

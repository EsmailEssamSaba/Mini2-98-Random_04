package com.example.miniapp.repositories;

import com.example.miniapp.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.email LIKE %:emailDomain")
    List<Customer> findCustomersByEmailDomain(String emailDomain);

    @Query("SELECT c FROM Customer c WHERE c.phoneNumber LIKE :phonePrefix%")
    List<Customer> findCustomersByPhonePrefix(String phonePrefix);
}

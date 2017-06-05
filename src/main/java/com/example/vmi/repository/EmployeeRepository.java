package com.example.vmi.repository;

import com.example.vmi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, QueryDslPredicateExecutor<Employee>{
    
    Employee findByEmailAndPassword(String email, String password);
}

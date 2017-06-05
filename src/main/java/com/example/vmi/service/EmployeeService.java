package com.example.vmi.service;

import com.example.vmi.entity.Employee;
import static com.example.vmi.expression.EmployeeExpression.*;
import com.example.vmi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }
    
    public Employee authenticate(String email, String password){
        return employeeRepository.findByEmailAndPassword(email, password);
        //return employeeRepository.findOne(hasEmail(email).and(hasPassword(password)));
    }
    
    public Employee findOne(Long id){
        return employeeRepository.findOne(id);
    }
}

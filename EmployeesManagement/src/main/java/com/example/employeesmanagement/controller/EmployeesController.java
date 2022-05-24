package com.example.employeesmanagement.controller;

import com.example.employeesmanagement.model.Employee;
import com.example.themeparks.model.ResponseAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeesController {
    private ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping
    public ResponseEntity getAllEmployees(){
        if (employees.size() ==0) {
            return ResponseEntity.status(200).body(new ResponseAPI("There's no employees in the list!",200));
        }
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping
    public ResponseEntity<ResponseAPI> addEmployee(@RequestBody @Valid Employee employee, Errors errors) {
        if(errors.hasErrors()) {
            String message = Objects.requireNonNull(errors.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(400).body(new ResponseAPI(message,400,errors.getFieldError()));
        }
        employees.add(employee);
        return ResponseEntity.status(201).body(new ResponseAPI("Employee Added Successfully!",200));
    }

    @PutMapping("/{ID}")
    public ResponseEntity<ResponseAPI> updateEmployee(@PathVariable Integer ID,@RequestBody @Valid Employee employee,Errors errors) {
        if(errors.hasErrors()) {
            String message = Objects.requireNonNull(errors.getFieldError()).getDefaultMessage();
            return ResponseEntity.status(400).body(new ResponseAPI(message,400,errors.getFieldError()));
        }
        if(!checkEmployee(ID,employee)){
            return ResponseEntity.status(400).body(new ResponseAPI("ID is Wrong!",400));
        }
        return ResponseEntity.status(200).body(new ResponseAPI("Employee Updated Successfully!",200));
    }

    @DeleteMapping("/{ID}")
    public ResponseEntity<ResponseAPI> removeEmployee(@PathVariable Integer ID) {
        if(!deleteEmployee(ID)){
            return ResponseEntity.status(400).body(new ResponseAPI("ID is Wrong!",400));
        }
        return ResponseEntity.status(200).body(new ResponseAPI("Employee Deleted Successfully!",200));
    }

    @PutMapping("/remove/{ID}")
    public ResponseEntity<ResponseAPI> applyForAnnualLeave(@PathVariable Integer ID) {
        Employee employee = checkAnnualEmployee(ID);
        if (employee == null) {
            return ResponseEntity.status(400).body(new ResponseAPI("ID is Wrong!",400));
        }
        if(employee.getOnLeave()) {
            return ResponseEntity.status(400).body(new ResponseAPI("You Are Already on Leave!",400));
        }
        if(employee.getAnnualLeave() < 0) {
            return ResponseEntity.status(400).body(new ResponseAPI("you have no more Annual Leave!",400));
        }

        employee.setOnLeave(true);
        return ResponseEntity.status(200).body(new ResponseAPI("Employee is now on Leave!",200));
    }



    //Helper Function
    public Boolean checkEmployee(Integer id,Employee employee) {

        for (int i = 0; i < employees.size(); i++) {
            if(employees.get(i).getID().equals(String.valueOf(id))){
                employees.set(i,employee);
                return true;
            }
        }
        return false;
    }

    public Employee checkAnnualEmployee(Integer id) {
        for (Employee employee1: employees) {
            if(employee1.getID().equals(String.valueOf(id))){
                return employee1;
            }
        }
        return null;
    }


    public Boolean deleteEmployee(Integer id) {
        for (int i = 0; i < employees.size(); i++) {
            if(employees.get(i).getID().equals(String.valueOf(id))){
                employees.remove(i);
                return true;
            }
        }
        return false;
    }


}

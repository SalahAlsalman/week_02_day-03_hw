package com.example.employeesmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor @Data
public class Employee {
    @NotNull(message = "ID is null")
    @Size(min = 2,message="ID should be more than 2")
    private String ID;
    @NotNull(message = "name is null")
    @Size(min = 4,message="name should be more than 2")
    private String name;
    @NotNull(message = "age is null")
    @Positive(message = "age must be a number")
    @Min(value = 25,message = "age must be more than 25")
    private Integer age;
    @AssertFalse(message = "onLeave must be false")
    @NotNull(message = "onLeave is null")
    private Boolean onLeave;
    @NotNull(message = "employmentYear is null")
    @Positive(message = "employmentYear must be a number")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String employmentYear;
    @NotNull(message = "annualLeave is null")
    @Positive(message = "annualLeave must be a number")
    private Integer annualLeave;

}

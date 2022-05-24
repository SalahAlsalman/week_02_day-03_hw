package com.example.themeparks.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor @Data
public class Park {
    @NotNull(message = "rideID is null")
    @Size(min = 2, message = "rideID must be more than 2")
    private String rideID;
    @NotNull(message = "rideName is null")
    @Size(min = 4, message = "rideName must be more than 4")
    private String rideName;
    @NotNull(message = "rideType is null")
    @Pattern(regexp = "Rollercoaster|thriller|water",message = "must be Rollercoaster|thriller|water")
    private String rideType;
    @NotNull(message = "tickets is null")
//    @Pattern(message = "tickets must be a number", regexp="^[0-9]*$")
    @Positive(message = "tickets must be a number")
    private Integer tickets;
    @NotNull(message = "price is null")
//    @Pattern(message = "price must be a number", regexp="^[0-9]*$")
    @Positive(message = "price must be a number")
    private Double price;
}

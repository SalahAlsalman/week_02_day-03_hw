package com.example.themeparks.controller;

import com.example.themeparks.model.Park;
import com.example.themeparks.model.ResponseAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/park")
public class ParkController {
    ArrayList<Park> rides = new ArrayList<>();

    @GetMapping
    public ResponseEntity<Object> getAllTheRides(){
        if (rides.size() == 0) {
            return ResponseEntity.status(200).body(new ResponseAPI("there's no rides available!", 200));
        }
        return ResponseEntity.status(200).body(rides);
    }

    @PostMapping
    public ResponseEntity<ResponseAPI> addRide(@RequestBody @Valid Park park, Errors errors) {
        if (errors.hasErrors()) {
            ResponseAPI r1= errorsCheck(errors);
            return ResponseEntity.status(400).body(r1);
        }
        rides.add(park);
        return ResponseEntity.status(201).body(new ResponseAPI("Park Added Successfully!", 201));
    }

    @PutMapping("/{rideID}")
    public ResponseEntity<ResponseAPI> updateRide(@PathVariable Integer rideID,@RequestBody @Valid Park park, Errors errors) {
        if (errors.hasErrors()) {
            ResponseAPI r1= errorsCheck(errors);
            return ResponseEntity.status(400).body(r1);
        }
        Park updatePark = updateRide(rideID,park);
        if (updatePark == null) {
            return ResponseEntity.status(400).body(new ResponseAPI("rideID is Wrong!", 400));
        }
        return ResponseEntity.status(200).body(new ResponseAPI("Park Updated Successfully!", 200));
    }

    @DeleteMapping("/{rideID}")
    public ResponseEntity<ResponseAPI> deleteRide(@PathVariable Integer rideID) {
        Park deletedPark = getRide(rideID);
        if (deletedPark == null) {
            return ResponseEntity.status(400).body(new ResponseAPI("rideID is Wrong!", 400));
        }
        rides.remove(deletedPark);
        return ResponseEntity.status(200).body(new ResponseAPI("Park Deleted Successfully!", 200));
    }

    @PostMapping("/buyTickets/{rideID}/{amount}")
    public ResponseEntity<ResponseAPI> buyTickets(@PathVariable Integer rideID, @PathVariable Double amount) {
        Park ticketsRide = getRide(rideID);
        if (ticketsRide == null) {
            return ResponseEntity.status(400).body(new ResponseAPI("rideID is Wrong!", 400));
        }
        if (ticketsRide.getTickets() == 0) {
            return ResponseEntity.status(400).body(new ResponseAPI("There's 0 tickets available!", 400));
        }
        if (amount < ticketsRide.getPrice()) {
            return ResponseEntity.status(400).body(new ResponseAPI("amount is less than price!", 400));
        }
        ticketsRide.setTickets(ticketsRide.getTickets() -1);
        return ResponseEntity.status(200).body(new ResponseAPI("Tickets sold Successfully!", 200));
    }





    //helper functions
    private ResponseAPI errorsCheck(Errors errors) {
        String message = Objects.requireNonNull(errors.getFieldError()).getDefaultMessage();
        return new ResponseAPI(message, 400,errors.getFieldError());
    }

    private Park getRide(Integer id) {
        for (int i = 0; i < rides.size(); i++) {
            Park parkfor= rides.get(i);
            if (parkfor.getRideID().equals(String.valueOf(id))){
                return parkfor;
            }
        }
        return null;
    }

    private Park updateRide(Integer id, Park park) {
        for (int i = 0; i < rides.size(); i++) {
            Park parkfor= rides.get(i);
            if (parkfor.getRideID().equals(String.valueOf(id))){
                rides.set(i,park);
                return parkfor;
            }
        }
        return null;
    }

}

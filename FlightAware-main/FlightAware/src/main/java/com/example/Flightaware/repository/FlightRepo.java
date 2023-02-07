package com.example.Flightaware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Flightaware.model.UserIp;

@Repository
public interface FlightRepo extends CrudRepository<UserIp, Long> {

}
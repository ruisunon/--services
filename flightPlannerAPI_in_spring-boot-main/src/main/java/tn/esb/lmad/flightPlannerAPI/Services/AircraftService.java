package tn.esb.lmad.flightPlannerAPI.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;
import tn.esb.lmad.flightPlannerAPI.Domains.Aircraft;
import tn.esb.lmad.flightPlannerAPI.Repositories.AircraftRepository;

import java.util.List;
import java.util.Optional;

@Service
//The Service class is the Controller of the ECB (Entity Control Boundary) architecture
//In the service, we implement the business logic of the application like CRUD, the validation of the data, the business rules, etc...
//the service need to access the data from the database
//The service is implemented in java OO (Object Oriented) programming
//the database use SQL (Structured Query Language) programming
//the service will call the repository to access the data from the database
//the repository is responsible for the interaction with the database
public class AircraftService {
    private final AircraftRepository aircraftRepository;
    //@Autowired annotation is used to inject the AircraftRepository object
    //Using the AircraftRepository object, we can access the data from the database
    //Using Autowired annotation, we don't need to create an object of the AircraftRepository by ourselves
    //Spring will create an object of the AircraftRepository and inject it in the AircraftService
    @Autowired
    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }
    //implentation of the business logic of the application
    //implementation of the CRUD operations
    //get all aricrafts
    public ResponseEntity<List<Aircraft>> getAllAircraft(){
        List<Aircraft> aircraft=aircraftRepository.findAll();
        if(aircraft.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(aircraft);
        return  ResponseEntity.status(HttpStatus.OK).body(aircraft);
    }
    //get an aircraft by its code
    //findById() method returns an Optional<Aircraft> object
    //Optional<Aircraft> findById(String code);
    //Optional is a collection that can contain 0 or 1 element
    //getById() method returns the Object if it exists in the database or nothing if it doesn't exist
    //Optional has many methods like get(), isPresent(), isEmpty() and orElseThrow(), ...
    //get() method returns the Object if it exists in the database or throws an exception if it doesn't exist
    //isPresent() method returns true if the Optional contains an Object or false if it doesn't contain an Object
    //isEmpty() method returns true if the Optional doesn't contain an Object or false if it contains an Object
    //orElseThrow() method returns the Object if it exists in the database or throws an exception if it doesn't exist
    public Optional<Aircraft> getAircraftByCode(String code){
        if(!aircraftRepository.existsById(code))
            throw new RuntimeException("Aircraft with code "+code+" not found");
        return aircraftRepository.findById(code);
    }
    //or using orElseThrow() method
    //public Aircraft getAircraftByCode(String code){
    //    return aircraftRepository.findById(code).orElseThrow(()->new RuntimeException("Aircraft with code "+code+" not found"));
    //}
    //add an aircraft
    public ResponseEntity<?> addAircraft(Aircraft aircraft){
        if(aircraftRepository.existsById(aircraft.getCode()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The aircraft with code "+aircraft.getCode()+" already exists");
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraft);
    }
    public Aircraft updateAircraft(String code,Aircraft aircraft){
        if(!aircraftRepository.existsById(code))
            throw new RuntimeException("Aircraft with code "+code+" not found");
        aircraft.setCode(code);
        return aircraftRepository.save(aircraft);
    }
    public void deleteAircraft(String code){
        if(!aircraftRepository.existsById(code))
            throw new RuntimeException("Aircraft with code "+code+" not found");
        aircraftRepository.deleteById(code);
    }

}

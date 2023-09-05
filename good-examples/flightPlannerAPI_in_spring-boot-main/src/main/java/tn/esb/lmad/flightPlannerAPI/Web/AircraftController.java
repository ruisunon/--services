package tn.esb.lmad.flightPlannerAPI.Web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esb.lmad.flightPlannerAPI.Domains.Aircraft;
import tn.esb.lmad.flightPlannerAPI.Services.AircraftService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:49430"})

@RestController
//this class is the boundary of our architecture ECB (Entity Control Boundary)
//In the controller we will have the endpoints (APIs) that will be exposed to the outside world (the front end or the  client)
//the controller interacts with the service layer and with the front end
//the controller is listening to the requests from the front end and it will call the service layer
//the controller will return the response to the front end
//the frontend/backend communication is done using json (or xml, resources, etc...) and HTTP protocol
//so in the controller we use annotations like @GetMapping, @PostMapping, @PutMapping, @DeleteMapping and @PatchMapping
//to specify the HTTP method (GET, POST, PUT, PATCH,DELETE, ...) of the request, the path of the request and the response status code (200, 201, 204, 400, 404, 500, ...)
//@GetMapping annotation is used if the request want to get data from the backend (in SQL SELECT)
//@PostMapping annotation is used if the request want to add data to the backend (in SQL INSERT)
//@PutMapping annotation is used if the request want to update data entirely in the backend (in SQL UPDATE)
//@PatchMapping annotation is used if the request want to update data partially in the backend (in SQL UPDATE)
//@DeleteMapping annotation is used if the request want to remove data from the backend (in SQL DELETE)
//The font end or the client send a HttpRequest to the backend and the backend will return a HttpResponse
//Both HttpRequest and HttpResponse have a body and a header
//The header contains the metadata of the request/response like the content type,
//the content length, the status code,the method (request) or the protocol,encoding,authorization,...
//The body contains the data of the request/response (json, xml, resources, ...), in general the body is in json format
//the data must be sent on the body is sensible data (password, credit card number, ...) or big data (images, videos, objects ...)
//spring-boot uses the @RequestBody annotation to get the data from the request body

//We can send too data to the backend on the path of the request (in the url)
//like if we want to call a service calculator having the url http://localhost:8080/calculator/
//andd wde want to evaluate the expression 2+3
//the request will be http://localhost:8080/calculator?x=2&y=3
//the x and y are the parameters of the request and spring boot uses the @RequestParam annotation to get the parameters from the request
 // the data could be also sent to the backend on the url of the request as a path variable
//like if we want to call a service calculator having the url http://localhost:8080/calculator/
//and wde want to evaluate the sin of 70 degrees
//the request will be http://localhost:8080/calculator/sin/70
//spring boot uses the @PathVariable annotation to get the path variable from the request

// for the data format we use annotations like @JsonFormat, @JsonIdentityInfo, @JsonIgnore, @JsonProperty
@RequestMapping("/aircraft")
//the line below add /aircraft to the path of all the endpoints of this controller
public class AircraftController {
    //the controller will call the service layer
    private final AircraftService aircraftService;
    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }
    //list all aircraft
    //url=http://localhost:9995/aircrafact/all
    @GetMapping("/all")
    public ResponseEntity<List<Aircraft>> getAircraft(){
        return aircraftService.getAllAircraft();
    }
    //get an aircraft by its code
    //to get the aircraft having the code A1
    //the url=http://localhost:9995/aircrafact/A1
    //to get the aircraft having the code A2
    //the url=http://localhost:9995/aircrafact/A2
    //@GetMapping("/code") //==> the url is http://localhost:9995/aircrafact/code
    @GetMapping("/{code}")
    //{code} means that the code value is a variable
    //Adding the annotation @PathVariable means that the code value
    //will be taken from the path of the request
    public Optional<Aircraft> getAircraft(@PathVariable String code){
        return aircraftService.getAircraftByCode(code);
    }
    //add an aircraft
    @PostMapping("/add")
    //the annotation @RequestBody means that the aircraft object
    //will be taken from the body of the request
    //@Valid annotation means that the aircraft object will be validated
    //Using @Valid annotation spring-boot will verify if the given object
    //is satisfying constraints defined in the Aircraft domain class like @Email, max length, min length
    //date and time format, fields type,...
    //if the parameter is not valid then the request is rejected
    //if not then the request is transmitted to the service.
    public ResponseEntity<?> addOneAircraft(@Valid  @RequestBody Aircraft aircraft){
        return aircraftService.addAircraft(aircraft);
    }
    @PutMapping("/update/{code}")
    public Aircraft updateOneAircraft(@PathVariable String code,@Valid @RequestBody Aircraft aircraft){
        return aircraftService.updateAircraft(code,aircraft);
    }
    @DeleteMapping("/delete/{code}")
    public void deleteOneAircraft(@PathVariable String code){
        aircraftService.deleteAircraft(code);
    }
}

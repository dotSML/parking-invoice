package ee.sml.parking.controller;

import ee.sml.parking.model.Parking;
import ee.sml.parking.service.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {
    ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping()
    public List<Parking> list() {
        return parkingService.listParkings();
    }

    @PostMapping("/add/{propertyId}/{customerId}")
    public String add(@PathVariable String propertyId, @PathVariable String customerId) {
        // Starts parking
        parkingService.add(propertyId, customerId);
        return "OK";
    }


}

package htttdl.Controller;
import htttdl.Entity.Hospital;
import htttdl.Entity.HospitalTier;
import htttdl.Entity.Specialty;
import htttdl.Entity.Street;
import htttdl.Entity.Ward;
import htttdl.Repository.HospitalTierRepository;
import htttdl.Repository.SpecialtyRepository;
import htttdl.Repository.StreetRepository;
import htttdl.Repository.WardRepository;
import htttdl.Service.HospitalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/hospital")
@CrossOrigin(origins ="http://127.0.0.1:5500")
public class HospitalController {

	@Autowired
    private HospitalService service;
	
	@Autowired
    private WardRepository wardRepo;
	
	@Autowired
    private HospitalTierRepository hospitalTierRepo;
	
	@Autowired
    private SpecialtyRepository specialtyRepo;
	
	@Autowired
    private StreetRepository streetRepo;

    @GetMapping("/")
    public String getAllGeoLocationsAsGeoJson() {
        List<Hospital> geoLocations = service.get();
        return service.convertGeoLocationsToGeoJSON(geoLocations);
    }
    
    
    @PostMapping("/add")
    public ResponseEntity<String> addGeoLocation(@RequestParam String name,
                                                  @RequestParam String number,
                                                  @RequestParam Integer type, 
                                                  @RequestParam Long phone,
                                                  @RequestParam double longitude,
                                                  @RequestParam double latitude,
                                                  @RequestParam Long streetId,
                                                  @RequestParam Long wardId,
                                                  @RequestParam Long hospitalTierId,
                                                  @RequestParam Long specialtyId) { 
        GeometryFactory geometryFactory = new GeometryFactory();
        
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));

        Street street = streetRepo.findById(streetId).get();
        Ward ward = wardRepo.findById(wardId).get();
        HospitalTier hospitalTier = hospitalTierRepo.findById(hospitalTierId).get(); 
        Specialty specialty = specialtyRepo.findById(specialtyId).get(); 

        Hospital geoLocation = new Hospital(name, number, type, phone, point, hospitalTier, street, specialty, ward);

        service.saveGeoLocation(geoLocation);
        
        return ResponseEntity.ok("Điểm đã được thêm thành công!");
    }


    
    @GetMapping("/search")
    public List<Map<String, Object>> searchGeoLocations(@RequestParam("name") String name) {
        List<Hospital> geoLocations = service.searchByName(name);
        
        List<Map<String, Object>> result = new ArrayList<>();
        for (Hospital geoLocation : geoLocations) {
            if (geoLocation.getLocation() instanceof Point) {
                Map<String, Object> geoData = new HashMap<>();
                geoData.put("name", geoLocation.getName());
                geoData.put("phone", geoLocation.getPhone());
                geoData.put("number", geoLocation.getNumber());
                geoData.put("street", geoLocation.getStreet().getName());
                geoData.put("ward", geoLocation.getWard().getName());
                
                Map<String, Double> coordinates = new HashMap<>();
                coordinates.put("x", geoLocation.getLocation().getCoordinate().x);
                coordinates.put("y", geoLocation.getLocation().getCoordinate().y);

                geoData.put("coordinates", coordinates);
                result.add(geoData);
            }
        }
        
        return result;
    }
    
    @GetMapping("/nearby")
    public List<Map<String, Object>> getNearbyHospitals(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "1000") double radius) {
        List<Hospital> nearbyHospitals = service.findHospitalsWithinRadius(latitude, longitude, radius);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Hospital geoLocation : nearbyHospitals) {
            if (geoLocation.getLocation() instanceof Point) {
                Map<String, Object> geoData = new HashMap<>();
                geoData.put("name", geoLocation.getName());
                geoData.put("phone", geoLocation.getPhone());
                geoData.put("number", geoLocation.getNumber());
                geoData.put("street", geoLocation.getStreet().getName());
                geoData.put("ward", geoLocation.getWard().getName());
                
                Map<String, Double> coordinates = new HashMap<>();
                coordinates.put("x", geoLocation.getLocation().getCoordinate().x);
                coordinates.put("y", geoLocation.getLocation().getCoordinate().y);

                geoData.put("coordinates", coordinates);
                result.add(geoData);
            }
        }
        return result;
    }

    //Tìm đường đi
    @GetMapping("/route")
    public ResponseEntity<String> getRoute(
            @RequestParam double startLongitude,
            @RequestParam double startLatitude,
            @RequestParam double endLongitude,
            @RequestParam double endLatitude,
            @RequestParam(required = false, defaultValue = "false") boolean alternatives) {

        String osrmUrl = String.format("http://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=full&alternatives=%b", 
                                        startLongitude, startLatitude, endLongitude, endLatitude, alternatives);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(osrmUrl, String.class);

        return ResponseEntity.ok(response);
    }


}

package htttdl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import htttdl.Entity.HospitalTier;
import htttdl.Repository.HospitalTierRepository;

@RestController
@CrossOrigin("http://127.0.0.1:5500")
public class HospitalTierController {

	@Autowired
    private HospitalTierRepository hospitalTierRepo;
	
    @GetMapping("/api/hospital-tiers")
    public List<HospitalTier> getHospitalTiers() {
        return hospitalTierRepo.findAll();
    }
}

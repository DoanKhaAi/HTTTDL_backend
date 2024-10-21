package htttdl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import htttdl.Entity.Specialty;
import htttdl.Repository.SpecialtyRepository;

@RestController
@CrossOrigin("http://127.0.0.1:5500")
public class SpecialtyController {
	@Autowired
    private SpecialtyRepository specialtyRepo;
	
	@GetMapping("/api/specialties")
    public List<Specialty> getAll() {
        return specialtyRepo.findAll();
    }
}

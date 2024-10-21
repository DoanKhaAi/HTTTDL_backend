package htttdl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import htttdl.Entity.Street;
import htttdl.Repository.StreetRepository;

@RestController
@CrossOrigin("http://127.0.0.1:5500")
public class StreetController {
	@Autowired
    private StreetRepository streetRepo;
	
	@GetMapping("/api/streets")
    public List<Street> getAll() {
        return streetRepo.findAll();
    }
}

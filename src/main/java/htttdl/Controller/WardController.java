package htttdl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import htttdl.Entity.Ward;
import htttdl.Repository.WardRepository;

@RestController
@CrossOrigin("http://127.0.0.1:5500")
public class WardController {
	@Autowired
    private WardRepository wardRepo;
	
    @GetMapping("/api/wards")
    public List<Ward> getAll() {
        return wardRepo.findAll();
    }
}

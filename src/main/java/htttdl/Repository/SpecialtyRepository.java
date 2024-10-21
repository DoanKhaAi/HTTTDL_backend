package htttdl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import htttdl.Entity.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long>{

}

package htttdl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import htttdl.Entity.Street;

@Repository
public interface StreetRepository  extends JpaRepository<Street, Long>{

}

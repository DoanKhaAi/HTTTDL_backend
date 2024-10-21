package htttdl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import htttdl.Entity.Ward;

@Repository
public interface WardRepository  extends JpaRepository<Ward, Long>{

}

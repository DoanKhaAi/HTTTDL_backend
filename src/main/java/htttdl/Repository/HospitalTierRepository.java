package htttdl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import htttdl.Entity.HospitalTier;

@Repository
public interface HospitalTierRepository extends JpaRepository<HospitalTier, Long>{

}

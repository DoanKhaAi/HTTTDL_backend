package htttdl.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import htttdl.Entity.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT g FROM Hospital g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Hospital> findByNameAndPoint(String name);
    
    @Query("SELECT g FROM Hospital g WHERE (LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(g.ward.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(g.street.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Hospital> findByNameAndAddress(String name);
    
    @Query(value = "SELECT * FROM hospital h WHERE ST_Distance_Sphere(h.location, point(:longitude, :latitude)) <= :radius", nativeQuery = true)
    List<Hospital> findHospitalsWithinRadius(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);

}

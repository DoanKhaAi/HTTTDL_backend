package htttdl.Entity;

import org.locationtech.jts.geom.Geometry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hospital")
public class Hospital{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column
    private String number;
    
    @Column 
    private Integer type;
    
    @Column 
    private Long phone;

    @Column(columnDefinition = "geometry")
    private Geometry location;
    
    @ManyToOne
    @JoinColumn(name = "hospital_tier_id", nullable = false)
    private HospitalTier hospitalTier;
    
    @ManyToOne
    @JoinColumn(name = "street_id", nullable = false)
    private Street street;
    
    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    private Specialty specialty;
    
    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

    public Hospital() {}

    public Hospital(String name, Geometry location) {
        this.name = name;
        this.location = location;
    }
   

	public Hospital(String name, String number, Integer type, Long phone, Geometry location,
			HospitalTier hospitalTier, Street street, Specialty specialty, Ward ward) {
		this.name = name;
		this.number = number;
		this.type = type;
		this.phone = phone;
		this.location = location;
		this.hospitalTier = hospitalTier;
		this.street = street;
		this.specialty = specialty;
		this.ward = ward;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   

    public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public Geometry getLocation() {
        return location;
    }

    public void setLocation(Geometry location) {
        this.location = location;
    }

	public HospitalTier getHospitalTier() {
		return hospitalTier;
	}

	public void setHospitalTier(HospitalTier hospitalTier) {
		this.hospitalTier = hospitalTier;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
    
    
}

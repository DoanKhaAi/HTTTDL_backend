package htttdl.Service;

import java.io.StringWriter;
import java.util.List;

import org.geotools.geojson.geom.GeometryJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import htttdl.Entity.Hospital;
import htttdl.Repository.HospitalRepository;



@Service 
public class HospitalService {
	@Autowired
    private HospitalRepository repo;
	
	 public List<Hospital> get() {
	    return  (List<Hospital>) repo.findAll();
	
	  }
 
	@SuppressWarnings("deprecation")
	public String geometryToGeoJSON(Geometry geometry) throws Exception {
	    GeometryJSON gjson = new GeometryJSON();
	    StringWriter writer = new StringWriter();
	    gjson.write(geometry, writer);
	    return writer.toString();      
	}

    
 // thư viện json không hỗ trợ kiểu generics gây ra cảnh báo "raw type" do không có kiểu dữ liệu cụ thể nào được chỉ định
    @SuppressWarnings("unchecked") 
    public String convertGeoLocationsToGeoJSON(List<Hospital> geoLocations) {
        JSONObject featureCollection = new JSONObject();
        featureCollection.put("type", "FeatureCollection");

        JSONArray features = new JSONArray();

        for (Hospital location : geoLocations) {
            JSONObject feature = new JSONObject();
            feature.put("type", "Feature");

            JSONObject geometry = new JSONObject();
            try {
                geometry = (JSONObject) org.json.simple.JSONValue.parse(geometryToGeoJSON(location.getLocation()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            feature.put("geometry", geometry);

            JSONObject properties = new JSONObject();
            properties.put("name", location.getName());
            properties.put("number", location.getNumber());
            properties.put("street", location.getStreet().getName());
            properties.put("ward", location.getWard().getName());
            properties.put("phone", location.getPhone());
            feature.put("properties", properties);

            features.add(feature);
        }

        featureCollection.put("features", features);

        return featureCollection.toJSONString();
    }

    public void saveGeoLocation(Hospital geoLocation) {
        repo.save(geoLocation);
    } 
    
    public List<Hospital> searchByName(String name) {
       // return repo.findByNameAndPoint(name);
    	return repo.findByNameAndAddress(name);
    }
    
    public List<Hospital> findHospitalsWithinRadius(double latitude, double longitude, double radius) {
        return repo.findHospitalsWithinRadius(latitude, longitude, radius);
    }
}

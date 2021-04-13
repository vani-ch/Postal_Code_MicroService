package rowan.ood.dimicroservice.primetester;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import rowan.ood.dimicroservice.microservice.PostalCodeTester;
import rowan.ood.dimicroservice.microservice.beans.Place;
import rowan.ood.dimicroservice.microservice.beans.PostalCode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostalCodeTesterImpl  implements PostalCodeTester {

    private static final Logger log = LoggerFactory.getLogger(PostalCodeTesterImpl.class);

     JdbcTemplate jdbcTemplate;

    static final String BaseUrl = "http://api.zippopotam.us/us/"; //08028";
    private  Integer postCodeId;
    RestTemplate restTemplate;

    @Autowired
    public PostalCodeTesterImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcTemplate.execute("DROP TABLE places  IF EXISTS");
        String places = " CREATE TABLE places(place_id  SERIAL PRIMARY KEY,place_zipcode VARCHAR(255) UNIQUE, place_name VARCHAR(255), longitude VARCHAR(255), state VARCHAR(255), state_abbreviation VARCHAR(255), latitude VARCHAR(255))";
        jdbcTemplate.execute( places);
        jdbcTemplate.execute("DROP TABLE postalCode  IF EXISTS");
        String postalCode="CREATE TABLE postalCode( post_code_id  SERIAL PRIMARY KEY, post_code VARCHAR(255) NOT NULL,country VARCHAR(255)," +
                " country_abbreviation VARCHAR(255), place_ids VARCHAR(255) NOT NULL)";
        jdbcTemplate.execute(postalCode);

    }

        @Override
    public PostalCode postalCodeTester(String zipCode) {

        boolean isPostalCodeExist = isPostalCodeExist(zipCode);

        if (isPostalCodeExist) {
            log.info(" Postal Code: "+ zipCode+ " is present in DB, So we fetch from DB");

            List<PostalCode> postalCodeList = isPostalCodeRecordExits(zipCode);
             if(postalCodeList.size() >0)   return postalCodeList.get(0);
             else return null;
        } else {
            log.info(" Postal Code: "+ zipCode+ " is not present in DB, SO calling Open API.");
            restTemplate = new RestTemplate();
            // Integer zip = Integer.parseInt(zipCode);
            String url = BaseUrl + zipCode;
            System.out.println("^^^^^^^^^^^^^^^ ****************** URL value: " + url);
            PostalCode response = restTemplate.getForObject(url, PostalCode.class);
            log.info("==== RESTful API Response using Spring RESTTemplate START =======>>>>>>>>>>>>>>>>>>>>");
            log.info(response.toString());
            log.info("==== RESTful API Response using Spring RESTTemplate END =======");
            //    PostalCode code = postalCodeResponseMapper(response);
            //PostalCode postalCode = new PostalCode();
            //postalCode.setPost_code(zipCode);
               createPostalCodeRecord(response);

            return response;
        }

    }

    public boolean isPostalCodeExist(String zipCode){
        final String querystr = String.format("SELECT POST_CODE_ID FROM postalCode WHERE post_code = '%s'", zipCode);
        try{
            postCodeId =jdbcTemplate.queryForObject(querystr,Integer.class);
            if(postCodeId!= null){
                return true;
            }
        }
        catch(Exception e){
            return false;
        }
        return false;
    }
         public List<PostalCode> isPostalCodeRecordExits(String zipCode){

            final String querystr = "SELECT * FROM postalCode WHERE post_code_id =?";

             PostalCode postalCode = null;
             List<Place> placeList = new ArrayList<>();
             List<PostalCode> postalCodeList = new ArrayList<>();
            // First check if the provided string is in the database
            try {
                log.info(" before logginf with query for object - posyalcode: ");
                  PostalCode p = (PostalCode) jdbcTemplate.queryForObject(querystr, new Object[]{postCodeId}, new PostalCodeRowMapper());
                  log.info(" logginf with query for object - posyalcode: ", p);
               postalCodeList.add(p);
                    String places_ids = p.getPlace_ids();
                    String[] place_arr = places_ids.split(",");

                for(int j=0; j< place_arr.length; j++){

                       final String sql = String.format("SELECT * FROM places WHERE place_zipcode = '%s' and place_id= '%d'", zipCode, Integer.parseInt(place_arr[j]) );
                       Place place = jdbcTemplate.queryForObject(sql, Place.class);
                       placeList.add(place);
                   }

                 p.setPlaces(placeList);

                   log.info(" postalCode obj: ", p);
              //   postalCodeList.add(p);
                return postalCodeList;

            } catch (Exception e) {
                // Not found in the db, so run the Miller Rabin test and store the result in the db
              log.error("Exception while searching postalCode: "+zipCode, e.getMessage());
               }
             return postalCodeList;
      }


       public void createPostalCodeRecord(PostalCode postalCode){

           String querystr = null;

   for(Place place: postalCode.getPlaces()){

       querystr = String.format("INSERT into places(place_zipcode,place_name, longitude, state,state_abbreviation,latitude ) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
               postalCode.getPost_code(),place.getPlace_name(),place.getLongitude(),
               place.getState(), place.getState_abbreviation(), place.getLatitude());
       jdbcTemplate.execute(querystr);
   }

           StringBuilder place_ids = new StringBuilder();
           final String sql = String.format("SELECT * FROM places WHERE place_zipcode = '%s'",postalCode.getPost_code() );
           List<Place> placeList = jdbcTemplate.query(sql, (resultSet, i) -> {
               Place place = new Place();
               place.setPlace_id(resultSet.getInt("place_id"));
               place.setPlace_zipcode(resultSet.getString("place_zipcode"));
               place.setPlace_name(resultSet.getString("place_name"));
               place.setLatitude(resultSet.getString("latitude"));
               place.setState(resultSet.getString("state"));
               place.setState_abbreviation(resultSet.getString("state_abbreviation"));
               place.setLongitude(resultSet.getString("longitude"));

               return place;
           });

           for(int i=0; i< placeList.size(); i++){
               place_ids.append(placeList.get(i).getPlace_id()).append(",");
           }

           querystr = String.format("INSERT into postalCode(post_code,country, country_abbreviation,place_ids ) VALUES ('%s', '%s', '%s', '%s')",
                   postalCode.getPost_code(),postalCode.getCountry(),postalCode.getCountry_abbreviation(), place_ids);
           jdbcTemplate.execute(querystr);



       }

}

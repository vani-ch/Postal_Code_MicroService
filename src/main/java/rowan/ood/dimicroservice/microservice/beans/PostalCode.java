package rowan.ood.dimicroservice.microservice.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

public class PostalCode {

    @JsonProperty("post code")
    @Column

    private String post_code;
    @JsonProperty("country")
    @Column
    private String  country;
    @JsonProperty("country abbreviation")
    @Column
    private String country_abbreviation;
    @JsonProperty("places")
    @Column
    private List<Place> places;

    private Integer postCode_id;
    private  String place_ids;

    public PostalCode(String post_code, String country, String country_abbreviation, List<Place> places) {

        this.post_code = post_code;
        this.country = country;
        this.country_abbreviation = country_abbreviation;
        this.places = places;
    }

    public PostalCode(){

    }

    @Override
    public String toString() {
        return "PostalCode{" +
                "post_code='" + post_code + '\'' +
                ", country='" + country + '\'' +
                ", country_abbreviation='" + country_abbreviation + '\'' +
                ", places='" + places + '\'' +
                '}';
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_abbreviation() {
        return country_abbreviation;
    }

    public void setCountry_abbreviation(String country_abbreviation) {
        this.country_abbreviation = country_abbreviation;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public Integer getPostCode_id() {
        return postCode_id;
    }

    public void setPostCode_id(Integer postCode_id) {
        this.postCode_id = postCode_id;
    }

    public String getPlace_ids() {
        return place_ids;
    }

    public void setPlace_ids(String place_ids) {
        this.place_ids = place_ids;
    }
}

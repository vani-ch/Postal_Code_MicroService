package rowan.ood.dimicroservice.microservice.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Id;

public class Place {
    @Id
    @Column
    private Integer place_id;
    @NotNull
    @Column
    private String place_zipcode;

    @JsonProperty("place name")
    @Column(unique = true)
    private String place_name;
    @JsonProperty("longitude")
    @Column
    private String longitude;
   @JsonProperty("state")
   @Column
   private String state;
   @JsonProperty("state abbreviation")
   @Column
   private String state_abbreviation;
    @JsonProperty("latitude")
    @Column
    private String latitude;

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState_abbreviation() {
        return state_abbreviation;
    }

    public void setState_abbreviation(String state_abbreviation) {
        this.state_abbreviation = state_abbreviation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Integer place_id) {
        this.place_id = place_id;
    }

    public String getPlace_zipcode() {
        return place_zipcode;
    }

    public void setPlace_zipcode(String place_zipcode) {
        this.place_zipcode = place_zipcode;
    }

    public Place(String place_name, String longitude, String state, String state_abbreviation, String latitude ,Integer place_id,String place_zipcode) {
        this.place_name = place_name;
        this.longitude = longitude;
        this.state = state;
        this.state_abbreviation = state_abbreviation;
        this.latitude = latitude;
        this.place_id=place_id;
        this.place_zipcode=place_zipcode;
    }

    public Place(String place_name, String longitude, String state, String state_abbreviation, String latitude ) {
        this.place_name = place_name;
        this.longitude = longitude;
        this.state = state;
        this.state_abbreviation = state_abbreviation;
        this.latitude = latitude;

    }
    public Place(){

    }

    @Override
    public String toString() {
        return "Place{" +
                "place_id='" + place_id + '\'' +
                "place_zipcode='" + place_zipcode + '\'' +
                "place_name='" + place_name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", state='" + state + '\'' +
                ", state_abbreviation='" + state_abbreviation + '\'' +
                ", latitude='" + latitude + '\'' +

                '}';
    }
}

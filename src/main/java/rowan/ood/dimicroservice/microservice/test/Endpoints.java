package rowan.ood.dimicroservice.microservice.test;


/**
 * @author Dr. Baliga
 *
 * Endpoints supported by the web api
 *
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rowan.ood.dimicroservice.microservice.PostalCodeResponse;
import rowan.ood.dimicroservice.microservice.PostalCodeTester;
import rowan.ood.dimicroservice.microservice.Response;

public interface Endpoints {

    @GET("/postal_code")
    Call<PostalCodeResponse> getPostalCodeTestResponse(@Query("zipcode") String zipCode);
   // PostalCodeTester getPostalCodeTestResponse2(@Query("zipcode") String zipCode);

    /* @GET("primetest")
    Call<Response> getPrimeTestResponse(@Query("p") String p);*/
}

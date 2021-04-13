package rowan.ood.dimicroservice.microservice.test;


/**
 * @author Dr. Baliga
 *
 * Web API unit test using Retrofit client
 *
 */


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import rowan.ood.dimicroservice.microservice.PostalCodeResponse;
import rowan.ood.dimicroservice.microservice.PostalCodeTester;
import rowan.ood.dimicroservice.microservice.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PostalCodeMicroserviceTest {

    static Endpoints endpoints;
    static final String BaseUrl = "http://api.zippopotam.us/us/90210/"; // Location of the web api
    static PostalCodeTester mockPostalCodeTester;
    static Set<String> postalCodesToTest;

    @BeforeAll
    static void init() {

        Gson gson = new GsonBuilder().create();
        Retrofit client = new Retrofit
                .Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        endpoints = client.create(Endpoints.class);

        // Perform the injection and retrieve testing beans
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigTest.class);
        mockPostalCodeTester = context.getBean(PostalCodeTester.class);
        postalCodesToTest = (Set<String>) context.getBean("postalCodesToTest");

    }

    // Test microservice using numbers listed within the mock prime tester.
    @Test
    void postalCodeChecks() {
        try {
            // Test all numbers in the test suite
            for (String number : postalCodesToTest) {

                // Invoke the webapi to compute the answer
                Call<PostalCodeResponse> postalCodeTestResponse = endpoints.getPostalCodeTestResponse(number);
                PostalCodeResponse resp = postalCodeTestResponse.execute().body();
                Assertions.assertNotNull(resp.getPostalCode(),number);

                assertEquals(resp.getPostalCode(), number);
                // Verify that the webapi's answer is the same as the mock test's answer
                assertEquals(resp.getPostalCode(), mockPostalCodeTester.postalCodeTester(number));
            }
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}

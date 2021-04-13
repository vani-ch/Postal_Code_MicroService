package rowan.ood.dimicroservice.microservice;

/**
 * @author Dr. Baliga
 *
 * Implements the prime testing microservice. DI is used to inject
 * a prime testing implementation.
 *
 */

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class PostalCodeMicroservice {
    PostalCodeTester postalCodeTester;
    @Autowired
    public PostalCodeMicroservice(PostalCodeTester postalCodeTester) {
        this.postalCodeTester = postalCodeTester;

    }

    @GetMapping("/info")
    public String info() {
        return "The world's best prime testing microservice";
    }

    // http://api.zippopotam.us/us/08028
    @GetMapping("/postal_code")
    public String postalCodeTest(@RequestParam("zipcode") String zipCode) {
        // Use the injected prime tested to test for primality.
        System.out.println(" ********************************  IN controller - Prime Micro Service: ========================================== "+ zipCode);
        return new Gson().toJson(new PostalCodeResponse( postalCodeTester.postalCodeTester(zipCode)));
    }
}
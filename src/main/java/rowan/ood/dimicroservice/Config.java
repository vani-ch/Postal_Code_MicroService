package rowan.ood.dimicroservice.webapi;

/**
 * @author Dr. Baliga
 *
 * DI configuration for the web api
 *
 */

import org.springframework.jdbc.core.JdbcTemplate;
import rowan.ood.dimicroservice.microservice.PostalCodeMicroservice;
import rowan.ood.dimicroservice.microservice.PostalCodeTester;
import rowan.ood.dimicroservice.primetester.PostalCodeTesterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    // H2 database jdbc connector injected by Spring. How convenient!
    @Autowired(required = true)
    private JdbcTemplate jdbcTemplate;

    @Bean
    public PostalCodeTester getPostalCodeTester(){

        return new PostalCodeTesterImpl(jdbcTemplate);
    }

//    @Bean
//    public PrimeTester getPrimeTester() {
//
//        return new PrimeTesterImpl(jdbcTemplate);
//    }

   @Bean
  public PostalCodeMicroservice getPostalCodeMicroService() {
     return new PostalCodeMicroservice(this.getPostalCodeTester());
   }

}

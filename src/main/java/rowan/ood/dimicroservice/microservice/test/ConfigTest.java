package rowan.ood.dimicroservice.microservice.test;

/**
 * @author Dr. Baliga
 *
 * Mock configuration for testing the microservice
 *
 */

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import rowan.ood.dimicroservice.microservice.PostalCodeTester;
import rowan.ood.dimicroservice.microservice.beans.Place;
import rowan.ood.dimicroservice.microservice.beans.PostalCode;

import java.util.*;

import static org.mockito.Mockito.when;


@Configuration
public class ConfigTest {

    static List<Place> placeList=null;
    static List<Place> placeList1=null;
    static List<Place> placeList2=null;


    @Mock
   PostalCodeTester mockPostalCodeTester;
    static {
        placeList= new ArrayList<>();
        placeList1= new ArrayList<>();
        placeList2= new ArrayList<>();

        placeList.add(new Place("Glassboro","-75.1172","New Jersey","NJ","39.7068"));
        placeList1.add(new Place("Charlotte","-80.8577","North Carolina","NC","35.1316"));
        placeList2.add(new Place("Charlotte","-80.9084","North Carolina","NC","35.1714"));

    }

    // Test suite
    static Map<String, PostalCode> tests = new HashMap<>() {{

        put("08028", new PostalCode("08028","United States","US",placeList));
        put("28210", new PostalCode("28210","United States","US",placeList1));
        put("28217", new PostalCode("28217","United States","US",placeList2));


    }};

    @Bean(name="mockPostalCodeTester")
    PostalCodeTester getMockPostalCodeTester() {
        MockitoAnnotations.openMocks(this);
        for (String v : tests.keySet())
            when(mockPostalCodeTester.postalCodeTester(v)).thenReturn(tests.get(v));
        return mockPostalCodeTester;
    }

    // Named bean example
    @Bean(name = "postalCodesToTest")
    @Primary
    public static Set<String> getPostalCodeTester() {
        return tests.keySet();
    }


}

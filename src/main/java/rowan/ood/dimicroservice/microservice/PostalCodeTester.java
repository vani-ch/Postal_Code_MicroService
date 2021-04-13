package rowan.ood.dimicroservice.microservice;

import rowan.ood.dimicroservice.microservice.beans.PostalCode;

public interface PostalCodeTester {
    public PostalCode postalCodeTester(String zipCode);

    // Returns true if the number is a prime number, False otherwise
    // String used a parameter to accommodate testing of arbitrary sized prime numbers
   // boolean isPrime(String number);
}

package rowan.ood.dimicroservice.microservice;

import rowan.ood.dimicroservice.microservice.beans.PostalCode;

public class PostalCodeResponse
{

    private PostalCode postalCode;

    public PostalCodeResponse(){}

    public PostalCodeResponse(PostalCode postalCode) {
        this.postalCode = postalCode;
    }

    public PostalCode getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(PostalCode postalCode) {
        this.postalCode = postalCode;
    }
}

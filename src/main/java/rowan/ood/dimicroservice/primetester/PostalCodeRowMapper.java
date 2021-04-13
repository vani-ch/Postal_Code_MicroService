package rowan.ood.dimicroservice.primetester;

import org.springframework.jdbc.core.RowMapper;
import rowan.ood.dimicroservice.microservice.beans.PostalCode;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostalCodeRowMapper implements RowMapper<PostalCode> {

    @Override
    public PostalCode mapRow(ResultSet resultSet, int i) throws SQLException {
        PostalCode postalCode = new PostalCode();
        postalCode.setPost_code(resultSet.getString("post_code"));
        postalCode.setPostCode_id(resultSet.getInt("post_code_id"));
        postalCode.setCountry(resultSet.getString("country"));
        postalCode.setCountry_abbreviation(resultSet.getString("country_abbreviation"));
        postalCode.setPlace_ids(resultSet.getString("place_ids"));
        return postalCode;
    }
}

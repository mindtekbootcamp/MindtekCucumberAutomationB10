package utilities;

import io.restassured.response.Response;
import org.junit.Assert;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAPITest {

    public static void main(String args[]) throws SQLException {
        /*
        POST Yard
        Request:
            1. URL
            2. Headers
                a. Accept:application/json
                b. Authorization:Token 50c79942251edf8c7fd98c8036480b4f80b84c1d
                c. Content-Type:application/json
            3. Payload
        Response:
            1. Status code
            2. Data
         */

        Response response=given().baseUri("https://elarbridgelogisticsmindtek.space/en-us/api/v2")
                .and().header("Accept","application/json")
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().header("Content-Type","application/json")
                .and().body("{\n" +
                        "    \"location\": \"Yard155\",\n" +
                        "    \"status\": \"active\",\n" +
                        "    \"address\": \"123 abc st\",\n" +
                        "    \"city\": \"New York\",\n" +
                        "    \"state\": \"NY\",\n" +
                        "    \"zip_code\": \"60173\",\n" +
                        "    \"spots\": \"1123\",\n" +
                        "    \"contacts\": []\n" +
                        "}")
                .and().log().all() // Log / Print all details of request
                .when().post("/yards/");

        // Log / Print all details of response
        response.then().log().all();

        response.then().statusCode(201);

        String yardId=response.body().jsonPath().getString("id");

        /*
        GET Call
        Request:
            1. URL
            2. Headers
                a. Accept:application/json
                b. Authorization:Token 50c79942251edf8c7fd98c8036480b4f80b84c1d
        Response:
            1. Status code
            2. Data
         */

        Response getResponse=given().baseUri("https://elarbridgelogisticsmindtek.space/en-us/api/v2")
                .and().header("Accept","application/json")
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get("/yards/"+yardId+"/");

        getResponse.then().log().all();

        getResponse.then().statusCode(200);

        JDBCUtils.establishDBConnection(ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword"));

        List<Map<String,Object>> data = JDBCUtils.executeQuery("select * from core_yard where id="+yardId);

        // Validating we have one yard
        Assert.assertTrue(data.size()==1);

    }

}

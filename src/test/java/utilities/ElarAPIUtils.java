package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;


public class ElarAPIUtils {

    /*
    .getCall(String endpoint); -> returns response;
    .postCall(String endpoint, Object body); -> returns response(Response data type);
    .patchCall(String endpoint, Object body); -> returns response(Response data type);
     */

    public static Response getCall(String endpoint){
        Response response=given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get(endpoint);
        response.then().log().all();
        return response;
    }

    public static Response getCall(String endpoint, Map<String,Object> queryParams){
        Response response=given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().queryParams(queryParams)
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().log().all()
                .when().get(endpoint);
        response.then().log().all();
        return response;
    }

    public static Response postCall(String endpoint, Object body){
        Response response=given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body(body)
                .and().log().all()
                .when().post(endpoint);
        response.then().log().all();
        return response;
    }

    public static Response patchCall(String endpoint, Object body){
        Response response=given().baseUri(ConfigReader.getProperty("ElarAPIBaseURL"))
                .and().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("Authorization","Token 50c79942251edf8c7fd98c8036480b4f80b84c1d")
                .and().body(body)
                .and().log().all()
                .when().patch(endpoint);
        response.then().log().all();
        return response;
    }

}

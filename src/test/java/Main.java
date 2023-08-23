import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.matcher.RestAssuredMatchers.*;
import pages.BasePage;
//import io.restassured.module.jsv.JsonSchemaValidator.*;
//import io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import static io.restassured.RestAssured.when;

import static io.restassured.RestAssured.baseURI;

public class Main {
    private BasePage basePage = new BasePage();
    @BeforeClass
    public static void setUp(){
        RestAssured.baseURI = "http://3.11.77.136";
    }

    @Test
    public void checkEndpoint(){
        Response response = RestAssured.get(basePage.generatePath());
        response.then().assertThat().statusCode(404);
    }

}
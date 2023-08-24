import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.matcher.RestAssuredMatchers.*;
import pages.BasePage;
//import io.restassured.module.jsv.JsonSchemaValidator.*;
//import io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.when;

import static io.restassured.RestAssured.baseURI;

public class Main {
    private BasePage basePage = new BasePage();

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://3.11.77.136/";
    }

    @Test
    public void checkEndpoint() {
        Response response = RestAssured.get(basePage.generatePath());
        response.then().assertThat().statusCode(404);
    }

    @Test
    public void loginFunctionality() {
        CookieFilter cookieFilter = new CookieFilter();
        Map<String, String> loginDetails = new HashMap<String, String>();
        loginDetails.put("back", "my-account");
        loginDetails.put("email", "hhbrother@gmail.com");
        loginDetails.put("password", "password");
        loginDetails.put("submitLogin", "1");
        Response response = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("back", "my-account")
                .formParams(loginDetails)
                .when()
                .post("index.php");
        System.out.println(response.statusCode());
        System.out.println(cookieFilter.getCookieStore());
        Response getMyAccount = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "my-account")
                .get("index.php");
        System.out.println(getMyAccount.getBody().prettyPrint());
        System.out.println(cookieFilter.getCookieStore());
        Assert.assertEquals("My account", getMyAccount.body().htmlPath().getString("html.head.title"));
    }

    @Test
    public void loginNotValid() {
        CookieFilter cookieFilter = new CookieFilter();
        Map<String, String> loginDetails = new HashMap<String, String>();
        loginDetails.put("back", "my-account");
        loginDetails.put("email", "failtest@mail.com");
        loginDetails.put("password", "failtest");
        loginDetails.put("submitLogin", "1");
        Response response = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "authentication")
                .queryParam("back", "my-account")
                .formParams(loginDetails)
                .when()
                .post("index.php");
        System.out.println(response.statusCode());
        System.out.println(cookieFilter.getCookieStore());
        Response getMyAccount = RestAssured
                .given().filter(cookieFilter)
                .queryParam("controller", "my-account")
                .get("index.php");
        System.out.println(getMyAccount.getBody().prettyPrint());
        System.out.println(cookieFilter.getCookieStore());
        Assert.assertEquals("Login", getMyAccount.body().htmlPath().getString("html.head.title"));
    }
}
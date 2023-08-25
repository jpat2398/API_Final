package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import pages.BasePage;

public class InvalidURLTest {
    private BasePage basePage = new BasePage();
    @Test
    public void invalidURLTest() {

        Response response = RestAssured.get(basePage.generatePath());
        response.then().assertThat().statusCode(404);
        System.out.println("Status code: 404. Page not found");
    }
}

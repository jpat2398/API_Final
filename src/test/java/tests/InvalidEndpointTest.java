package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pages.BasePage;

public class InvalidEndpointTest {

    private BasePage basePage = new BasePage();
    private static final Logger logger = LogManager.getLogger(InvalidEndpointTest.class);

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://3.11.77.136/";
    }

    @Before
    public void logStep (){
        logger.info("Tests started....");}

    @Test
    public void invalidURLTest() {
        Response response = RestAssured.get(basePage.generatePath());

        //Assert that page is not found.
        response.then().assertThat().statusCode(404);
        System.out.println("Status code: 404. Page not found");
    }

    @After
    public void logEnd(){
        logger.info("Tests Ended.....");
    }

}

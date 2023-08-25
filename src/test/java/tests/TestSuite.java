package tests;

import io.restassured.RestAssured;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RegistrationTests.class, InvalidURLTest.class, CartTests.class, LoginTests.class})
public class TestSuite {
    private static final Logger logger = LogManager.getLogger(TestSuite.class);


    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://3.11.77.136/";
    }

    @Before
            public void logStep (){
            logger.info("Tests started....");
    }
    @After
    public void logEnd(){
        logger.info("Tests Ended.....");
    }
    @AfterClass
    public static void apiTestEnd(){
        System.out.println("Successful testing of the API!");
    }

}

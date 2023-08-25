package tests;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import pages.CartPage;
import pages.HomePage;

public class ProducsToCartTest {

    private HomePage homePage = new HomePage();
    private CartPage cartPage = new CartPage();
    private static final Logger logger = LogManager.getLogger(ProducsToCartTest.class);

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://3.11.77.136/";
    }

    @Before
    public void logStep() {
        logger.info("Tests started....");
    }

    @Test
    public void addItemToCartTest() {
        CookieFilter cookieFilter = new CookieFilter();

        homePage.productGetRequest(cookieFilter, "**.findAll { it.@name == 'token' }.@value");

        Response cartPost = RestAssured.given()
                .filter(cookieFilter)
                .queryParam("controller", "cart")
                .formParams(cartPage.cartItemFormData("1"))
                .when()
                .post("index.php");

        //Assert status code 200.
        cartPost.then().assertThat().statusCode(200);
        String cartItem = cartPost.body().htmlPath().getString("**.findAll { it.@class == 'cart-products-count' }");
        String cartItemQuantityString = cartItem.replace("(", "").replace(")", "");
        int cartItemQuantity = Integer.parseInt(cartItemQuantityString);

        //Assert for basket item.
        Assert.assertTrue(cartItemQuantity > 0);
        System.out.printf("Test passed. There are %s item(s) in the basket.", cartItemQuantity);
    }

    @Test
    public void addMultipleItemsToCartTest() {
        CookieFilter cookieFilter = new CookieFilter();

        homePage.productGetRequest(cookieFilter, "**.findAll { it.@name == 'token' }.@value");

        Response cartPost = RestAssured.given()
                .filter(cookieFilter)
                .queryParam("controller", "cart")
                .formParams(cartPage.cartItemFormData("2"))
                .when()
                .post("index.php");

        //Assert status code 200.
        cartPost.then().assertThat().statusCode(200);
        String cartItem = cartPost.body().htmlPath().getString("**.findAll { it.@class == 'cart-products-count' }");
        String cartItemQuantityString = cartItem.replace("(", "").replace(")", "");
        int cartItemQuantity = Integer.parseInt(cartItemQuantityString);

        //Assert multiple items are in basket.
        Assert.assertTrue(cartItemQuantity > 1);
        System.out.printf("Test passed. There are %s item(s) in the basket.", cartItemQuantity);

    }

    @After
    public void logEnd(){
        logger.info("Tests Ended.....");
    }
}

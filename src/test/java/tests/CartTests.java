package tests;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pages.CartPage;
import pages.HomePage;

public class CartTests {
    private CartPage cartPage = new CartPage();
    private HomePage homePage = new HomePage();

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
        cartPost.then().assertThat().statusCode(200);
        String cartItem = cartPost.body().htmlPath().getString("**.findAll { it.@class == 'cart-products-count' }");
        String cartItemQuantityString = cartItem.replace("(", "").replace(")", "");
        int cartItemQuantity = Integer.parseInt(cartItemQuantityString);
        Assert.assertTrue(cartItemQuantity > 0);
        System.out.printf("Test passed. There are %s item(s) in the basket.", cartItemQuantity);
    }

    @Test
    public void addMultipleItemsToCartTest() {
        CookieFilter cookieFilter = new CookieFilter();

        homePage.productGetRequest(cookieFilter, "**.findAll { it.@name == 'token' }.@value");

        //Get POST Response
        Response cartPost = RestAssured.given()
                .filter(cookieFilter)
                .queryParam("controller", "cart")
                .formParams(cartPage.cartItemFormData("2"))
                .when()
                .post("index.php");
        cartPost.then().assertThat().statusCode(200);
        String cartItem = cartPost.body().htmlPath().getString("**.findAll { it.@class == 'cart-products-count' }");
        String cartItemQuantityString = cartItem.replace("(", "").replace(")", "");
        int cartItemQuantity = Integer.parseInt(cartItemQuantityString);
        Assert.assertTrue(cartItemQuantity > 0);
        System.out.printf("Test passed. There are %s item(s) in the basket.", cartItemQuantity);


    }
}

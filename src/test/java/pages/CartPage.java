package pages;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class CartPage {

    HomePage homePage = new HomePage();
    public Map cartItemFormData(String quantity) {

        Map<String, String> cartAdditionDetails = new HashMap<String, String>();
        cartAdditionDetails.put("token", homePage.getToken());
        cartAdditionDetails.put("id_product", "1");
        cartAdditionDetails.put("id_customization", "0");
        cartAdditionDetails.put("group[1]", "1");
        cartAdditionDetails.put("group[3]", "13");
        cartAdditionDetails.put("qty", quantity);
        cartAdditionDetails.put("add", "1");
        cartAdditionDetails.put("action", "update");
    return cartAdditionDetails;
    }
}

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest implements ConstantsLinks{
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final Date date = new Date();

    @Test
    @Step
    void getUsersTest() {
        Response response =
                given().log().uri()
                        .get(GET_PEOPLE_URL)
                        .then().extract().response();

        JsonPath body = response.getBody().jsonPath();
        List<String> usersEmails = body.getList("data.email");

        assertEquals(usersEmails.size(), 6);
        System.out.println("YES 6 USERS");
    }

    @Test
    @Step
    void findUsersTest() {
        given().log().uri()
                .get(GET_PEOPLE_URL)
                .then().assertThat().body("data.email", hasItems("george.bluth@reqres.in", "emma.wong@reqres.in"));
        System.out.println("USERS FIND");
    }

    @Test
    @Step
    void registerNewUserTest() {
        Response response = given().log().uri()
                .contentType("application/json").body(USER_JSON)
                .when().post(ConstantsLinks.POST_CREATE_USER_URL)
                .then().log().body().extract().response();

        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 201);
        System.out.println("USER CREATED IS " + dateFormat.format(date));
    }
}

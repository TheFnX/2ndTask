package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CrudItemTest {
    @Test
    public void verifyCreateProjectJsonObject(){

        JSONObject body = new JSONObject();
        body.put("Content", "BetoCrud");
        //Create
        Response response = given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .body(body.toString())
                .log().all()
        .when()
                .post("https://todo.ly/api/items.json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("BetoCrud"));

        int idItem = response.then().extract().path("Id");


        //Read
        response = given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .log().all()
                .when()
                .get("https://todo.ly/api/items/"+idItem+".json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("BetoCrud"));

        //Update
        body.put("Content", "NewBetoCrud");
        response = given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .body(body.toString())
                .log().all()
                .when()
                .put("https://todo.ly/api/items/"+idItem+".json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("NewBetoCrud"));

        //Delete
        response = given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .log().all()
                .when()
                .delete("https://todo.ly/api/items/"+idItem+".json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("NewBetoCrud"));
    }

}

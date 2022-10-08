package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CrudProjectTest {

    @Test
    public void verifyCreateProjectJsonObject(){

        JSONObject body = new JSONObject();
        body.put("Content", "whenDo.apk");
        body.put("Icon",10);
        //Create
        Response response = given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .body(body.toString())
                .log().all()
        .when()
                .post("https://todo.ly/api/projects.json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("whenDo.apk"))
                .body("Icon", equalTo(10));

        int idProject= response.then().extract().path("Id");


        //Read
        response = given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .log().all()
        .when()
                .get("https://todo.ly/api/projects/"+idProject+".json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("whenDo.apk"))
                .body("Icon", equalTo(10));

        //Update
        body.put("Content", "whenDoCrud.apk");
        body.put("Icon", "15");
        response = given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .body(body.toString())
                .log().all()
        .when()
                .put("https://todo.ly/api/projects/"+idProject+".json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("whenDoCrud.apk"))
                .body("Icon", equalTo(15));

        //Delete
        response = given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .log().all()
        .when()
                .delete("https://todo.ly/api/projects/"+idProject+".json");

        response.then()
                .log().all()
                .statusCode(200)
                .body("Content", equalTo("whenDoCrud.apk"))
                .body("Icon", equalTo(15));

    }
}

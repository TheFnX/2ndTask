package basicRestAssured;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreateProjectTest {

    @Test
    public void verifyCreateProject(){
        given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .body("{\n" +
                        "    \"Content\": \"Prueba\",\n" +
                        "    \"Icon\": 4\n" +
                        "}")
                .log().all()
        .when()
                .post("https://todo.ly/api/projects.json")
        .then()
                .log().all();
    }

    @Test
    public void verifyCreateProjectJsonObject(){

        JSONObject body = new JSONObject();
        body.put("Content", "BetoPrueba");
        body.put("Icon",1);
        given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .body(body.toString())
                .log().all()
        .when()
                .post("https://todo.ly/api/projects.json")
        .then()
                .log().all();
    }

    @Test
    public void verifyCreateProjectFile(){
        String jsonFile = new File("").getAbsolutePath()+"/src/test/resources/createProject.json";
        given()
                .auth()
                .preemptive()
                .basic("baldubeto@gmail.com","123456789")
                .body(new File(jsonFile))
                .log().all()
        .when()
                .post("https://todo.ly/api/projects.json")
        .then()
                .log().all();
    }
}

package basicRestAssured;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateProjectCheckTest {

    @Test
    public void verifyCreateProjectJsonObject(){

        JSONObject body = new JSONObject();
        body.put("Content", "BetoNewTest");
        body.put("Icon",5);

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
                .body("Content", equalTo("BetoNewTest"))
                .body("Icon", equalTo(5));
    }

    @Test
    public void verifyCreateProjectSchema(){

        JSONObject body = new JSONObject();
        body.put("Content", "lastTest");
        body.put("Icon",7);

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
                .body("Content", equalTo("lastTest"))
                .body("Icon", equalTo(7));

        // Schema verification
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(
                        SchemaVersion.DRAFTV4
                ).freeze()).freeze();

        response.then()
                .body(matchesJsonSchemaInClasspath("schemaCreateResponse2.json")
                        .using(jsonSchemaFactory));

        int id = response.then().extract().path("Id");
        String nameProject = response.then().extract().path("Content");

        System.out.println("****** ID: "+id);
        System.out.println("****** NAME PROJECT: "+nameProject);

    }

}

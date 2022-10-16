package testClean;

import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.ApiConfiguration;

import static org.hamcrest.Matchers.equalTo;

public class ItemTest {
    Response response;
    JSONObject body = new JSONObject();
    RequestInfo requestInfo = new RequestInfo();
    int idProject;

    @BeforeEach
    @DisplayName("Create Project")
    public void init() {
        // Create Project
        body.put("Content", "Project whit Item");
        body.put("Icon", 10);
        requestInfo.setUrl(ApiConfiguration.CREATE_PROJECT);
        requestInfo.setBody(body.toString());
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().body("Content", equalTo(body.get("Content"))).statusCode(200);
        idProject = response.then().extract().path("Id");
    }

    @Test
    @DisplayName("CRUD for Items")
    public void verifyCRUDItem() {
        // Create
        body.put("Content", "Item Name");
        body.put("ProjectId", idProject);
        body.put("Priority", 3);
        requestInfo.setUrl(ApiConfiguration.CREATE_ITEM);
        requestInfo.setBody(body.toString());
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().body("Content", equalTo(body.get("Content"))).statusCode(200);
        int idItem = response.then().extract().path("Id");

        // Update
        body.put("Content", "Item Update");
        body.put("Priority", 1);
        requestInfo.setUrl(String.format(ApiConfiguration.UPDATE_ITEM, idItem));
        requestInfo.setBody(body.toString());
        response = FactoryRequest.make("put").send(requestInfo);
        response.then().body("Content", equalTo(body.get("Content"))).statusCode(200);

        // Read
        requestInfo.setUrl(String.format(ApiConfiguration.READ_ITEM, idItem));
        requestInfo.setBody(body.toString());
        response = FactoryRequest.make("get").send(requestInfo);
        response.then().body("Content", equalTo(body.get("Content"))).statusCode(200);

        // Delete
        requestInfo.setUrl(String.format(ApiConfiguration.DELETE_ITEM, idItem));
        requestInfo.setBody(body.toString());
        response = FactoryRequest.make("delete").send(requestInfo);
        response.then().body("Content", equalTo(body.get("Content"))).statusCode(200);
    }
}


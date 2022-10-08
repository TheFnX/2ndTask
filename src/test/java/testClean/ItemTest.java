package testClean;

import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import util.ApiConfiguration;

import static org.hamcrest.Matchers.equalTo;

public class ItemTest {
    Response response;
    JSONObject body = new JSONObject();
    RequestInfo requestInfo = new RequestInfo();

    @Test
    public void verifyCRUDItem() {
        body.put("Content", "Cato2022");
        requestInfo.setUrl(ApiConfiguration.CREATE_ITEM);
        requestInfo.setBody(body.toString());
        //Create
        response = FactoryRequest.make("post").send(requestInfo);
        response.then().body("Content", equalTo(body.get("Content"))).statusCode(200);
        int idItem = response.then().extract().path("Id");
        //Update
        body.put("Content","CatoUpdated2022");
        requestInfo.setUrl(String.format(ApiConfiguration.UPDATE_ITEM,idItem));
        requestInfo.setBody(body.toString());
        response = FactoryRequest.make("put").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);
        //Read
        requestInfo.setUrl(String.format(ApiConfiguration.READ_ITEM,idItem));
        requestInfo.setBody(body.toString());
        response = FactoryRequest.make("get").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);
        //Delete
        requestInfo.setUrl(String.format(ApiConfiguration.DELETE_ITEM,idItem));
        requestInfo.setBody(body.toString());
        response = FactoryRequest.make("delete").send(requestInfo);
        response.then().body("Content",equalTo(body.get("Content"))).statusCode(200);
    }
}

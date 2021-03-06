/* 
 * Copyright (C) 2019 Yannick
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package rest;

import domain.User;
import domain.enums.Language;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Yannick
 */
public class UserResourceIT {
    
    @Before
    public void setUp() {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/kwetter/api/";
    }
    
    @Test
    public void getUsers() {
        given()
        .when()
            .get("/users")
        .then()
            .statusCode(200);
    }
    
    
    @Test
    public void addUser() {
        User user = new User("Frans", "test", Language.Dutch);
        
        given()
            .contentType("application/json")
            .body(user)
        .when()
            .post("/users")
        .then()
            .statusCode(201);
    }
    
    @Test
    public void getUserById() {
        
        String json = get("/users").asString();
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String uuid = (String) jsonObject.get("uuid");
        
        String user = get("/users/" + uuid).asString();
        JSONObject userJsonObject = new JSONObject(user);
        
        Assert.assertEquals(uuid, userJsonObject.get("uuid"));
    }
    
    @Test
    public void duplicateUserName() {
        User user1 = new User("Harry", "test1", Language.Dutch);
        User user2 = new User("Harry", "test2", Language.English);
        
        given()
            .contentType("application/json")
            .body(user1)
        .when()
            .post("/users")
        .then()
            .statusCode(200);
        
        given()
            .contentType("application/json")
            .body(user2)
        .when()
            .post("/users")
        .then()
            .statusCode(400);
    }
    
    
}

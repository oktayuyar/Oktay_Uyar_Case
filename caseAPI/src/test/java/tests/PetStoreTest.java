package tests;

import API.listeners.TestListener;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import API.base.BaseTest;
import API.model.Category;
import API.model.Pet;
import API.model.Tag;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Listeners({ TestListener.class })
@Epic("Pet Store API Automation")
@Feature("Check Pet Store CRUD Operations")
public class PetStoreTest extends BaseTest {

    private static final long PET_ID = (int)(Math.random() * 900) + 100;

    @Test(priority = 1, description = "Create pet with valid parameters")
    @Story("Create pet with valid parameters.")
    public void createPet() {
        Category category = new Category(1, "Dogs");
        Tag tag1 = new Tag(101, "Bulldog");
        Tag tag2 = new Tag(102, "Puppy");

        Pet pet = new Pet(
                PET_ID,
                category,
                "Tommy",
                Arrays.asList("http://test.com/dog", "http://test.com/dog1"),
                Arrays.asList(tag1, tag2),
                "available"
        );

        given()
                .spec(requestSpec)
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("name", equalTo("Tommy"))
                .body("status", equalTo("available"))
                .body("category.name", equalTo("Dogs"))
                .body("tags[0].name", equalTo("Bulldog"));
    }


    @Test(priority = 2, description = "Get created pet")
    @Story("Get created pet.")
    public void getPet() {
        given()
                .spec(requestSpec)
                .when()
                .get("/pet/" + PET_ID)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo((int) PET_ID))
                .body("category.name", equalTo("Dogs"))
                .body("tags[1].name", equalTo("Puppy"));
    }

    @Test(priority = 3, description = "Update created pet")
    @Story("Update created pet.")
    public void updatePet() {
        Category category = new Category(2, "Cats");
        Tag tag = new Tag(201, "Persian");

        Pet updatedPet = new Pet(
                PET_ID,
                category,
                "Kitty",
                Arrays.asList("http://photo.com/cat"),
                Arrays.asList(tag),
                "sold"
        );

        given()
                .spec(requestSpec)
                .body(updatedPet)
                .when()
                .put("/pet")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("name", equalTo("Kitty"))
                .body("status", equalTo("sold"))
                .body("category.name", equalTo("Cats"))
                .body("tags[0].name", equalTo("Persian"));
    }

    @Test(priority = 4, description = "Delete created pet")
    @Story("Delete created pet.")
    public void deletePet() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/pet/" + PET_ID)
                .then()
                .statusCode(200);
    }


    @Test(priority = 4, description = "Get pet with invalid pet id")
    @Story("Get pet with invalid pet id.")
    public void getPetNotFound() {
        given()
                .spec(requestSpec)
                .when()
                .get("/pet/" + "9999999999")
                .then()
                .statusCode(404);
    }



}


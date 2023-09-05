package pl.redhat.samples.quarkus.person;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import pl.redhat.samples.quarkus.person.model.Gender;
import pl.redhat.samples.quarkus.person.model.Person;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class PersonResourceTests {

    @Test
    void getPersons() {
        List<Person> persons = given().when().get("/persons")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getList(".", Person.class);
        assertEquals(persons.size(), 6);
    }

    @Test
    void getPersonById() {
        Person person = given()
                .pathParam("id", 1)
                .when().get("/persons/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body().as(Person.class);
        assertNotNull(person);
        assertEquals(1L, person.id);
    }

    @Test
    void newPersonAdd() {
        Person newPerson = new Person();
        newPerson.age = 22;
        newPerson.name = "TestNew";
        newPerson.gender = Gender.FEMALE;
        Person person = given()
                .body(newPerson)
                .contentType(ContentType.JSON)
                .when().post("/persons")
                .then()
                .statusCode(200)
                .extract()
                .body().as(Person.class);
        assertNotNull(person);
        assertNotNull(person.id);
    }
}

package com.techpro.upc.reviews_service.bdd;

import com.techpro.upc.reviews_service.domain.model.aggregates.Review;
import com.techpro.upc.reviews_service.infrastructure.persistence.jpa.repositories.ReviewRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReviewSteps {

    @Autowired
    private ReviewRepository reviewRepository;

    private List<Review> result;

    @Given("existen reseñas registradas en el sistema")
    public void existenResenasRegistradas() {
        Review r1 = new Review();
        r1.setPatientId(1L);
        r1.setPsychologistId(10L);
        r1.setComment("Excelente atención");
        r1.setRating(5);

        Review r2 = new Review();
        r2.setPatientId(2L);
        r2.setPsychologistId(20L);
        r2.setComment("Muy buena experiencia");
        r2.setRating(4);

        reviewRepository.save(r1);
        reviewRepository.save(r2);
    }

    @When("el cliente solicita la lista de reseñas")
    public void elClienteSolicitaLaListaDeResenas() {
        result = reviewRepository.findAll();
    }

    @Then("el sistema devuelve una lista con todas las reseñas registradas")
    public void elSistemaDevuelveTodasLasResenas() {
        assertEquals(2, result.size());
    }
}

package com.techpro.upc.reviews_service.interfaces.rest;

import com.techpro.upc.reviews_service.domain.model.commands.DeleteReviewCommand;
import com.techpro.upc.reviews_service.domain.model.queries.GetAllReviewsQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewByIdQuery;
import com.techpro.upc.reviews_service.domain.model.queries.GetReviewsByPsychologistIdQuery;
import com.techpro.upc.reviews_service.domain.model.services.ReviewCommandService;
import com.techpro.upc.reviews_service.domain.model.services.ReviewQueryService;
import com.techpro.upc.reviews_service.interfaces.rest.resources.CreateReviewResource;
import com.techpro.upc.reviews_service.interfaces.rest.resources.ReviewResource;
import com.techpro.upc.reviews_service.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.techpro.upc.reviews_service.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/reviews", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Reviews", description = "Endpoints for managing psychologist reviews")
public class ReviewsController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    public ReviewsController(ReviewCommandService reviewCommandService, ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates a new psychologist review from a patient.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ReviewResource> createReview(@RequestBody CreateReviewResource resource) {
        var command = CreateReviewCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = reviewCommandService.handle(command);
        if (result.isEmpty()) return ResponseEntity.badRequest().build();
        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(result.get());
        return new ResponseEntity<>(reviewResource, HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Get review by ID", description = "Fetch a specific review by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review found"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public ResponseEntity<ReviewResource> getReviewById(@PathVariable Long reviewId) {
        var query = new GetReviewByIdQuery(reviewId);
        var result = reviewQueryService.handle(query);
        return result.map(review ->
                ResponseEntity.ok(ReviewResourceFromEntityAssembler.toResourceFromEntity(review))
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List all reviews", description = "Returns a list of all reviews in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews found"),
            @ApiResponse(responseCode = "404", description = "No reviews found")
    })
    public ResponseEntity<List<ReviewResource>> getAllReviews() {
        var result = reviewQueryService.handle(new GetAllReviewsQuery());
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        var resources = result.stream()
                .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/psychologist/{psychologistId}")
    @Operation(
            summary = "Get all reviews by psychologist ID",
            description = "Retrieve all reviews written for a specific psychologist."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reviews found"),
            @ApiResponse(responseCode = "404", description = "No reviews found for this psychologist")
    })
    public ResponseEntity<List<ReviewResource>> getReviewsByPsychologistId(@PathVariable Long psychologistId) {
        var query = new GetReviewsByPsychologistIdQuery(psychologistId);
        var reviews = reviewQueryService.handle(query);
        if (reviews.isEmpty()) return ResponseEntity.notFound().build();

        var resources = reviews.stream()
                .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete review", description = "Deletes a review by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        var command = new DeleteReviewCommand(reviewId);
        reviewCommandService.handle(command);
        return ResponseEntity.ok("Review deleted successfully");
    }


}

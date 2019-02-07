package com.deltax.imdb.ws.producer;

import com.deltax.imdb.constants.GeneralConstants;
import com.deltax.imdb.controller.AbstractController;
import com.deltax.imdb.dto.request.MovieCreationBean;
import com.deltax.imdb.exception.DataException;
import com.deltax.imdb.services.movie.MovieService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/rest/movie")
public class MovieWS extends AbstractController {

    @Autowired
    private MovieService movieService;

    @ApiOperation("Adds a new movie information for existing actors and producers")
    @PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addMovie(@Valid @RequestBody MovieCreationBean movieCreationBean)
    {
        try
        {
            return buildResponse(movieService.addMovie(movieCreationBean),"Movie added successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation("Updates movie information")
    @PutMapping(value = "/update/{movieId}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> updateMovie(@Min(value = 1,message = "Movie id should be greater than or equal to 1")
                                         @PathVariable("movieId")int movieId,@Valid @RequestBody MovieCreationBean movieCreationBean)
    {
        try
        {
            return buildResponse(movieService.updateMovie(movieCreationBean,movieId),"Movie updated successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation(value = "Fetches movie information and actors associated to it")
    @GetMapping(value = "/fetch-by-movie/{movieId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> fetchMovieById(@Min(value = 1,message = GeneralConstants.MOVIE_ID)
                                            @PathVariable("movieId")int movieId)
    {
        try
        {
            return buildResponse(movieService.fetchAllMovieInfo(movieId),"List of all actors and movie information fetched successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation("Fetches list of all movie data")
    @GetMapping(value = "/fetch-all",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> fetchAllMovies(@RequestParam(value = "movieName",required = false)String movieName)
    {
        try
        {
            return buildResponse(movieService.fetchAll(movieName),"List of movies fetched successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }

    @ApiOperation(value = "Soft deletes a movie")
    @DeleteMapping(value = "/delete/{movieId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deleteMovie(@Min(value = 1,message = GeneralConstants.MOVIE_ID)
                                         @PathVariable("movieId")int movieId)
    {
        try
        {
            return buildResponse(movieService.deleteMovie(movieId),"Movie deleted successfully");
        }
        catch (DataException e)
        {
            return buildError(e);
        }
    }
}

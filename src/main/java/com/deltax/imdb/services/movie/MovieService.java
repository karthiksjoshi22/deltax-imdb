package com.deltax.imdb.services.movie;

import com.deltax.imdb.dto.request.MovieCreationBean;
import com.deltax.imdb.dto.response.AggregateMovieResponse;
import com.deltax.imdb.dto.response.MovieResponseBean;
import com.deltax.imdb.exception.DataException;

import java.util.List;

public interface MovieService {

    long addMovie(MovieCreationBean movieCreationBean)throws DataException;

    boolean updateMovie(MovieCreationBean movieCreationBean,int movieId)throws DataException;

    AggregateMovieResponse fetchAllMovieInfo(int movieId)throws DataException;

    List<MovieResponseBean> fetchAll(String searchParam)throws DataException;

    boolean deleteMovie(int movieId)throws DataException;
}

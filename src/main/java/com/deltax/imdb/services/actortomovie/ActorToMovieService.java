package com.deltax.imdb.services.actortomovie;

import com.deltax.imdb.dto.response.ActorResponse;
import com.deltax.imdb.exception.DataException;

import java.util.List;

public interface ActorToMovieService {

    long associateActorToMovie(int movieId,int actorId)throws DataException;

    boolean deleteAssociation(int movieId)throws DataException;

    List<ActorResponse> fetchActorByMovie(int movieId)throws DataException;
}

package com.deltax.imdb.services.actortomovie;

import com.deltax.imdb.constants.GeneralConstants;
import com.deltax.imdb.db.model.actor.Actor;
import com.deltax.imdb.db.model.actortomovie.ActorToMovie;
import com.deltax.imdb.db.model.actortomovie.ActorToMovieRepository;
import com.deltax.imdb.db.model.movie.Movie;
import com.deltax.imdb.dto.response.ActorResponse;
import com.deltax.imdb.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ActorToMovieServiceImpl implements ActorToMovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorToMovieServiceImpl.class);

    @Autowired
    private ActorToMovieRepository actorToMovieRepository;

    @Override
    public long associateActorToMovie(int movieId, int actorId) throws DataException
    {
        try
        {
            LOGGER.info("Associating actor to movie");
            ActorToMovie actorToMovie = new ActorToMovie();
            Actor actor = new Actor();
            actor.setActorId(actorId);
            actorToMovie.setActor(actor);

            Movie movie = new Movie();
            movie.setMovieId(movieId);
            actorToMovie.setMovie(movie);
            actorToMovieRepository.save(actorToMovie);
            return actorToMovie.getAmId();
        }
        catch (DataAccessException e)
        {
            LOGGER.error(GeneralConstants.ERROR,e);
            throw new DataException(GeneralConstants.FAULT,GeneralConstants.SQL_EXCEPTIONS,HttpStatus.INTERNAL_SERVER_ERROR,e.getRootCause().getMessage());
        }
        catch (Exception e)
        {
            LOGGER.error(GeneralConstants.ERROR,e);
            throw new DataException(GeneralConstants.FAULT,GeneralConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @Transactional(rollbackOn = DataException.class)
    @Override
    public boolean deleteAssociation(int movieId) throws DataException
    {
        try
        {
            LOGGER.info("Deleting actor to movie association");
            int result = actorToMovieRepository.delete(movieId);
            return result>1;
        }
        catch (DataAccessException e)
        {
            LOGGER.error(GeneralConstants.ERROR,e);
            throw new DataException(GeneralConstants.FAULT,GeneralConstants.SQL_EXCEPTIONS,HttpStatus.INTERNAL_SERVER_ERROR,e.getRootCause().getMessage());
        }
        catch (Exception e)
        {
            LOGGER.error(GeneralConstants.ERROR,e);
            throw new DataException(GeneralConstants.FAULT,GeneralConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @Override
    public List<ActorResponse> fetchActorByMovie(int movieId) throws DataException
    {
        try
        {
            LOGGER.info("Fetching list of all actors for a movie");
            List<ActorResponse> actorResponses = actorToMovieRepository.fetchActorsByMovie(movieId);
            if (actorResponses.isEmpty())
            {
                throw new DataException(GeneralConstants.OK,"No actors found for movie",HttpStatus.OK,"Entered movie doesn't have actor data");
            }
            return actorResponses;
        }
        catch (DataAccessException e)
        {
            LOGGER.error(GeneralConstants.ERROR,e);
            throw new DataException(GeneralConstants.FAULT,GeneralConstants.SQL_EXCEPTIONS,HttpStatus.INTERNAL_SERVER_ERROR,e.getRootCause().getMessage());
        }
        catch (DataException e)
        {
            LOGGER.error(GeneralConstants.ERROR,e);
            throw e;
        }
        catch (Exception e)
        {
            LOGGER.error(GeneralConstants.ERROR,e);
            throw new DataException(GeneralConstants.FAULT,GeneralConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}

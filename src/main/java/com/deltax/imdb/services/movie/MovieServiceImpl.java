package com.deltax.imdb.services.movie;

import com.deltax.imdb.constants.GeneralConstants;
import com.deltax.imdb.db.model.file.File;
import com.deltax.imdb.db.model.movie.Movie;
import com.deltax.imdb.db.model.movie.MovieRepository;
import com.deltax.imdb.db.model.producer.Producer;
import com.deltax.imdb.dto.request.MovieCreationBean;
import com.deltax.imdb.dto.response.ActorResponse;
import com.deltax.imdb.dto.response.AggregateMovieResponse;
import com.deltax.imdb.dto.response.MovieResponseBean;
import com.deltax.imdb.exception.DataException;
import com.deltax.imdb.services.actortomovie.ActorToMovieService;
import com.deltax.imdb.utils.NullEmptyUtils;
import com.deltax.imdb.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorToMovieService actorToMovieService;

    @Transactional(rollbackOn = DataException.class)
    @Override
    public long addMovie(MovieCreationBean movieCreationBean) throws DataException
    {
        try
        {
            LOGGER.info("Adding a new movie");
            Movie movie = new Movie();
            Optional<Integer> checkMovie = movieRepository.checkMovieExist(movieCreationBean.getName());
            if (checkMovie.isPresent())
            {
                throw new DataException(GeneralConstants.FAIL,"Movie already exist",HttpStatus.BAD_REQUEST,"Movie with same exist,cannot be duplicated");
            }
            movie.setMovieName(movieCreationBean.getName());
            if (!NullEmptyUtils.isNullorEmpty(movieCreationBean.getPlot()))
            {
                movie.setPlot(movieCreationBean.getPlot());
            }
            Producer producer = new Producer();
            producer.setProducerId(movieCreationBean.getProducerId());
            movie.setProducer(producer);
            if (!NullEmptyUtils.isNullorEmpty(movieCreationBean.getReleaseYear()))
            {
                movie.setReleaseYear(movieCreationBean.getReleaseYear());
            }
            if (!NullEmptyUtils.isNullorEmpty(movieCreationBean.getPosterId()))
            {
                File file = new File();
                file.setFileId(movieCreationBean.getPosterId());
                movie.setPoster(file);
            }
            movieRepository.save(movie);
            /**
             * Iterates over the list of all actors
             */
            if (!NullEmptyUtils.isNullorEmpty(movieCreationBean.getActorIdList()))
            {
                for (Integer actorId : movieCreationBean.getActorIdList())
                {
                    actorToMovieService.associateActorToMovie(movie.getMovieId(),actorId);
                }
            }
            return movie.getMovieId();
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

    @Transactional(rollbackOn = DataException.class)
    @Override
    public boolean updateMovie(MovieCreationBean movieCreationBean, int movieId) throws DataException
    {
        try
        {
            LOGGER.info("Updating movie info");
            int result = movieRepository.updateMovie(movieCreationBean.getName(),movieCreationBean.getPlot(),movieCreationBean.getPosterId(),
                    movieCreationBean.getReleaseYear(),movieId,movieCreationBean.getProducerId());
            if (result<1)
            {
                throw new DataException(GeneralConstants.FAIL,"Updating movie information failed",HttpStatus.BAD_REQUEST,"Invalid movie id");
            }
            actorToMovieService.deleteAssociation(movieId);
            if (!NullEmptyUtils.isNullorEmpty(movieCreationBean.getActorIdList()))
            {
                for (Integer actorId : movieCreationBean.getActorIdList())
                {
                    actorToMovieService.associateActorToMovie(movieId,actorId);
                }
            }
            return true;
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

    @Override
    public AggregateMovieResponse fetchAllMovieInfo(int movieId) throws DataException
    {
        try
        {
            LOGGER.info("Fetching list of all movie info");
            AggregateMovieResponse aggregateMovieResponse = new AggregateMovieResponse();
            Optional<MovieResponseBean> movieResponseBean = movieRepository.fetchByMovieId(movieId);
            if (!movieResponseBean.isPresent())
            {
                throw new DataException(GeneralConstants.FAIL,"Empty data",HttpStatus.OK,"No data found for entered movie");
            }
            aggregateMovieResponse.setMovieResponseBean(movieResponseBean.get());
            List<ActorResponse> actorResponseList = actorToMovieService.fetchActorByMovie(movieId);
            aggregateMovieResponse.setActorResponseList(actorResponseList);
            return aggregateMovieResponse;
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

    @Override
    public List<MovieResponseBean> fetchAll(String searchParam) throws DataException
    {
        try
        {
            LOGGER.info("Fetching list of all movies");
            List<MovieResponseBean> movieResponseBeanList = null;
            /**
             * Fetches movie info based on name
             */
            if (!NullEmptyUtils.isNullorEmpty(searchParam))
            {
                searchParam = ValidationUtil.replaceMetaCharForLike(searchParam);
                movieResponseBeanList = movieRepository.fetchByMovieName(searchParam);
            }
            else
            {
                movieResponseBeanList = movieRepository.fetchAll();
            }
            if (movieResponseBeanList.isEmpty())
            {
                throw new DataException(GeneralConstants.OK,"No data found",HttpStatus.OK,"Empty data");
            }
            return movieResponseBeanList;
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

    @Transactional(rollbackOn = DataException.class)
    @Override
    public boolean deleteMovie(int movieId) throws DataException
    {
        try
        {
            LOGGER.info("Deleting a movie");
            int result = movieRepository.deleteMovie(movieId);
            if (result<1)
            {
                throw new DataException(GeneralConstants.FAIL,"Deletion failed",HttpStatus.BAD_REQUEST,"Movie not present");
            }
            return true;
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

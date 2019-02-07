package com.deltax.imdb.services.actor;

import com.deltax.imdb.constants.GeneralConstants;
import com.deltax.imdb.db.model.actor.Actor;
import com.deltax.imdb.db.model.actor.ActorRepository;
import com.deltax.imdb.dto.request.ActorCreationBean;
import com.deltax.imdb.dto.response.ActorResponse;
import com.deltax.imdb.dto.response.ActorResponseBean;
import com.deltax.imdb.exception.DataException;
import com.deltax.imdb.utils.NullEmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorServiceImpl.class);

    @Autowired
    private ActorRepository actorRepository;

    /**
     * Service to add actors
     * @param actorCreationBean
     * @return
     * @throws DataException
     */
    @Override
    public long addActor(ActorCreationBean actorCreationBean) throws DataException
    {
        try
        {
            LOGGER.info("Adding a new actor");
            Actor actor = new Actor();
            /**
             * Checks whether actor with same exist or not
             */
            Optional<Integer> checkName = actorRepository.checkIfActorExist(actorCreationBean.getName());
            if (checkName.isPresent())
            {
                throw new DataException(GeneralConstants.FAIL,"Actor with same name exist",HttpStatus.BAD_REQUEST,"Actor name should be unique");
            }
            actor.setActorName(actorCreationBean.getName());
            actor.setSex(actorCreationBean.getSex());
            actor.setDob(actorCreationBean.getDob());
            actor.setDeleted(false);
            if (!NullEmptyUtils.isNullorEmpty(actorCreationBean.getName()))
            {
                actor.setBio(actorCreationBean.getBio());
            }
            actorRepository.save(actor);
            return actor.getActorId();
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
            throw new DataException(GeneralConstants.FAIL,GeneralConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @Override
    public Page<ActorResponseBean> fetchAllActors(int page, int size) throws DataException
    {
        try
        {
            LOGGER.info("Fetching list of all actors");
            Page<ActorResponseBean> actorResponseBeans = actorRepository.fetchAllActors(PageRequest.of(page, size));
            if (!actorResponseBeans.hasContent())
            {
                throw new DataException(GeneralConstants.OK,GeneralConstants.EMPTY_DATA,HttpStatus.OK,"No data present in entity");
            }
            return actorResponseBeans;
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
    public boolean updateActor(int actorId,ActorCreationBean actorCreationBean) throws DataException
    {
        try
        {
            LOGGER.info("Updating actor information");
            int result = actorRepository.updateActor(actorId,actorCreationBean.getName(),actorCreationBean.getSex(),
                    actorCreationBean.getDob());
            if (result<1)
            {
                throw new DataException(GeneralConstants.FAIL,"Updation failed",HttpStatus.BAD_REQUEST,"Invalid actor id");
            }
            return result>1;
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
    public boolean deleteActor(int actorId) throws DataException
    {
        try
        {
            LOGGER.info("Deleting a particular actor");
            int result = actorRepository.deleteActor(actorId);
            if (result<1)
            {
                throw new DataException(GeneralConstants.FAIL,"Deletion failed",HttpStatus.BAD_REQUEST,"Invalid actor id");
            }
            return result>1;
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
    public List<ActorResponse> fetchAllActorDropDown() throws DataException
    {
        try
        {
            LOGGER.info("Fetching actor list for drop-down data");
            List<ActorResponse> actorResponseList = actorRepository.fetchDropDown();
            if (actorResponseList.isEmpty())
            {
                throw new DataException(GeneralConstants.OK,"Empty data",HttpStatus.OK,"No data found for actors");
            }
            return actorResponseList;
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
    public ActorResponse fetchById(int actorId) throws DataException
    {
        try
        {
            LOGGER.info("Fetching actor info by id");
            Optional<ActorResponse> actorResponse = actorRepository.fetchById(actorId);
            if (!actorResponse.isPresent())
            {
                throw new DataException(GeneralConstants.OK,"Empty data",HttpStatus.OK,"No data found for actor");
            }
            return actorResponse.get();
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

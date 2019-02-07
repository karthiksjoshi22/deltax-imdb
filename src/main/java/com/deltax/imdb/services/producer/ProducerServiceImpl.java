package com.deltax.imdb.services.producer;

import com.deltax.imdb.constants.GeneralConstants;
import com.deltax.imdb.db.model.producer.Producer;
import com.deltax.imdb.db.model.producer.ProducerRepository;
import com.deltax.imdb.dto.request.ProducerCreationBean;
import com.deltax.imdb.dto.response.ProducerResponseBean;
import com.deltax.imdb.exception.DataException;
import com.deltax.imdb.utils.NullEmptyUtils;
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
public class ProducerServiceImpl implements ProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Autowired
    private ProducerRepository producerRepository;

    @Override
    public long addProducer(ProducerCreationBean producerCreationBean) throws DataException
    {
        try
        {
            LOGGER.info("Adding a new producer");
            Producer producer = new Producer();
            Optional<Integer> checkProducer = producerRepository.checkProducer(producerCreationBean.getName());
            if (checkProducer.isPresent())
            {
                throw new DataException(GeneralConstants.FAIL,"Producer already exist",HttpStatus.BAD_REQUEST,"Producer with same name already exist");
            }
            producer.setProducerName(producerCreationBean.getName());
            producer.setSex(producerCreationBean.getSex());
            if (!NullEmptyUtils.isNullorEmpty(producerCreationBean.getBio()))
            {
                producer.setBio(producerCreationBean.getBio());
            }
            producer.setDob(producerCreationBean.getDob());
            producerRepository.save(producer);
            return producer.getProducerId();
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
    public List<ProducerResponseBean> fetchAllProducer() throws DataException
    {
        try
        {
            LOGGER.info("Fetching list of all producers");
            List<ProducerResponseBean> producerResponseBeans = producerRepository.fetchAll();
            if (producerResponseBeans.isEmpty())
            {
                throw new DataException(GeneralConstants.OK,"Empty data",HttpStatus.OK,"No data present");
            }
            return producerResponseBeans;
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
    public boolean updateProducer(int producerId,ProducerCreationBean producerCreationBean) throws DataException
    {
        try
        {
            LOGGER.info("Updating producer information");
            int result = producerRepository.updateProducer(producerCreationBean.getName(),producerCreationBean.getDob(),
                    producerCreationBean.getSex(),producerId);
            if (result<1)
            {
                throw new DataException(GeneralConstants.FAIL,"Updating producer failed",HttpStatus.BAD_REQUEST,"Invalid producer id");
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

    @Transactional(rollbackOn = DataException.class)
    @Override
    public boolean deleteProducer(int pId) throws DataException
    {
        try
        {
            LOGGER.info("Deleting a producer");
            int result = producerRepository.deleteProducer(pId);
            if (result<1)
            {
                throw new DataException(GeneralConstants.FAIL,"Deleting producer failed",HttpStatus.BAD_REQUEST,"Invalid producer id");
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

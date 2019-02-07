package com.deltax.imdb.services.producer;

import com.deltax.imdb.dto.request.ProducerCreationBean;
import com.deltax.imdb.dto.response.ProducerResponseBean;
import com.deltax.imdb.exception.DataException;

import java.util.List;

public interface ProducerService {

    long addProducer(ProducerCreationBean producerCreationBean)throws DataException;

    List<ProducerResponseBean> fetchAllProducer()throws DataException;

    boolean updateProducer(int producerId,ProducerCreationBean producerCreationBean)throws DataException;

    boolean deleteProducer(int pId)throws DataException;
}

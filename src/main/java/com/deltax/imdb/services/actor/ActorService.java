package com.deltax.imdb.services.actor;

import com.deltax.imdb.dto.request.ActorCreationBean;
import com.deltax.imdb.dto.response.ActorResponse;
import com.deltax.imdb.dto.response.ActorResponseBean;
import com.deltax.imdb.exception.DataException;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ActorService {

    long addActor(ActorCreationBean actorCreationBean)throws DataException;

    Page<ActorResponseBean> fetchAllActors(int page,int size)throws DataException;

    boolean updateActor(int actorId,ActorCreationBean actorCreationBean)throws DataException;

    boolean deleteActor(int actorId)throws DataException;

    List<ActorResponse> fetchAllActorDropDown()throws DataException;

    ActorResponse fetchById(int actorId)throws DataException;
}

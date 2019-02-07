package com.deltax.imdb.db.model.actor;

import com.deltax.imdb.dto.response.ActorResponse;
import com.deltax.imdb.dto.response.ActorResponseBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor,Long> {

    String fetch_all_actors = "SELECT a.actorId AS actorId,a.actorName AS actorName,a.sex AS sex,a.dob AS dob FROM Actor a "
            +   "WHERE a.isDeleted = FALSE ";

    @Query(fetch_all_actors)
    Page<ActorResponseBean> fetchAllActors(Pageable pageable);

    @Modifying
    @Query("UPDATE Actor a SET a.actorName =:actorName,a.sex =:sex,a.dob =:dob WHERE a.actorId =:actorId AND a.isDeleted = FALSE ")
    int updateActor(@Param("actorId")int actorId, @Param("actorName")String actorName, @Param("sex")String sex,
                    @Param("dob")Date dob);

    @Modifying
    @Query("UPDATE Actor a SET a.isDeleted = TRUE WHERE a.actorId =:actorId AND a.isDeleted = FALSE")
    int deleteActor(@Param("actorId")int actorId);

    @Query("SELECT new com.deltax.imdb.dto.response.ActorResponse(a.actorId,a.actorName) "
        +   "FROM Actor a WHERE a.isDeleted = FALSE ")
    List<ActorResponse> fetchDropDown();

    @Query("SELECT a.actorId FROM Actor a WHERE a.actorName =:actorName AND a.isDeleted = FALSE ")
    Optional<Integer> checkIfActorExist(@Param("actorName")String actorName);

    @Query("SELECT new com.deltax.imdb.dto.response.ActorResponse(a.actorId,a.actorName) "
            +   "FROM Actor a WHERE a.isDeleted = FALSE AND a.actorId =:aId ")
    Optional<ActorResponse> fetchById(@Param("aId")int aId);
}

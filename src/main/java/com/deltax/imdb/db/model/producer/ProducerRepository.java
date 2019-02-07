package com.deltax.imdb.db.model.producer;

import com.deltax.imdb.dto.response.ProducerResponseBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer,Long> {

    String fetch_all = "SELECT p.producerId AS producerId,p.producerName AS producerName "
            +   "FROM Producer p WHERE p.isDeleted = FALSE";

    @Query(fetch_all)
    List<ProducerResponseBean> fetchAll();

    @Modifying
    @Query("UPDATE Producer p SET p.producerId =:pId,p.producerName =:name,p.sex =:sex," +
            "p.dob =:dob WHERE p.producerId =:pId")
    int updateProducer(@Param("name")String name, @Param("dob")Date dob,@Param("sex")String sex,
                       @Param("pId")int pId);

    @Modifying
    @Query("UPDATE Producer p SET p.isDeleted = TRUE WHERE p.producerId =:pId AND p.isDeleted = FALSE ")
    int deleteProducer(@Param("pId")int pId);

    @Query("SELECT p.producerId FROM Producer p WHERE p.isDeleted = FALSE AND p.producerName =:pName ")
    Optional<Integer> checkProducer(@Param("pName")String pName);
}

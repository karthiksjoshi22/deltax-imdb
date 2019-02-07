package com.deltax.imdb.db.model.actortomovie;

import com.deltax.imdb.dto.response.ActorResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActorToMovieRepository extends JpaRepository<ActorToMovie,Long> {

    @Modifying
    @Query(value = "DELETE FROM imdb_actor_to_movie WHERE frn_movie_id =:movieId ",nativeQuery = true)
    int delete(@Param("movieId")int movieId);

    @Query("SELECT new com.deltax.imdb.dto.response.ActorResponse(atm.actor.actorId,atm.actor.actorName) " +
            "FROM ActorToMovie atm WHERE atm.movie.movieId =:movieId ")
    List<ActorResponse> fetchActorsByMovie(@Param("movieId")int movieId);
}

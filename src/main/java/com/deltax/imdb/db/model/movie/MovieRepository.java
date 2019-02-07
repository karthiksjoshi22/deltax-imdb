package com.deltax.imdb.db.model.movie;

import com.deltax.imdb.dto.response.MovieResponseBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Modifying
    @Query("UPDATE Movie m SET m.movieName =:name,m.releaseYear =:releaseYear,m.plot =:plot,m.poster.fileId =:posterId," +
            "m.producer.producerId =:producerId WHERE m.movieId =:movieId AND m.isDeleted = FALSE")
    int updateMovie(@Param("name")String name,@Param("plot")String plot,@Param("posterId")int posterId,
                    @Param("releaseYear")String releaseYear,@Param("movieId")int movieId,@Param("producerId")int producerId);

    String fetch_all = "SELECT new com.deltax.imdb.dto.response.MovieResponseBean(m.movieName,m.releaseYear,m.producer.producerId," +
            "m.producer.producerName) FROM Movie m WHERE m.isDeleted = FALSE ";

    String fetch_by_id = "AND m.movieId =:movieId ";

    String fetch_by_movie_name = "AND m.movieName LIKE :searchParam% ";

    @Query(fetch_all)
    List<MovieResponseBean> fetchAll();

    @Query(fetch_all + fetch_by_movie_name)
    List<MovieResponseBean> fetchByMovieName(@Param("searchParam")String searchParam);

    @Query(fetch_all + fetch_by_id)
    Optional<MovieResponseBean> fetchByMovieId(@Param("movieId")int movieId);

    @Query("SELECT m.movieId FROM Movie m WHERE m.movieName =:movieName AND m.isDeleted = FALSE ")
    Optional<Integer> checkMovieExist(@Param("movieName")String movieName);

    @Modifying
    @Query("UPDATE Movie m SET m.isDeleted = TRUE WHERE m.movieId =:movieId AND m.isDeleted = FALSE ")
    int deleteMovie(@Param("movieId")int movieId);
}

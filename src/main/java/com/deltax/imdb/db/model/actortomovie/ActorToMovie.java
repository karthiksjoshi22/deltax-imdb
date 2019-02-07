package com.deltax.imdb.db.model.actortomovie;

import com.deltax.imdb.db.model.actor.Actor;
import com.deltax.imdb.db.model.movie.Movie;

import javax.persistence.*;

@Entity
@Table(name = "imdb_actor_to_movie")
public class ActorToMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "am_id")
    private int amId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frn_actor_id",referencedColumnName = "actor_id")
    private Actor actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frn_movie_id",referencedColumnName = "movie_id")
    private Movie movie;

    public int getAmId() {
        return amId;
    }

    public void setAmId(int amId) {
        this.amId = amId;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}

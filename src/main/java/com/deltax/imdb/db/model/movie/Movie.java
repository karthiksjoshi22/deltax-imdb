package com.deltax.imdb.db.model.movie;

import com.deltax.imdb.db.model.file.File;
import com.deltax.imdb.db.model.producer.Producer;
import javax.persistence.*;

@Entity
@Table(name = "imdb_movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "movie_year_of_release")
    private String releaseYear;

    @Column(name = "movie_plot")
    private String plot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frn_movie_poster",referencedColumnName = "file_id")
    private File poster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frn_producer_id",referencedColumnName = "producer_id")
    private Producer producer;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public File getPoster() {
        return poster;
    }

    public void setPoster(File poster) {
        this.poster = poster;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

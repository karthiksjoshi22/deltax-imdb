package com.deltax.imdb.dto.response;

public class MovieResponseBean {

    private String movieName;
    private String releaseYear;
    private int producerId;
    private String producerName;

    public MovieResponseBean(String movieName,String releaseYear,int producerId,String producerName)
    {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.producerId = producerId;
        this.producerName = producerName;
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

    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }
}

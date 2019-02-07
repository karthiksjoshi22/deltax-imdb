package com.deltax.imdb.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class MovieCreationBean {

    @NotBlank(message = "Movie name cannot be empty or null")
    private String name;

    private String releaseYear;
    private int posterId;
    private String plot;

    @NotNull(message = "Producer id cannot be null or empty")
    @Min(value = 1,message = "Producer id should be greater than or equal to 1")
    private int producerId;

    @NotNull(message = "Actor id cannot be null or empty")
    private List<Integer> actorIdList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public List<Integer> getActorIdList() {
        return actorIdList;
    }

    public void setActorIdList(List<Integer> actorIdList) {
        this.actorIdList = actorIdList;
    }
}

package com.deltax.imdb.dto.response;

import java.util.List;

public class AggregateMovieResponse {

    private MovieResponseBean movieResponseBean;
    private List<ActorResponse> actorResponseList;

    public MovieResponseBean getMovieResponseBean() {
        return movieResponseBean;
    }

    public void setMovieResponseBean(MovieResponseBean movieResponseBean) {
        this.movieResponseBean = movieResponseBean;
    }

    public List<ActorResponse> getActorResponseList() {
        return actorResponseList;
    }

    public void setActorResponseList(List<ActorResponse> actorResponseList) {
        this.actorResponseList = actorResponseList;
    }
}

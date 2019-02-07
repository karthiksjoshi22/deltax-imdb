package com.deltax.imdb.db.model.actor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "imdb_actor")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private int actorId;

    @Column(name = "actor_name")
    private String actorName;

    @Column(name = "actor_sex")
    private String sex;

    @Column(name = "actor_dob")
    private Date dob;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "actor_bio")
    private String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

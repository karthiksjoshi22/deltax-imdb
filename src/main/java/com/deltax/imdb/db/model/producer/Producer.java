package com.deltax.imdb.db.model.producer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "imdb_producer")
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producer_id")
    private int producerId;

    @Column(name = "producer_name")
    private String producerName;

    @Column(name = "producer_sex")
    private String sex;

    @Column(name = "producer_dob")
    private Date dob;

    @Column(name = "producer_bio")
    private String bio;

    @Column(name = "is_deleted")
    private boolean isDeleted;

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

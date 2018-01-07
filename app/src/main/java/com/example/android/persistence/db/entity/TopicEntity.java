package com.example.android.persistence.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.persistence.model.Topic;

/**
 * Author: HMT
 * Date: 06/01/2018.
 */
@Entity(tableName = "topic")
public class TopicEntity implements Topic {
    @PrimaryKey
    private int id;
    private String name;
    private int imageResource;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getImageResource() {
        return imageResource;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public TopicEntity(){}

    public TopicEntity(Topic topic){
        this.id = topic.getId();
        this.name = topic.getName();
        this.imageResource = topic.getImageResource();
    }

    public TopicEntity(int id, String name, int imageResource) {
        this.id = id;
        this.name = name;
        this.imageResource = imageResource;
    }
}

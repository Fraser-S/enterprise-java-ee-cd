package com.qa.cd.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CD {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String genre;
    private String artist;

    public CD() {

    }

    public CD(String title, String genre, String published) {
        this.title = title;
        this.genre = genre;
        this.artist = published;
    }

    public void setID(Long id){
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getartist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}

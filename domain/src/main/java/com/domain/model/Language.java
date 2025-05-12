package com.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String lname;

    @Column(name = "language_key")
    private String languageKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLanguageKey() {
        return languageKey;
    }

    public void setLanguageKey(String languageKey) {
        this.languageKey = languageKey;
    }

    public Language(Integer id, String lname, String languageKey) {
        this.id = id;
        this.lname = lname;
        this.languageKey = languageKey;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", lname='" + lname + '\'' +
                ", languageKey='" + languageKey + '\'' +
                '}';
    }

    public Language(Integer id) {
        this.id = id;
    }

    public Language(String lname) {
        this.lname = lname;
    }
    public  Language(){}

}


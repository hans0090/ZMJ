package com.example.zmj;
/**create in 2020/7/1 by hans*/
//用于存储一条影片的目录信息
public class animent {
    private int id;
    private String name;
    private String language;
    private String year;
    private String type;
    private int season;
    private String episode;
    private String intro;
    private boolean watched;

    public animent(int id, String name, String language, String year, String type, int season, String episode, String intro, boolean watched) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.year = year;
        this.type = type;
        this.season = season;
        this.episode = episode;
        this.intro = intro;
        this.watched = watched;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}

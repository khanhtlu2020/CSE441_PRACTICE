package com.example.prac03;
public class CountryItem {
    private String name;
    private String description;
    private int imageResourceId;
    private String population;
    private String area;
    private String density;
    private String worldShare;
    public String getArea() {
        return area;
    }

    public CountryItem(String area, String density, String description, int imageResourceId, String name, String population, String worldShare) {
        this.area = area;
        this.density = density;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.name = name;
        this.population = population;
        this.worldShare = worldShare;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getWorldShare() {
        return worldShare;
    }

    public void setWorldShare(String worldShare) {
        this.worldShare = worldShare;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}

package com.example.android.absolutmixr.Model;

/**
 * Created by Leonard on 7/12/2017.
 */

public class DrinkItem {
    //the name of the drink
    private String name;

    //get the descriptionPlain from the JSON
    private String description;

    //color of the drink
    private String color;

    //skill of the drink
    private String skill;

    //id of the drink will be used to call the picture
    private String id;

    //rating of the drink
    private int rating;

    //array list for the ingredients


    //Constructor
    public DrinkItem(String id, String name, String description, String color, String skill
            , int rating){
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.skill =skill;
        this.rating = rating;
    }


    //Getters and Setters for the values
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
}

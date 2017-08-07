package com.example.android.absolutmixr.Model;

//importing serialable to send an object to another activity

import java.io.Serializable;
import java.util.List;

/**
 * Created by Leonard on 7/12/2017.
 */

@SuppressWarnings("serial")
public class DrinkItem  implements Serializable{
    //the name of the drink
    private String name;

    //get the descriptionPlain from the JSON
    private String description;

    //color of the drink
    private String color;

    //skill of the drink
    private String skill;

    //an array of ingredients
    private List<String> ingredients;

    //an array of tastes
    private List<String> tastes;

    //an array of occasions
    private List<String> occassions;

    //id of the drink will be used to call the picture
    private String id;

    //rating of the drink
    //Changed to a string because there was problems with adding it to the text view
    private String rating;

    //array list for the ingredients


    //Constructor
    //Changed to a string because there was problems with adding it to the text view
    public DrinkItem(String id, String name, String description, String color, String skill
            , String rating,List<String> ingredients, List<String> occassions, List<String> tastes){
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.skill =skill;
        this.rating = rating;
        this.ingredients = ingredients;
        this.occassions = occassions;
        this.tastes =tastes;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getTastes() {
        return tastes;
    }

    public void setTastes(List<String> tastes) {
        this.tastes = tastes;
    }

    public List<String> getOccassions() {
        return occassions;
    }

    public void setOccassions(List<String> occassions) {
        this.occassions = occassions;
    }
}

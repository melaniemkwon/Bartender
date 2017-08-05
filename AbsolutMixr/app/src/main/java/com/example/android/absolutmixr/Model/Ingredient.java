package com.example.android.absolutmixr.Model;

/**
 * Created by Danny on 7/31/2017.
 */

public class Ingredient {
    private String name;
    private String id;
    private String type;
    private String description;

    public Ingredient(String name, String id, String type, String description) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

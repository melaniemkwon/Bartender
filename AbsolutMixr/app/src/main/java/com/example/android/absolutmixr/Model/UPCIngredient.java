package com.example.android.absolutmixr.Model;

/**
 * Created by Danny on 8/8/2017.
 */

public class UPCIngredient {
    private String name;
    private String id;
    private String type;
    private String description;

    public UPCIngredient(String name, String description) {
        this.name = name;
        this.description = description;
    }

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
}

package hu.mandaline.bakingmoni.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe_model implements Parcelable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private ArrayList<Ingredient_model> ingredients;
    @SerializedName("steps")
    private ArrayList<Step_model> steps;
    @SerializedName("servings")
    private Integer servings;
    @SerializedName("image")
    private String image;

    //This empty constructor is needed by the Parceler library
    public Recipe_model() {
    }

    public Recipe_model(Integer id, String name, ArrayList<Ingredient_model> ingredients, ArrayList<Step_model> steps, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient_model> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient_model> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step_model> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step_model> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }

    /**
     * Static field used to regenerate object, individually or as arrays
     */
    public static final Creator<Recipe_model> CREATOR = new Creator<Recipe_model>() {
        public Recipe_model createFromParcel(Parcel pc) {
            return new Recipe_model(pc);
        }

        public Recipe_model[] newArray(int size) {
            return new Recipe_model[size];
        }
    };

    /**
     * Creator from Parcel, reads back fields IN THE ORDER they were written
     */
    private Recipe_model(Parcel pc) {
        this.id = pc.readInt();
        this.name = pc.readString();
        this.ingredients = new ArrayList<>();
        pc.readArrayList(Ingredient_model.class.getClassLoader());
        this.steps = new ArrayList<>();
        pc.readArrayList(Step_model.class.getClassLoader());
        this.servings = pc.readInt();
        this.image = pc.readString();
    }
}

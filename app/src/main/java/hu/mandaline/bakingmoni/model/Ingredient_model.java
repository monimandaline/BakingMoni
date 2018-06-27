package hu.mandaline.bakingmoni.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient_model implements Parcelable {

    @SerializedName("quantity")
    private Double quantity;

    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

    //This empty constructor is needed by the Parceler library
    public Ingredient_model() {
    }

    public Ingredient_model(Double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeValue(quantity);
        parcel.writeValue(measure);
        parcel.writeValue(ingredient);
    }

    /**
     * Static field used to regenerate object, individually or as arrays
     */
    public static final Creator<Ingredient_model> CREATOR = new Creator<Ingredient_model>() {
        public Ingredient_model createFromParcel(Parcel pc) {
            return new Ingredient_model(pc);
        }

        public Ingredient_model[] newArray(int size) {
            return new Ingredient_model[size];
        }
    };

    /**
     * Creator from Parcel, reads back fields IN THE ORDER they were written
     */

    private Ingredient_model(Parcel pc) {
        quantity = pc.readDouble();
        measure = pc.readString();
        ingredient = pc.readString();
    }


}

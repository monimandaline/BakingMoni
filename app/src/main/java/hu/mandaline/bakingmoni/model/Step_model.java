package hu.mandaline.bakingmoni.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step_model implements Parcelable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("description")
    private String description;
    @SerializedName("videoURL")
    private String videoURL;
    @SerializedName("thumbnailURL")
    private String thumbnailURL;

    //This empty constructor is needed by the Parceler library
    public Step_model() {
    }

    public Step_model(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoUrl;
        this.thumbnailURL = thumbnailUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeValue(id);
        parcel.writeValue(shortDescription);
        parcel.writeValue(description);
        parcel.writeValue(videoURL);
        parcel.writeValue(thumbnailURL);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Creator<Step_model> CREATOR = new Creator<Step_model>() {
        public Step_model createFromParcel(Parcel pc) {
            return new Step_model(pc);
        }
        public Step_model[] newArray(int size) {
            return new Step_model[size];
        }
    };

    /** Creator from Parcel, reads back fields IN THE ORDER they were written */
    private Step_model(Parcel pc){
        id = pc.readInt();
        shortDescription = pc.readString();
        description =  pc.readString();
        videoURL = pc.readString();
        thumbnailURL = pc.readString();
    }

}

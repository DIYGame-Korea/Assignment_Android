package com.idlab.idcorp.assignment_android.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by diygame5 on 2017-03-28.
 * Project : Assignment_Android
 * Retrofit2 와 Gson 을 사용하기 위한 데이터 클래스
 */

public class Card {
    @SerializedName("mainTitle")
    @Expose
    private String mainTitle;
    @SerializedName("subTitle")
    @Expose
    private String subTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public Card(String mainTitle, String subTitle, String description, String imageUrl) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

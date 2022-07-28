package com.fnf.game.ugh.pack.more.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<com.fnf.game.ugh.pack.more.model.AdSubModel> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<com.fnf.game.ugh.pack.more.model.AdSubModel> getData() {
        return data;
    }

    public void setData(List<com.fnf.game.ugh.pack.more.model.AdSubModel> data) {
        this.data = data;
    }
}

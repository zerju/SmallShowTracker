
package com.zerju.movies.gsonClasses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Previousepisode {

    @SerializedName("href")
    @Expose
    private String href;

    /**
     * 
     * @return
     *     The href
     */
    public String getHref() {
        return href;
    }

    /**
     * 
     * @param href
     *     The href
     */
    public void setHref(String href) {
        this.href = href;
    }

}

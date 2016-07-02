
package com.zerju.movies.gsonClasses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Rating {

    @SerializedName("average")
    @Expose
    private Object average;

    /**
     * 
     * @return
     *     The average
     */
    public Object getAverage() {
        return average;
    }

    /**
     * 
     * @param average
     *     The average
     */
    public void setAverage(Object average) {
        this.average = average;
    }

}

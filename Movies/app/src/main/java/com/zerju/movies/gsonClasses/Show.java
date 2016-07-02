
package com.zerju.movies.gsonClasses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Show {

    @SerializedName("score")
    @Expose
    private double score;
    @SerializedName("show")
    @Expose
    private Show_ show;

    /**
     * 
     * @return
     *     The score
     */
    public double getScore() {
        return score;
    }

    /**
     * 
     * @param score
     *     The score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * 
     * @return
     *     The show
     */
    public Show_ getShow() {
        return show;
    }

    /**
     * 
     * @param show
     *     The show
     */
    public void setShow(Show_ show) {
        this.show = show;
    }


}

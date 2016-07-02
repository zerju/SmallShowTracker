
package com.zerju.movies.gsonClasses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Externals {

    @SerializedName("tvrage")
    @Expose
    private Object tvrage;
    @SerializedName("thetvdb")
    @Expose
    private int thetvdb;
    @SerializedName("imdb")
    @Expose
    private Object imdb;

    /**
     * 
     * @return
     *     The tvrage
     */
    public Object getTvrage() {
        return tvrage;
    }

    /**
     * 
     * @param tvrage
     *     The tvrage
     */
    public void setTvrage(Object tvrage) {
        this.tvrage = tvrage;
    }

    /**
     * 
     * @return
     *     The thetvdb
     */
    public int getThetvdb() {
        return thetvdb;
    }

    /**
     * 
     * @param thetvdb
     *     The thetvdb
     */
    public void setThetvdb(int thetvdb) {
        this.thetvdb = thetvdb;
    }

    /**
     * 
     * @return
     *     The imdb
     */
    public Object getImdb() {
        return imdb;
    }

    /**
     * 
     * @param imdb
     *     The imdb
     */
    public void setImdb(Object imdb) {
        this.imdb = imdb;
    }

}

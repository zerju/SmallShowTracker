
package com.zerju.movies.gsonClasses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Links {

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("previousepisode")
    @Expose
    private Previousepisode previousepisode;

    /**
     *
     * @return
     *     The self
     */
    public Self getSelf() {
        return self;
    }

    /**
     *
     * @param self
     *     The self
     */
    public void setSelf(Self self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The previousepisode
     */
    public Previousepisode getPreviousepisode() {
        return previousepisode;
    }

    /**
     * 
     * @param previousepisode
     *     The previousepisode
     */
    public void setPreviousepisode(Previousepisode previousepisode) {
        this.previousepisode = previousepisode;
    }

}

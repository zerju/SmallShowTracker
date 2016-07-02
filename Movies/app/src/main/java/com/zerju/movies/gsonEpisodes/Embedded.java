
package com.zerju.movies.gsonEpisodes;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Embedded {

    @SerializedName("episodes")
    @Expose
    private List<Episode_> episodes = new ArrayList<Episode_>();

    /**
     * 
     * @return
     *     The episodes
     */
    public List<Episode_> getEpisodes() {
        return episodes;
    }

    /**
     * 
     * @param episodes
     *     The episodes
     */
    public void setEpisodes(List<Episode_> episodes) {
        this.episodes = episodes;
    }

}

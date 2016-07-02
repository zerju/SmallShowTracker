package com.zerju.movies;

import io.realm.RealmObject;

/**
 * Created by Jure on 4. 02. 2016.
 */
public class Favorite extends RealmObject {
    private String idShow;
    private String name;
    private String date;
    private String status;
    private String image;
    private boolean obstajaSlika;
    private String url;

    public String getIdShow() {
        return idShow;
    }

    public void setIdShow(String idShow) {
        this.idShow = idShow;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isObstajaSlika() {
        return obstajaSlika;
    }

    public void setObstajaSlika(boolean obstajaSlika) {
        this.obstajaSlika = obstajaSlika;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

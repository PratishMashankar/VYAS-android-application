package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

public class Highlights {
    String highlight;
    String url;

    public Highlights(){

    }

    public Highlights(String url,String highlight){
        this.highlight=highlight;
        this.url=url;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

package com.pratishaad.homelibrarymanagement.bibliophilecompanion;

public class Highlights {
    String highlight;
    String url;
    String highlightID;

    public Highlights(){

    }

    public Highlights(String url,String highlight, String highlightID){
        this.highlight=highlight;
        this.url=url;
        this.highlightID=highlightID;
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

    public String getHighlightID() {
        return highlightID;
    }

    public void setHighlightID(String highlightID) {
        this.highlightID = highlightID;
    }


}

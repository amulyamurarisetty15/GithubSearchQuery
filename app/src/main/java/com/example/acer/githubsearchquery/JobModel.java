package com.example.acer.githubsearchquery;

public class JobModel {
    String jtitle,jlocation,jurl;

    public JobModel(String jtitle, String jlocation, String jurl) {
        this.jtitle = jtitle;
        this.jlocation = jlocation;
        this.jurl = jurl;
    }

    public String getJtitle() {
        return jtitle;
    }

    public void setJtitle(String jtitle) {
        this.jtitle = jtitle;
    }

    public String getJlocation() {
        return jlocation;
    }

    public void setJlocation(String jlocation) {
        this.jlocation = jlocation;
    }

    public String getJurl() {
        return jurl;
    }

    public void setJurl(String jurl) {
        this.jurl = jurl;
    }
}

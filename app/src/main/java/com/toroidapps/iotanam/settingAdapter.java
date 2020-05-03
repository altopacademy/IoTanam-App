package com.toroidapps.iotanam;

/**
 * Created by agoes on 17/03/2019.
 */

public class settingAdapter {
    private String username, nama_lengkap, email, ttl, domisili;

    public settingAdapter(){

    }

    public settingAdapter(String username, String nama_lengkap, String email, String ttl, String domisili) {
        this.username = username;
        this.nama_lengkap = nama_lengkap;
        this.email = email;
        this.ttl = ttl;
        this.domisili = domisili;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getDomisili() {
        return domisili;
    }

    public void setDomisili(String domisili) {
        this.domisili = domisili;
    }
    @Override
    public String toString(){
        return  " "+username+"\n" +
                " "+nama_lengkap+"\n" +
                " "+email+"\n" +
                " "+ttl+"\n" +
                " "+domisili;
    }
}

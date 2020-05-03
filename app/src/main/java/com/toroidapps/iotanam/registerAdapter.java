package com.toroidapps.iotanam;

/**
 * Created by agoes on 16/03/2019.
 */

public class registerAdapter {
    private String username, email, password, nama_lengkap, ttl, domisili, start_date;
    private Integer currentDay;
    public registerAdapter(){

    }

    public registerAdapter(String username, String email, String password, String nama_lengkap, String ttl, String domisili, String start_date, Integer currentDay) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nama_lengkap = nama_lengkap;
        this.ttl = ttl;
        this.domisili = domisili;
        this.start_date = start_date;
        this.currentDay = currentDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Integer getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Integer currentDay) {
        this.currentDay = currentDay;
    }

    @Override
    public String toString(){
        return  " "+username+"\n" +
                " "+email+"\n" +
                " "+password+"\n" +
                " "+nama_lengkap+"\n" +
                " "+ttl+"\n" +
                " "+domisili+"\n" +
                " "+start_date+"\n" +
                " "+currentDay;
    }
}

package com.toroidapps.iotanam;

/**
 * Created by agoes on 17/03/2019.
 */

public class contactAdapter {
    private String namalengkap, email, message, status;
    public contactAdapter(){

    }

    public contactAdapter(String namalengkap, String email, String message, String status) {
        this.namalengkap = namalengkap;
        this.email = email;
        this.message = message;
        this.status = status;
    }

    public String getNamalengkap() {
        return namalengkap;
    }

    public void setNamalengkap(String namalengkap) {
        this.namalengkap = namalengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString(){
        return  " "+namalengkap+"\n" +
                " "+email+"\n" +
                " "+message+"\n" +
                " "+status;
    }
}

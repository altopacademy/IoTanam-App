package com.toroidapps.iotanam;

/**
 * Created by agoes on 16/03/2019.
 */

public class registerAdapterAuxilliary {
    private Double value;
    private String name;
    public registerAdapterAuxilliary(){

    }

    public registerAdapterAuxilliary(Double value, String name) {
        this.value = value;
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return  " "+name+"\n" +
                " "+value;
    }
}

package org.abstruck.mc.morehearts.utils.data;

public enum ChangeAction {
    ADD("add"),REMOVE("remove"),CLEAR("clear");
    private String name;
    ChangeAction(String name){
        this.name= name;
    }

    public String getName() {
        return name;
    }
}

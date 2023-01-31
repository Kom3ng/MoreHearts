package org.abstruck.mc.morehearts.net;

public abstract class BaseNetworking {
    public static int ID = 0;
    public static int nextId(){
        return ID++;
    }
}

package org.abstruck.mc.morehearts.utils.data;

public class NbtData<T> {
    private final String KEY;
    private T value;

    public NbtData(String key, T value){
        this.KEY = key;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String getKey() {
        return KEY;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

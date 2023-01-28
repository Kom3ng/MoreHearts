package org.abstruck.mc.morehearts.utils.functions;

@FunctionalInterface
public interface ForEachWithIndexFunc<E> {
    void accept(int index,E e);
}

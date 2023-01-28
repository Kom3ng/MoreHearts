package org.abstruck.mc.morehearts.utils.collection.list;

import org.abstruck.mc.morehearts.utils.functions.ForEachWithIndexFunc;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ListUtil {
    public static <E> void forEachWithIndex(@NotNull List<E> arrayList , ForEachWithIndexFunc<E> func){
        for (int index = 0; index<arrayList.size(); index++){
            func.accept(index,arrayList.get(index));
        }
    }

    public static <E> @NotNull List<E> reverse(@NotNull List<E> list){
        int size = list.size();
        List<E> result = new ArrayList<>(list.size());
        for (int index = size-1; index > -1; index--){
            result.add(list.get(index));
        }
        return result;
    }

    public static <E> void reverseForEach(List<E> list, Consumer<E> consumer){
        reverse(list).forEach(consumer);
    }
    public static <E> void reverseForEachWithIndex(@NotNull List<E> list, ForEachWithIndexFunc<E> func){
        for (int index = list.size()-1;index> -1;index--){
            func.accept(index,list.get(index));
        }
    }

    public static <E> void shrinkFromBehind(@NotNull List<E> list, int num){
        if (num > list.size()){
            list.clear();
            return;
        }
        list = list.subList(0, list.size() - num);
    }

    public static <E> @NotNull List<E> safeShrinkFromBehind(@NotNull List<E> list, int num){
        List<E> result = new ArrayList<>(list);
        shrinkFromBehind(result,num);
        return result;
    }

    public static <E> @NotNull List<E> sublistFromBehind(@NotNull List<E> list, int num){
        if (num > list.size()){
            return new ArrayList<>();
        }

        return list.subList(list.size() - num - 1, list.size() - 1);
    }

    public static void loop(int times,Runnable runnable){
        for (int i = 0; i<times;i++){
            runnable.run();
        }
    }
}

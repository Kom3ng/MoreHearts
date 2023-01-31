package org.abstruck.mc.morehearts.utils.data;

import javafx.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChangeData<E>{
    List<Pair<ChangeAction,E>> changeActions;
    public ChangeData(){
        changeActions = new ArrayList<>();
    }

    public Iterator<Pair<ChangeAction,E>> getIterator(){
        return changeActions.iterator();
    }

    public void addChange(Pair<ChangeAction,E> change){
        this.changeActions.add(change);
    }

    @Contract(value = " -> new", pure = true)
    public static <T> @NotNull ChangeDataBuilder<T> builder(){
        return new ChangeDataBuilder<>();
    }

    private static class ChangeDataBuilder<E>{
        private ChangeData<E> value;
        public ChangeDataBuilder(){
            value = new ChangeData<>();
        }

        public ChangeDataBuilder<E> addChange(Pair<ChangeAction,E> change){
            this.value.addChange(change);
            return this;
        }

        public ChangeData<E> build(){
            return value;
        }
    }
}

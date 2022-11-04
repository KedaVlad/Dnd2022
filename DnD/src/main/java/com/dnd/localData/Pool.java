package com.dnd.localData;

public interface Pool<T> {

    T borrowItem() throws Exception, InterruptedException;
    void returnItem(T item);

}
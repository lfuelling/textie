package de.micromata.azubi.builder;

/**
 * Created by jsiebert on 30.10.14.
 */
public interface Builder<T> {

    //uId mit idGenerator erstellen
    public Builder<T> build();

    public T get();
}

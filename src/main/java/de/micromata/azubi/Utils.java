package de.micromata.azubi;

/**
 * WTH is this?
 * Created by jsiebert on 31.10.14.
 */
public class Utils {

    private static long nextid = 1;

    public static long nextId() {
        return nextid++;
    }
}

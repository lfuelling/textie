package de.micromata.azubi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOUtils {

    public static String readLine(String promt) {

        System.out.print(promt);

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String eingabe = "";
        try {
            eingabe = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eingabe;
    }

    /**
     * @param string to convert
     * @return Returns the string which was given as parameter where the first letter is uppercase and every other is lowercase (ex. "LuKaS MaEd DiS" => "Lukas maed dis")
     * @author Lukas Fülling
     */
    public static String convertToName(String stc) {
        stc = stc.substring(0, 1).toUpperCase() + stc.substring(1).toLowerCase();
        return stc;
    }

}

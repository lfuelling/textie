package de.micromata.azubi;

import java.io.*;

/**
 * @author Lukas Füllimg (l.fuelling@micromata.de)
 * @version 1.2
 */
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
     * @param stc <b>S</b>tring <b>T</b>o <b>C</b>onvert
     * @return Returns the string which was given as parameter where the first letter is uppercase and every other is lowercase (ex. "LuKaS MaEd DiS" => "Lukas maed dis")
     */
    public static String convertToName(String stc) {
        stc = stc.substring(0, 1).toUpperCase() + stc.substring(1).toLowerCase();
        return stc;
    }

  /**
   * @deprecated
   * @param savegame_data
   */
    public static void writeInFile(String savegame_data){
        File savegame_file = new File("Savegame.json");

        try {
            FileWriter writer = new FileWriter(savegame_file);

            writer.write(savegame_data);

            // Platformunabhängiger Zeilenumbruch wird in den Stream geschrieben
            //writer.write(System.getProperty("line.separator"));

            writer.flush();

            // Schließt den Stream
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /**
   * @deprecated
   * @return File contents.
   */
    public static String readFromFile(){
        String savegame = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("Savegame.json"));
            String zeile;
            try {
                while ((zeile = br.readLine()) != null) {
                    savegame = zeile;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return savegame;
    }


}

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

}

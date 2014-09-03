import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class IOUtils {
	public static String readine(String prompt){
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
	    System.out.print(prompt);
	    String eingabe = "";
	    try {
	        eingabe = br.readLine();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return eingabe;
	}
}

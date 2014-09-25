package test.de.micromata.azubi; 

import de.micromata.azubi.Command;
import de.micromata.azubi.Textie;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* Textie Tester. 
* 
* @author <Authors name> 
* @since <pre>Sep 25, 2014</pre> 
* @version 1.0 
*/ 
public class TextieTest { 

    @Test
    public void testMain() throws Exception {
        Textie.initEngine();
        Textie.showIntro("Lukas");
        start().nimm("Fackel").gehe("süd");
    }

    private TextieTest gehe(String richtung) {
        Textie.executeCommand(new String[]{Command.GEHE, richtung}, new String[]{richtung});
        return this;
    }

    private TextieTest nimm(String text) {
        Textie.executeCommand(new String[]{Command.NIMM, text}, new String[]{text});
        return this;
    }

    private TextieTest start() {
       Textie.runGame(false);
       return this;
    }

} 

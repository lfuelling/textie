package test.de.micromata.azubi;

import de.micromata.azubi.Dungeon;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Dungeon Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Sep 30, 2014</pre>
 */
public class DungeonTest {
  @Before
  public void before() throws Exception {
  }

  @After
  public void after() throws Exception {
  }

  /**
   * Method: getDungeon()
   *//*
  @Test
  public void testGetDungeon() throws Exception {
    Dungeon dungeon = Dungeon.getDungeon();
    dungeon.init();
    String jsonDungeon = new JSONSerializer().deepSerialize(dungeon);
    System.out.println(jsonDungeon);

    Dungeon dungeon1 = new JSONDeserializer<Dungeon>().deserialize( jsonDungeon );
    System.out.println("curentRaum: "  + dungeon1.currentRaum.getNumber());
  }
*/

} 

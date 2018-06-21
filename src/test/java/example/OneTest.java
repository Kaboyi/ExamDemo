package example;

import static org.junit.Assert.*;

import com.migu.schedule.info.TaskInfo;
import org.junit.Test;

import java.util.*;

public class OneTest {
  @Test
  public void testFoo() throws Exception {
    One one = new One();
    //Test foo
    assertEquals("foo", one.foo());
  }

  @Test
  public void go() {
    Map m = new TreeMap();
    m.put(1, 31);
    m.put(44, 131);
    m.put(111, 1131);
    m.put(13, 11131);
    m.put(134, 11111131);
    m.put(11, 1211111131);
    System.out.println(m.values());
    System.out.println(m);
  }
}
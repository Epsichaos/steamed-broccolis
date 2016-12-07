package net.antidot.gwtodo;

import net.antidot.gwtodo.client.GWTodoTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class GWTodoSuite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for GWTodo");
    suite.addTestSuite(GWTodoTest.class);
    return suite;
  }
}

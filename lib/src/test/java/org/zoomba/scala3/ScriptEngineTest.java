package org.zoomba.scala3;

import org.junit.Test;

import javax.script.SimpleBindings;

import java.util.Map;

import static org.junit.Assert.*;


public class ScriptEngineTest {

    final String PRINT_HELLO = "println( \"Hello, World!\")" ;

    final String USE_BINDING = "_mem.get( \"x\" ) " ;

    @Test
    public void helloWorld() throws Throwable{
        ScriptEngine.ENGINE.eval( PRINT_HELLO,   new SimpleBindings() );
    }

    @Test
    public void useBinding() throws Throwable{
        assertEquals( 42, ScriptEngine.ENGINE.eval( USE_BINDING,   new SimpleBindings(Map.of("x", 42 )) ) );
    }
}

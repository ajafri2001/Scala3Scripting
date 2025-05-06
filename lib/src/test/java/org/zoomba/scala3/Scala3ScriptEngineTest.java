package org.zoomba.scala3;

import org.junit.Test;

import javax.script.SimpleBindings;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class Scala3ScriptEngineTest {

    final String PRINT_HELLO = "println( \"Hello, World!\")" ;

    final String USE_BINDING = "_mem.get( \"x\" ) " ;

    @Test
    public void helloWorld() throws Throwable{
        Scala3ScriptEngine.ENGINE.eval( PRINT_HELLO,   new SimpleBindings() );
    }

    @Test
    public void useBinding() throws Throwable{
        assertEquals( 42, Scala3ScriptEngine.ENGINE.eval( USE_BINDING,   new SimpleBindings(Map.of("x", 42 )) ) );
    }

    @Test
    public void factoryMethodsTest(){
        assertEquals("scala3",  Scala3ScriptEngine.FACTORY.getEngineName() );
        assertEquals("0.1",  Scala3ScriptEngine.FACTORY.getEngineVersion() );

        assertEquals(List.of("scala", "sc"),  Scala3ScriptEngine.FACTORY.getExtensions() );

        assertEquals("scala3",  Scala3ScriptEngine.FACTORY.getLanguageName() );
        assertEquals("3.*",  Scala3ScriptEngine.FACTORY.getLanguageVersion() );

        assertEquals("obj.func()", Scala3ScriptEngine.FACTORY.getMethodCallSyntax( "obj", "func") );
        assertEquals("obj.func(x)", Scala3ScriptEngine.FACTORY.getMethodCallSyntax( "obj", "func", "x" ) );
        assertEquals("obj.func(x,y)", Scala3ScriptEngine.FACTORY.getMethodCallSyntax( "obj", "func", "x" , "y" ) );

        assertEquals( List.of(), Scala3ScriptEngine.FACTORY.getMimeTypes() );
        assertEquals( List.of("scala3"), Scala3ScriptEngine.FACTORY.getNames() );

        assertEquals("println(x)",  Scala3ScriptEngine.FACTORY.getOutputStatement("x") );
        assertNull( Scala3ScriptEngine.FACTORY.getParameter("x"));
        assertEquals("println(x)\nx = func(y)",  Scala3ScriptEngine.FACTORY.getProgram("println(x)" , "x = func(y)" ) );

        assertSame( Scala3ScriptEngine.ENGINE, Scala3ScriptEngine.FACTORY.getScriptEngine() );
    }
}

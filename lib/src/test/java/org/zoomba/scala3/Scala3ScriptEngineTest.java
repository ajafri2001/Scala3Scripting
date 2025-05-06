package org.zoomba.scala3;

import org.junit.Test;

import javax.script.CompiledScript;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class Scala3ScriptEngineTest {

    final String PRINT_HELLO = "println( \"Hello, World!\")" ;

    final String USE_BINDING = "_mem.get( \"x\" ) " ;

    @Test
    public void evalFunctionsTest() throws Throwable{
        Scala3ScriptEngine.ENGINE.eval( PRINT_HELLO,   new SimpleBindings() );
        assertEquals( 42, Scala3ScriptEngine.ENGINE.eval( USE_BINDING,   new SimpleBindings(Map.of("x", 42 )) ) );
        assertEquals( 42, Scala3ScriptEngine.ENGINE.eval( new StringReader(USE_BINDING),   new SimpleBindings(Map.of("x", 42 )) ) );

        assertThrows( ScriptException.class, () ->
                Scala3ScriptEngine.ENGINE.eval( "foo-bar",   new SimpleBindings(Map.of("x", 42 )) ) );
        assertThrows( ScriptException.class, () ->
                Scala3ScriptEngine.ENGINE.eval( new StringReader("foo-bar"),   new SimpleBindings(Map.of("x", 42 )) ) );


        Exception ex = assertThrows( ScriptException.class, () ->
                Scala3ScriptEngine.ENGINE.eval( "foo-bar",   Scala3ScriptEngine.ENGINE.getContext() ) );
        assertSame( Scala3ScriptEngine.MULTITHREADING_NIGHTMARE_PURITY, ex );
        assertThrows( ScriptException.class, () ->
                Scala3ScriptEngine.ENGINE.eval( new StringReader("foo-bar"),   Scala3ScriptEngine.ENGINE.getContext() ) );
        assertSame( Scala3ScriptEngine.MULTITHREADING_NIGHTMARE_PURITY, ex );
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
        assertSame( Scala3ScriptEngine.FACTORY, Scala3ScriptEngine.ENGINE.getFactory() );

    }

    @Test
    public void compiledScriptTest() throws Exception {
        CompiledScript cs =  Scala3ScriptEngine.ENGINE.compile( USE_BINDING );
        Exception ex =  assertThrows( ScriptException.class, () -> cs.eval( Scala3ScriptEngine.ENGINE.getContext())) ;
        assertSame( Scala3ScriptEngine.MULTITHREADING_NIGHTMARE_PURITY, ex);
        assertNull( cs.eval() ) ;
        assertSame( Scala3ScriptEngine.ENGINE,  cs.getEngine() );
        assertTrue( cs.equals(cs ) );
        assertTrue( cs.equals( Scala3ScriptEngine.ENGINE.compile( USE_BINDING ) ) );
        assertTrue( cs.equals( Scala3ScriptEngine.ENGINE.compile( new StringReader(USE_BINDING) ) ) );

        assertFalse( cs.equals( Scala3ScriptEngine.ENGINE.compile( PRINT_HELLO ) ) );
        assertFalse( cs.equals( null  ) );
        assertFalse( cs.equals( 42  ) );
        assertNotEquals( 0, cs.hashCode() );
    }

    @Test
    public void compilableMethodsTest() throws Exception {
        assertThrows( ScriptException.class, () -> Scala3ScriptEngine.ENGINE.compile( "foo-bar") );
        assertThrows( ScriptException.class, () -> Scala3ScriptEngine.ENGINE.compile( new StringReader("foo-bar") ) );

    }
}

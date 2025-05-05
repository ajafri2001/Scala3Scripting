package org.zoomba.scala3;

import org.junit.Test;

import javax.script.Bindings;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.function.Function;

import static org.junit.Assert.*;

public class CodeGenTest {

    @Test
    public void runTest() throws Exception {
        Function<Bindings,Object> f = CodeGen.function( "42" ) ;
        assertEquals( 42, f.apply(new SimpleBindings()));
        Function<Bindings,Object> f2 =  CodeGen.function( "42" ) ;
        assertSame( f, f2 );
    }


    @Test
    public void errorTest(){
        assertThrows( ScriptException.class, () -> CodeGen.function( "foo-bar")  );
    }
}

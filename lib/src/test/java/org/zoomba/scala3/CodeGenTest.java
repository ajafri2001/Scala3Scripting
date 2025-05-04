package org.zoomba.scala3;

import org.junit.Test;

import javax.script.SimpleBindings;

public class CodeGenTest {

    final String PRINT_HELLO = "println( \"Hello, World!\")" ;

    @Test
    public void helloWorld() throws Throwable{
        CodeGen.execute( PRINT_HELLO,   new SimpleBindings() );
    }
}

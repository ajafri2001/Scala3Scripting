package org.zoomba.scala3;

import org.junit.Test;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void loadFileTest() throws MalformedURLException {
        assertNull( Utils.loadFile( new URL( "file:///xxx")) ) ;
    }

    @Test
    public void scriptExceptionTest() {
        Exception ex = new Exception();
        assertSame( ex, Utils.scriptException(ex).getCause() );
        RuntimeException re = new RuntimeException();
        assertSame( re, Utils.scriptException(re).getCause() );
        Throwable th = new Throwable();
        assertSame( th, Utils.scriptException(th).getCause().getCause() );
        ScriptException sc = new ScriptException("Hi!");
        assertSame( sc, Utils.scriptException(sc) );
    }

    @Test
    public void readerStringTest() throws IOException {
        final String s = "Hello, world!" ;
        StringReader stringReader = new StringReader(s);
        assertEquals( s, Utils.string(stringReader) );
    }
}

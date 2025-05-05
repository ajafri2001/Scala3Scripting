package org.zoomba.scala3;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public enum Utils {
    ;

    static String loadFile( URL url ){
        try {
            return Files.readString(Paths.get( url.getPath() ) , Charset.defaultCharset() ) ;
        }catch (Throwable t){
            return null;
        }
    }

    static String string( Reader initialReader ) throws IOException {
        char[] arr = new char[8 * 1024];
        StringBuilder buffer = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = initialReader.read(arr, 0, arr.length)) != -1) {
            buffer.append(arr, 0, numCharsRead);
        }
        initialReader.close();
        return buffer.toString();
    }

    static ScriptException scriptException(Throwable t){
        final RuntimeException re;
        if ( t instanceof RuntimeException ){
            re = (RuntimeException)t;
        } else {
            re = new RuntimeException(t);
        }
        return new ScriptException(re);
    }

}

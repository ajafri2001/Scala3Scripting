package org.zoomba.scala3;

import dotty.tools.repl.ScriptEngine;

import javax.script.Bindings;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public interface CodeGen {

    static String loadFile( URL url ){
        try {
            return Files.readString(Paths.get( url.getPath() ) , Charset.defaultCharset() ) ;
        }catch (Throwable t){
            return null;
        }
    }

    String MACRO = loadFile( CodeGen.class.getClassLoader().getResource("Template.scala") );

    static Method method(Object o){
        try {
            return o.getClass().getDeclaredMethod("exec", Bindings.class);
        }catch (Throwable t){
            return null;
        }
    }

    static Object makeObject(String scalaSrc) {
        ScriptEngine sc = new ScriptEngine();
        String postMacro = String.format( MACRO,  scalaSrc );
        try {
            return sc.eval( postMacro );
        }catch (Throwable t){
            return null;
        }
    }

    Map<String, Map.Entry<Method,Object>>  cache = new HashMap<>();

    static Object execute(String scalaSrc,  Bindings b) throws Throwable {
        Map.Entry<Method,Object> e = cache.get(scalaSrc);
        if ( e == null ) {
            Object thisObject = makeObject(scalaSrc);
            Method m = method(thisObject);
            e = Map.of(m, thisObject).entrySet().iterator().next();
            cache.put(scalaSrc, e );
        }
        return e.getKey().invoke(e.getValue(), b);
    }
}

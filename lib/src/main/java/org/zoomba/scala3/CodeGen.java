package org.zoomba.scala3;

import dotty.tools.repl.ScriptEngine;

import javax.script.Bindings;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class CodeGen {

    static String loadFile( URL url ){
        try {
            return Files.readString(Paths.get( url.getPath() ) , Charset.defaultCharset() ) ;
        }catch (Throwable t){
            return null;
        }
    }

    static final String MACRO = loadFile( CodeGen.class.getClassLoader().getResource("Template.scala") );

    static Function<Bindings,Object> function(String scalaSrc) {
        ScriptEngine sc = new ScriptEngine();
        String postMacro = String.format( MACRO,  scalaSrc );
        try {
            Object o = sc.eval( postMacro );
            return (Function<Bindings, Object>) o;
        }catch (Throwable t){
            return null;
        }
    }

    static final Map<String, Function<Bindings,Object>>  cache = new HashMap<>();

    public static Object execute(String scalaSrc,  Bindings b) throws Throwable {
        Function<Bindings,Object> f = cache.get(scalaSrc);
        if ( f == null ) {
            f = function(scalaSrc);
            cache.put(scalaSrc, f );
        }
        return f.apply(b);
    }
}

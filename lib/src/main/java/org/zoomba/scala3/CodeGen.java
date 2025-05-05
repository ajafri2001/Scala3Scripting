package org.zoomba.scala3;

import dotty.tools.repl.ScriptEngine;

import javax.script.Bindings;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.zoomba.scala3.Utils.*;

/**
 * A Scala3 based code generator, which can be used to create a JSR-223 scala script
 * Idea is simply using Template.scala in resources to get filled up by the code coming in
 * Compile and execute the code to return the class instance ( object instance )
 * Cast it to a Java Function and that does it
 */
public enum CodeGen {
    ;

    static final String MACRO = loadFile( CodeGen.class.getClassLoader().getResource("Template.scala") );

    static final Map<String, Function<Bindings,Object>>  cache = new HashMap<>();

    /**
     * Converts a scala source code into a Java Function
     * @param scalaSrc the scala source code
     * @return a Java Function
     * @throws ScriptException in case of any error that happened
     */
    public static Function<Bindings,Object> function(String scalaSrc) throws ScriptException {
        Function<Bindings,Object> func = cache.get(scalaSrc);
        if ( func != null ) return func;

        ScriptEngine sc = new ScriptEngine();
        String postMacro = String.format( MACRO,  scalaSrc );
        try {
            Object o = sc.eval( postMacro );
            func = (Function<Bindings, Object>) o;
            cache.put(scalaSrc, func) ;
            return func;
        }catch (Throwable t){
            throw scriptException(t);
        }
    }
}

package org.zoomba.scala3;

import javax.script.*;
import java.io.Reader;
import java.util.List;
import java.util.function.Function;

import static org.zoomba.scala3.Utils.scriptException;
import static org.zoomba.scala3.Utils.string;

/**
 * A Scala3 JSR-223 Compliant Script Engine
 */
public final class ScriptEngine extends AbstractScriptEngine implements Compilable {

    /**
     * The underlying factory, if at all it is needed
     * It is not really needed
     */
    public static final ScriptEngineFactory FACTORY = new ScriptEngineFactory() {
        @Override
        public String getEngineName() {
            return "scala3";
        }

        @Override
        public String getEngineVersion() {
            return "0.1";
        }

        @Override
        public List<String> getExtensions() {
            return List.of("scala", "sc");
        }

        @Override
        public List<String> getMimeTypes() {
            return List.of();
        }

        @Override
        public List<String> getNames() {
            return List.of("scala3");
        }

        @Override
        public String getLanguageName() {
            return "scala3";
        }

        @Override
        public String getLanguageVersion() {
            return "3.*";
        }

        @Override
        public Object getParameter(String s) {
            return null;
        }

        @Override
        public String getMethodCallSyntax(String s, String s1, String... strings) {
            return "";
        }

        @Override
        public String getOutputStatement(String s) {
            return "";
        }

        @Override
        public String getProgram(String... strings) {
            return "";
        }

        @Override
        public javax.script.ScriptEngine getScriptEngine() {
            return ScriptEngine.ENGINE;
        }
    };

    static final ScriptException MULTITHREADING_NIGHTMARE_PURITY =
            new ScriptException( new UnsupportedOperationException (
                    "we do not support this - this design is multithreading nightmare!" ));

    @Override
    public Object eval(String s, ScriptContext scriptContext) throws ScriptException {
        throw MULTITHREADING_NIGHTMARE_PURITY;
    }

    @Override
    public Object eval(Reader reader, ScriptContext scriptContext) throws ScriptException {
        throw MULTITHREADING_NIGHTMARE_PURITY;
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public Object eval(String script, Bindings bindings) throws ScriptException {
        try {
            return compile(script).eval(bindings);
        }catch (Throwable t){
            throw scriptException(t);
        }
    }


    @Override
    public Object eval(Reader reader, Bindings bindings) throws ScriptException {
        try {
            return eval( string( reader), bindings );
        }catch (Throwable t){
            throw scriptException(t);
        }
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return FACTORY;
    }

    private ScriptEngine(){}

    @Override
    public CompiledScript compile(String s) throws ScriptException {
        try{
            return new CompiledScript() {

                Function<Bindings,Object> scalaFunction = CodeGen.function(s);

                @Override
                public Object eval(ScriptContext scriptContext) throws ScriptException {
                    throw MULTITHREADING_NIGHTMARE_PURITY;
                }

                @Override
                public Object eval(Bindings bindings) throws ScriptException {
                    return scalaFunction.apply(bindings);
                }

                @Override
                public Object eval() throws ScriptException {
                    return eval(ENGINE.createBindings());
                }

                @Override
                public javax.script.ScriptEngine getEngine() {
                    return ENGINE;
                }
            };
        }catch (Throwable t){
            throw scriptException(t);
        }
    }

    @Override
    public CompiledScript compile(Reader reader) throws ScriptException {
        try {
            return compile( string(reader) );
        }catch (Throwable t){
            throw scriptException(t);
        }
    }

    /**
     * An Instance of the Script Engine
     * We do not need to have anything impure so one instance is more than good enough
     */
    public static final ScriptEngine ENGINE = new ScriptEngine();
}

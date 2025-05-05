import javax.script.Bindings
import java.util.function.Function

class Scriptable extends Function[Bindings,Any] {

  def apply( _mem: Bindings) : Any = {
    /* code gets in here - do not ever replace */
    %s
  }
}

new Scriptable()

import javax.script.Bindings

class Scriptable {

  def exec( _mem: Bindings) : Any = {
    /* code gets in here - do not ever replace */
    %s
  }
}

new Scriptable()

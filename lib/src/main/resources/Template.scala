import javax.script.Bindings

object Scriptable {

  var b : Bindings

  def eval( binding: Bindings ) : Any = {
    b = bindings
    scriptExec()
  }

  def scriptExec() : Any = {
    /* code gets in here - do not ever replace the %s, that is where code gets pasted */
    %s
  }
}
package compiler.language.ast;

/*
 * Created on 30 Jun 2010
 */

/**
 * A pseudo-enum representing a modifier. It is not a pure enum because it
 * needs to be extended by certain modifiers (like "native") which need to
 * store a some variables of their own.
 *
 * @author Anthony Bryant
 */
public class Modifier
{

  public static final Modifier STATIC = new Modifier();
  public static final Modifier ABSTRACT = new Modifier();
  public static final Modifier FINAL = new Modifier();
  public static final Modifier IMMUTABLE = new Modifier();
  public static final Modifier SYNCHRONIZED = new Modifier();
  public static final Modifier TRANSIENT = new Modifier();
  public static final Modifier VOLATILE = new Modifier();

  /**
   * Protected constructor, so that nothing but a subclass can create a modifier.
   */
  protected Modifier()
  {
    // do nothing
  }
}

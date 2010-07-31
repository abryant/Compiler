package compiler.language.ast.member;

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

  public static final Modifier STATIC = new Modifier("static");
  public static final Modifier ABSTRACT = new Modifier("abstract");
  public static final Modifier FINAL = new Modifier("final");
  public static final Modifier MUTABLE = new Modifier("mutable");
  public static final Modifier IMMUTABLE = new Modifier("immutable");
  public static final Modifier SYNCHRONIZED = new Modifier("synchronized");
  public static final Modifier TRANSIENT = new Modifier("transient");
  public static final Modifier VOLATILE = new Modifier("volatile");

  private final String stringRepresentation;

  /**
   * Protected constructor, so that nothing but a subclass can create a modifier.
   * @param stringRepresentation - the String representation of this modifier
   */
  protected Modifier(String stringRepresentation)
  {
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return stringRepresentation;
  }
}

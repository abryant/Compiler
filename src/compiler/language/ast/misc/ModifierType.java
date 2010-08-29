package compiler.language.ast.misc;

/*
 * Created on 5 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public enum ModifierType
{

  STATIC("static"),
  ABSTRACT("abstract"),
  FINAL("final"),
  MUTABLE("mutable"),
  IMMUTABLE("immutable"),
  SYNCHRONIZED("synchronized"),
  TRANSIENT("transient"),
  VOLATILE("volatile"),
  SINCE_SPECIFIER("since()"),
  NATIVE_SPECIFIER("native()");

  private String stringRepresentation;

  /**
   * Creates a new ModifierType with the specified string representation
   * @param stringRepresentation - the string representation of this modifier type
   */
  private ModifierType(String stringRepresentation)
  {
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString()
  {
    return stringRepresentation;
  }

}

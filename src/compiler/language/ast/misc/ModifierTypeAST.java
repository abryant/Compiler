package compiler.language.ast.misc;

/*
 * Created on 5 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public enum ModifierTypeAST
{

  STATIC("static"),
  ABSTRACT("abstract"),
  FINAL("final"),
  MUTABLE("mutable"),
  IMMUTABLE("immutable"),
  SEALED("sealed"),
  SYNCHRONIZED("synchronized"),
  TRANSIENT("transient"),
  VOLATILE("volatile"),
  SINCE_SPECIFIER("since()"),
  NATIVE_SPECIFIER("native()");

  private String stringRepresentation;

  /**
   * Creates a new ModifierTypeAST with the specified string representation
   * @param stringRepresentation - the string representation of this modifier type
   */
  private ModifierTypeAST(String stringRepresentation)
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

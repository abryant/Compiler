package compiler.language.ast.type;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public enum FloatingTypeLengthAST
{
  FLOAT("float"),
  DOUBLE("double");

  private final String stringRepresentation;

  private FloatingTypeLengthAST(String stringRepresentation)
  {
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString()
  {
    return stringRepresentation;
  }
}

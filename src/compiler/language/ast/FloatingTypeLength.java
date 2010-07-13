package compiler.language.ast;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public enum FloatingTypeLength
{
  FLOAT("float"),
  DOUBLE("double");

  private final String stringRepresentation;

  private FloatingTypeLength(String stringRepresentation)
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

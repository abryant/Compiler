package compiler.language.ast;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerLiteral
{

  private String stringRepresentation;

  /**
   * Creates a new integer literal with the specified string representation.
   * @param stringRepresentation - the string representation of the integer literal
   */
  public IntegerLiteral(String stringRepresentation)
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

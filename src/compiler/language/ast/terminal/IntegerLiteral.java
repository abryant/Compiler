package compiler.language.ast.terminal;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerLiteral extends Terminal
{

  private String stringRepresentation;

  /**
   * Creates a new integer literal with the specified string representation.
   * @param stringRepresentation - the string representation of the integer literal
   * @param line - the line number
   * @param startPos - the starting position of the terminal on the specified line
   * @param length - the length of the terminal
   */
  public IntegerLiteral(String stringRepresentation, int line, int startPos, int length)
  {
    super(line, startPos, length);
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

package compiler.language.ast.terminal;

/*
 * Created on 5 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class StringLiteral extends Terminal
{

  private String literal;

  /**
   * Creates a new String literal with the specified value
   * @param literal - the value of the string literal
   * @param line - the line number of the token
   * @param startPos - the start position of the token
   * @param length - the length of the token
   */
  public StringLiteral(String literal, int line, int startPos, int length)
  {
    super(line, startPos, length);
    this.literal = literal;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return literal;
  }

}

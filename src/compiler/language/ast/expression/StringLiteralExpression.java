package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.StringLiteral;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class StringLiteralExpression extends Expression
{

  private StringLiteral literal;

  /**
   * Creates a new StringLiteralExpression with the specified literal value.
   * @param literal - the String literal
   * @param parseInfo - the parsing information
   */
  public StringLiteralExpression(StringLiteral literal, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public StringLiteral getLiteral()
  {
    return literal;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return literal.toString();
  }
}

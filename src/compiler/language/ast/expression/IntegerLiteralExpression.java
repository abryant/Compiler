package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.IntegerLiteral;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerLiteralExpression extends Expression
{

  private IntegerLiteral literal;

  /**
   * Creates a new IntegerLiteralExpression with the specified literal value.
   * @param literal - the integer literal
   * @param parseInfo - the parsing information
   */
  public IntegerLiteralExpression(IntegerLiteral literal, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public IntegerLiteral getLiteral()
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

package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.FloatingLiteral;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingLiteralExpression extends Expression
{

  private FloatingLiteral literal;

  /**
   * Creates a new FloatingLiteralExpression with the specified literal value.
   * @param literal - the floating literal
   * @param parseInfo - the parsing information
   */
  public FloatingLiteralExpression(FloatingLiteral literal, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public FloatingLiteral getLiteral()
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

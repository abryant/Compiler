package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.FloatingLiteralAST;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingLiteralExpressionAST extends ExpressionAST
{

  private FloatingLiteralAST literal;

  /**
   * Creates a new FloatingLiteralExpressionAST with the specified literal value.
   * @param literal - the floating literal
   * @param parseInfo - the parsing information
   */
  public FloatingLiteralExpressionAST(FloatingLiteralAST literal, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public FloatingLiteralAST getLiteral()
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

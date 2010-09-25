package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.IntegerLiteralAST;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerLiteralExpressionAST extends ExpressionAST
{

  private IntegerLiteralAST literal;

  /**
   * Creates a new IntegerLiteralExpressionAST with the specified literal value.
   * @param literal - the integer literal
   * @param parseInfo - the parsing information
   */
  public IntegerLiteralExpressionAST(IntegerLiteralAST literal, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public IntegerLiteralAST getLiteral()
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

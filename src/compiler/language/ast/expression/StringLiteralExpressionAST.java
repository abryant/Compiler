package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.StringLiteralAST;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class StringLiteralExpressionAST extends ExpressionAST
{

  private StringLiteralAST literal;

  /**
   * Creates a new StringLiteralExpressionAST with the specified literal value.
   * @param literal - the String literal
   * @param parseInfo - the parsing information
   */
  public StringLiteralExpressionAST(StringLiteralAST literal, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public StringLiteralAST getLiteral()
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

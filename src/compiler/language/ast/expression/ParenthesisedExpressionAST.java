package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ParenthesisedExpressionAST extends ExpressionAST
{

  private ExpressionAST expression;

  /**
   * Creates a new ParenthesisedExpressionAST with the specified expression.
   * @param expression - the expression that this ParenthesisedExpressionAST will encapsulate
   * @param parseInfo - the parsing information
   */
  public ParenthesisedExpressionAST(ExpressionAST expression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "(" + expression + ")";
  }
}

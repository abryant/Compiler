package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ParenthesisedExpression extends Expression
{

  private Expression expression;

  /**
   * Creates a new ParenthesisedExpression with the specified expression.
   * @param expression - the expression that this ParenthesisedExpression will encapsulate
   * @param parseInfo - the parsing information
   */
  public ParenthesisedExpression(Expression expression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
  }

  /**
   * @return the expression
   */
  public Expression getExpression()
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

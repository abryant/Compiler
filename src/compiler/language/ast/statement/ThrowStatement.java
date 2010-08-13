package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThrowStatement extends Statement
{

  private Expression expression;

  /**
   * Creates a new ThrowStatement to throw the specified Expression.
   * @param expression - the expression to throw
   * @param parseInfo - the parsing information
   */
  public ThrowStatement(Expression expression, ParseInfo parseInfo)
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
    return "throw " + expression + ";";
  }
}

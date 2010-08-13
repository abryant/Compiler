package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ReturnStatement extends Statement
{

  private Expression expression;

  /**
   * Creates a new ReturnStatement to return the result of the specified expression.
   * @param expression - the expression to return
   * @param parseInfo - the parsing information
   */
  public ReturnStatement(Expression expression, ParseInfo parseInfo)
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
    return "return " + expression + ";";
  }
}

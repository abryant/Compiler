package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.StatementExpression;

/*
 * Created on 14 Aug 2010
 */

/**
 * A Statement which executes an Expression. The only Expressions that can be executed by this Statement are subclasses of StatementExpression.
 * @author Anthony Bryant
 */
public class ExpressionStatement extends Statement
{

  private StatementExpression expression;

  /**
   * Creates a new ExpressionStatement with the specified StatementExpression to execute.
   * @param expression - the expression to execute
   * @param parseInfo - the parsing information
   */
  public ExpressionStatement(StatementExpression expression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
  }

  /**
   * @return the expression
   */
  public StatementExpression getExpression()
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
    return expression + ";";
  }
}

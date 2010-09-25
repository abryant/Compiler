package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.StatementExpressionAST;

/*
 * Created on 14 Aug 2010
 */

/**
 * A StatementAST which executes an ExpressionAST. The only Expressions that can be executed by this StatementAST are subclasses of StatementExpressionAST.
 * @author Anthony Bryant
 */
public class ExpressionStatementAST extends StatementAST
{

  private StatementExpressionAST expression;

  /**
   * Creates a new ExpressionStatementAST with the specified StatementExpressionAST to execute.
   * @param expression - the expression to execute
   * @param parseInfo - the parsing information
   */
  public ExpressionStatementAST(StatementExpressionAST expression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expression = expression;
  }

  /**
   * @return the expression
   */
  public StatementExpressionAST getExpression()
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

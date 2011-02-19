package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ReturnStatementAST extends StatementAST
{

  private ExpressionAST expression;

  /**
   * Creates a new ReturnStatementAST to return the result of the specified expression.
   * @param expression - the expression to return
   * @param parseInfo - the parsing information
   */
  public ReturnStatementAST(ExpressionAST expression, ParseInfo parseInfo)
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
    return "return " + expression + ";";
  }
}

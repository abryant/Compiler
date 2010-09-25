package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThrowStatementAST extends StatementAST
{

  private ExpressionAST expression;

  /**
   * Creates a new ThrowStatementAST to throw the specified ExpressionAST.
   * @param expression - the expression to throw
   * @param parseInfo - the parsing information
   */
  public ThrowStatementAST(ExpressionAST expression, ParseInfo parseInfo)
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
    return "throw " + expression + ";";
  }
}

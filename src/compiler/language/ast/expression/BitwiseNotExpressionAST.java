package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BitwiseNotExpressionAST extends ExpressionAST
{

  private ExpressionAST subExpression;

  /**
   * Creates a new BitwiseNotExpressionAST with the specified subExpression
   * @param subExpression - the expression that the bitwise not operation will be performed on
   * @param parseInfo - the parsing information
   */
  public BitwiseNotExpressionAST(ExpressionAST subExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.subExpression = subExpression;
  }

  /**
   * @return the subExpression
   */
  public ExpressionAST getSubExpression()
  {
    return subExpression;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "~" + subExpression;
  }

}

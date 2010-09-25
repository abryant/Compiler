package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayAccessExpressionAST extends ExpressionAST
{

  private ExpressionAST arrayExpression;
  private ExpressionAST indexExpression;

  /**
   * Creates a new ArrayAccessExpressionAST with the specified array and index expressions
   * @param arrayExpression - the expression for the array that is being accessed
   * @param indexExpression - the expression for the index into the array
   * @param parseInfo - the parsing information
   */
  public ArrayAccessExpressionAST(ExpressionAST arrayExpression, ExpressionAST indexExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.arrayExpression = arrayExpression;
    this.indexExpression = indexExpression;
  }

  /**
   * @return the arrayExpression
   */
  public ExpressionAST getArrayExpression()
  {
    return arrayExpression;
  }

  /**
   * @return the indexExpression
   */
  public ExpressionAST getIndexExpression()
  {
    return indexExpression;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return arrayExpression + "[" + indexExpression + "]";
  }

}

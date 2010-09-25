package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class RelationalExpressionAST extends ExpressionAST
{

  private ExpressionAST firstExpression;
  private ExpressionAST secondExpression;
  private RelationalExpressionTypeAST type;

  /**
   * Creates a new relational expression with the specified first and second expressions and type
   * @param firstExpression - the first expression to compare
   * @param type - the relational expression type
   * @param secondExpression - the second expression to compare
   * @param parseInfo - the parsing information
   */
  public RelationalExpressionAST(ExpressionAST firstExpression, RelationalExpressionTypeAST type, ExpressionAST secondExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.firstExpression = firstExpression;
    this.secondExpression = secondExpression;
    this.type = type;
  }

  /**
   * @return the firstExpression
   */
  public ExpressionAST getFirstExpression()
  {
    return firstExpression;
  }

  /**
   * @return the secondExpression
   */
  public ExpressionAST getSecondExpression()
  {
    return secondExpression;
  }

  /**
   * @return the type
   */
  public RelationalExpressionTypeAST getType()
  {
    return type;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "" + firstExpression + type.getOperatorString() + secondExpression;
  }

}

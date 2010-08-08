package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class RelationalExpression extends Expression
{

  private Expression firstExpression;
  private Expression secondExpression;
  private RelationalExpressionType type;

  /**
   * Creates a new relational expression with the specified first and second expressions and type
   * @param firstExpression - the first expression to compare
   * @param type - the relational expression type
   * @param secondExpression - the second expression to compare
   * @param parseInfo - the parsing information
   */
  public RelationalExpression(Expression firstExpression, RelationalExpressionType type, Expression secondExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.firstExpression = firstExpression;
    this.secondExpression = secondExpression;
    this.type = type;
  }

  /**
   * @return the firstExpression
   */
  public Expression getFirstExpression()
  {
    return firstExpression;
  }

  /**
   * @return the secondExpression
   */
  public Expression getSecondExpression()
  {
    return secondExpression;
  }

  /**
   * @return the type
   */
  public RelationalExpressionType getType()
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

package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InlineIfExpression extends Expression
{

  private Expression condition;
  private Expression trueExpression;
  private Expression falseExpression;

  /**
   * Creates a new InlineIfExpression with the specified condition and results
   * @param condition - the condition of the inline if
   * @param trueExpression - the result if the condition is true
   * @param falseExpression - the result if the condition is false
   * @param parseInfo - the parsing information
   */
  public InlineIfExpression(Expression condition, Expression trueExpression, Expression falseExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.condition = condition;
    this.trueExpression = trueExpression;
    this.falseExpression = falseExpression;
  }

  /**
   * @return the condition
   */
  public Expression getCondition()
  {
    return condition;
  }

  /**
   * @return the trueExpression
   */
  public Expression getTrueExpression()
  {
    return trueExpression;
  }

  /**
   * @return the falseExpression
   */
  public Expression getFalseExpression()
  {
    return falseExpression;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return condition + " ? " + trueExpression + " : " + falseExpression;
  }

}

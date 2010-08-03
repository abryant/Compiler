package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * A left-recursive expression which handles lists of sub-expressions.
 * This should be subclassed by any left-recursive expressions that wish to handle a list of sub-expressions easily
 * @author Anthony Bryant
 */
public abstract class LeftRecursiveExpression extends Expression
{

  private Expression[] subExpressions;

  /**
   * Creates a new LeftRecursiveExpression with the specified initial subExpressions
   * @param firstExpression - the first sub expression of this LeftRecursiveExpression
   * @param secondExpression - the second sub expression of this LeftRecursiveExpression
   */
  public LeftRecursiveExpression(Expression firstExpression, Expression secondExpression)
  {
    this.subExpressions = new Expression[] {firstExpression, secondExpression};
  }

  /**
   * Creates a new LeftRecursiveExpression from all of the sub expressions in startExpression plus a new subExpression
   * @param startExpression - the existing LeftRecursiveExpression to take subExpressions from
   * @param subExpression - the new sub expression for this LeftRecursiveExpression
   */
  public LeftRecursiveExpression(LeftRecursiveExpression startExpression, Expression subExpression)
  {
    Expression[] oldList = startExpression.getSubExpressions();
    subExpressions = new Expression[oldList.length + 1];
    System.arraycopy(oldList, 0, subExpressions, 0, oldList.length);
    subExpressions[oldList.length] = subExpression;
  }

  /**
   * @return the subExpressions
   */
  public Expression[] getSubExpressions()
  {
    return subExpressions;
  }

  /**
   * @param index - the index of the sub expression to get the separator after
   * @return the separator after the specified sub expression inside this left recursive expression
   */
  protected abstract String getSeparator(int index);

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < subExpressions.length; i++)
    {
      buffer.append(subExpressions[i]);
      if (i != subExpressions.length - 1)
      {
        buffer.append(getSeparator(i));
      }
    }
    return buffer.toString();
  }
}

package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * A left-recursive expression which handles lists of sub-expressions.
 * This should be subclassed by any left-recursive expressions that wish to handle a list of sub-expressions easily
 * @author Anthony Bryant
 */
public abstract class LeftRecursiveExpressionAST extends ExpressionAST
{

  private ExpressionAST[] subExpressions;

  /**
   * Creates a new LeftRecursiveExpressionAST with the specified initial subExpressions
   * @param firstExpression - the first sub expression of this LeftRecursiveExpressionAST
   * @param secondExpression - the second sub expression of this LeftRecursiveExpressionAST
   * @param parseInfo - the parsing information
   */
  public LeftRecursiveExpressionAST(ExpressionAST firstExpression, ExpressionAST secondExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.subExpressions = new ExpressionAST[] {firstExpression, secondExpression};
  }

  /**
   * Creates a new LeftRecursiveExpressionAST from all of the sub expressions in startExpression plus a new subExpression
   * @param startExpression - the existing LeftRecursiveExpressionAST to take subExpressions from
   * @param subExpression - the new sub expression for this LeftRecursiveExpressionAST
   * @param parseInfo - the parsing information
   */
  public LeftRecursiveExpressionAST(LeftRecursiveExpressionAST startExpression, ExpressionAST subExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    ExpressionAST[] oldList = startExpression.getSubExpressions();
    subExpressions = new ExpressionAST[oldList.length + 1];
    System.arraycopy(oldList, 0, subExpressions, 0, oldList.length);
    subExpressions[oldList.length] = subExpression;
  }

  /**
   * @return the subExpressions
   */
  public ExpressionAST[] getSubExpressions()
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
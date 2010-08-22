package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleExpression extends Expression
{

  private Expression[] expressions;

  /**
   * Creates a new TupleExpression from the specified array of expressions
   * @param expressions - the expressions to store in this TupleExpression
   * @param parseInfo - the parsing information
   */
  public TupleExpression(Expression[] expressions, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.expressions = expressions;
  }

  /**
   * @return the expressions
   */
  public Expression[] getExpressions()
  {
    return expressions;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < expressions.length; i++)
    {
      buffer.append(expressions[i]);
      if (i != expressions.length - 1)
      {
        buffer.append(", ");
      }
    }
    return buffer.toString();
  }

}

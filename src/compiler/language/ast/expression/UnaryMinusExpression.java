package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class UnaryMinusExpression extends Expression
{

  private Expression subExpression;

  /**
   * Creates a new UnaryMinusExpression with the specified subExpression
   * @param subExpression - the subExpression of this UnaryMinusExpression
   * @param parseInfo - the parsing information
   */
  public UnaryMinusExpression(Expression subExpression, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.subExpression = subExpression;
  }

  /**
   * @return the subExpression
   */
  public Expression getSubExpression()
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
    return "-" + subExpression;
  }

}

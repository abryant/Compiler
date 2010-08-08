package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanNotExpression extends Expression
{

  private Expression subExpression;

  /**
   * Creates a new BooleanNotExpression with the specified subExpression
   * @param subExpression - the expression that will have the boolean not operation performed on it
   * @param parseInfo - the parsing information
   */
  public BooleanNotExpression(Expression subExpression, ParseInfo parseInfo)
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
    return "!" + subExpression;
  }

}

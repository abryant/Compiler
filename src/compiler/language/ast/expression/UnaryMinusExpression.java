package compiler.language.ast.expression;

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
   */
  public UnaryMinusExpression(Expression subExpression)
  {
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

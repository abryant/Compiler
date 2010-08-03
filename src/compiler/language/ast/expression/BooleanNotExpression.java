package compiler.language.ast.expression;

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
   */
  public BooleanNotExpression(Expression subExpression)
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
    return "!" + subExpression;
  }

}

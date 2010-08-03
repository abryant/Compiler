package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayAccessExpression extends Expression
{

  private Expression arrayExpression;
  private Expression indexExpression;

  /**
   * Creates a new ArrayAccessExpression with the specified array and index expressions
   * @param arrayExpression - the expression for the array that is being accessed
   * @param indexExpression - the expression for the index into the array
   */
  public ArrayAccessExpression(Expression arrayExpression, Expression indexExpression)
  {
    this.arrayExpression = arrayExpression;
    this.indexExpression = indexExpression;
  }

  /**
   * @return the arrayExpression
   */
  public Expression getArrayExpression()
  {
    return arrayExpression;
  }

  /**
   * @return the indexExpression
   */
  public Expression getIndexExpression()
  {
    return indexExpression;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return arrayExpression + "[" + indexExpression + "]";
  }

}

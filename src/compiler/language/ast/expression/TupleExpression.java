package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleExpression extends LeftRecursiveExpression
{

  public TupleExpression(Expression firstExpression, Expression secondExpression)
  {
    super(firstExpression, secondExpression);
  }

  public TupleExpression(TupleExpression startExpression, Expression subExpression)
  {
    super(startExpression, subExpression);
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.expression.LeftRecursiveExpression#getSeparator()
   */
  @Override
  protected String getSeparator(int index)
  {
    return ", ";
  }

}

package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanAndExpression extends LeftRecursiveExpression
{

  public BooleanAndExpression(Expression firstExpression, Expression secondExpression)
  {
    super(firstExpression, secondExpression);
  }

  public BooleanAndExpression(BooleanAndExpression startExpression, Expression subExpression)
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
    return " && ";
  }

}

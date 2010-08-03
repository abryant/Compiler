package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BitwiseXorExpression extends LeftRecursiveExpression
{

  public BitwiseXorExpression(Expression firstExpression, Expression secondExpression)
  {
    super(firstExpression, secondExpression);
  }

  public BitwiseXorExpression(BitwiseXorExpression startExpression, Expression subExpression)
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
    return " ^ ";
  }

}

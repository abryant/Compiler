package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AdditiveExpression extends LeftRecursiveExpression
{

  private AdditiveExpressionType[] separatorTypes;

  public AdditiveExpression(Expression firstExpression, AdditiveExpressionType separator, Expression secondExpression)
  {
    super(firstExpression, secondExpression);
    separatorTypes = new AdditiveExpressionType[] {separator};
  }

  public AdditiveExpression(AdditiveExpression startExpression, AdditiveExpressionType separator, Expression subExpression)
  {
    super(startExpression, subExpression);
    AdditiveExpressionType[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new AdditiveExpressionType[oldList.length];
    System.arraycopy(oldList, 0, separatorTypes, 0, oldList.length);
    separatorTypes[oldList.length] = separator;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.expression.LeftRecursiveExpression#getSeparator(int)
   */
  @Override
  protected String getSeparator(int index)
  {
    switch (separatorTypes[index])
    {
    case PLUS:
      return " + ";
    case MINUS:
      return " - ";
    }
    return null;
  }

  /**
   * @return the separatorTypes
   */
  public AdditiveExpressionType[] getSeparatorTypes()
  {
    return separatorTypes;
  }

}

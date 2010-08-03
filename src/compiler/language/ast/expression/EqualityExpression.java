package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class EqualityExpression extends LeftRecursiveExpression
{

  private EqualityExpressionType[] separatorTypes;

  public EqualityExpression(Expression firstExpression, EqualityExpressionType separator, Expression secondExpression)
  {
    super(firstExpression, secondExpression);
    separatorTypes = new EqualityExpressionType[] {separator};
  }

  public EqualityExpression(EqualityExpression startExpression, EqualityExpressionType separator, Expression subExpression)
  {
    super(startExpression, subExpression);
    EqualityExpressionType[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new EqualityExpressionType[oldList.length];
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
    case EQUAL:
      return " == ";
    case NOT_EQUAL:
      return " != ";
    }
    return null;
  }

  /**
   * @return the separatorTypes
   */
  public EqualityExpressionType[] getSeparatorTypes()
  {
    return separatorTypes;
  }

}

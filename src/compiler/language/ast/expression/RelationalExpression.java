package compiler.language.ast.expression;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class RelationalExpression extends LeftRecursiveExpression
{

  private RelationalExpressionType[] separatorTypes;

  public RelationalExpression(Expression firstExpression, RelationalExpressionType separator, Expression secondExpression)
  {
    super(firstExpression, secondExpression);
    separatorTypes = new RelationalExpressionType[] {separator};
  }

  public RelationalExpression(RelationalExpression startExpression, RelationalExpressionType separator, Expression subExpression)
  {
    super(startExpression, subExpression);
    RelationalExpressionType[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new RelationalExpressionType[oldList.length];
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
    case LESS_THAN:
      return " < ";
    case GREATER_THAN:
      return " > ";
    case LESS_THAN_EQUAL:
      return " <= ";
    case GREATER_THAN_EQUAL:
      return " >= ";
    case INSTANCEOF:
      return " instanceof ";
    }
    return null;
  }

  /**
   * @return the separatorTypes
   */
  public RelationalExpressionType[] getSeparatorTypes()
  {
    return separatorTypes;
  }

}

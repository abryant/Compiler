package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class EqualityExpression extends LeftRecursiveExpression
{

  private EqualityExpressionType[] separatorTypes;

  public EqualityExpression(Expression firstExpression, EqualityExpressionType separator, Expression secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
    separatorTypes = new EqualityExpressionType[] {separator};
  }

  public EqualityExpression(EqualityExpression startExpression, EqualityExpressionType separator, Expression subExpression, ParseInfo parseInfo)
  {
    super(startExpression, subExpression, parseInfo);
    EqualityExpressionType[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new EqualityExpressionType[oldList.length + 1];
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

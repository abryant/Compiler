package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AdditiveExpression extends LeftRecursiveExpression
{

  private AdditiveExpressionType[] separatorTypes;

  public AdditiveExpression(Expression firstExpression, AdditiveExpressionType separator, Expression secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
    separatorTypes = new AdditiveExpressionType[] {separator};
  }

  public AdditiveExpression(AdditiveExpression startExpression, AdditiveExpressionType separator, Expression subExpression, ParseInfo parseInfo)
  {
    super(startExpression, subExpression, parseInfo);
    AdditiveExpressionType[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new AdditiveExpressionType[oldList.length + 1];
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

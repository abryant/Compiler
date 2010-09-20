package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class MultiplicativeExpression extends LeftRecursiveExpression
{

  private MultiplicativeExpressionType[] separatorTypes;

  public MultiplicativeExpression(Expression firstExpression, MultiplicativeExpressionType separator, Expression secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
    separatorTypes = new MultiplicativeExpressionType[] {separator};
  }

  public MultiplicativeExpression(MultiplicativeExpression startExpression, MultiplicativeExpressionType separator, Expression subExpression, ParseInfo parseInfo)
  {
    super(startExpression, subExpression, parseInfo);
    MultiplicativeExpressionType[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new MultiplicativeExpressionType[oldList.length + 1];
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
    case TIMES:
      return " * ";
    case DIVIDE:
      return " / ";
    case MODULUS:
      return " % ";
    }
    return null;
  }

  /**
   * @return the separatorTypes
   */
  public MultiplicativeExpressionType[] getSeparatorTypes()
  {
    return separatorTypes;
  }

}

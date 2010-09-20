package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ShiftExpression extends LeftRecursiveExpression
{

  private ShiftExpressionType[] separatorTypes;

  public ShiftExpression(Expression firstExpression, ShiftExpressionType separator, Expression secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
    separatorTypes = new ShiftExpressionType[] {separator};
  }

  public ShiftExpression(ShiftExpression startExpression, ShiftExpressionType separator, Expression subExpression, ParseInfo parseInfo)
  {
    super(startExpression, subExpression, parseInfo);
    ShiftExpressionType[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new ShiftExpressionType[oldList.length + 1];
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
    case LEFT_SHIFT:
      return " << ";
    case ARITHMETIC_RIGHT_SHIFT:
      return " >> ";
    case LOGICAL_RIGHT_SHIFT:
      return " >>> ";
    }
    return null;
  }

  /**
   * @return the separatorTypes
   */
  public ShiftExpressionType[] getSeparatorTypes()
  {
    return separatorTypes;
  }

}

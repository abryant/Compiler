package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AdditiveExpressionAST extends LeftRecursiveExpressionAST
{

  private AdditiveExpressionTypeAST[] separatorTypes;

  public AdditiveExpressionAST(ExpressionAST firstExpression, AdditiveExpressionTypeAST separator, ExpressionAST secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
    separatorTypes = new AdditiveExpressionTypeAST[] {separator};
  }

  public AdditiveExpressionAST(AdditiveExpressionAST startExpression, AdditiveExpressionTypeAST separator, ExpressionAST subExpression, ParseInfo parseInfo)
  {
    super(startExpression, subExpression, parseInfo);
    AdditiveExpressionTypeAST[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new AdditiveExpressionTypeAST[oldList.length + 1];
    System.arraycopy(oldList, 0, separatorTypes, 0, oldList.length);
    separatorTypes[oldList.length] = separator;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.expression.LeftRecursiveExpressionAST#getSeparator(int)
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
  public AdditiveExpressionTypeAST[] getSeparatorTypes()
  {
    return separatorTypes;
  }

}

package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BitwiseOrExpression extends LeftRecursiveExpression
{

  public BitwiseOrExpression(Expression firstExpression, Expression secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
  }

  public BitwiseOrExpression(BitwiseOrExpression startExpression, Expression subExpression, ParseInfo parseInfo)
  {
    super(startExpression, subExpression, parseInfo);
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.expression.LeftRecursiveExpression#getSeparator()
   */
  @Override
  protected String getSeparator(int index)
  {
    return " | ";
  }

}

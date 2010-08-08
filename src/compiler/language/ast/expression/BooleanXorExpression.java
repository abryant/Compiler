package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanXorExpression extends LeftRecursiveExpression
{

  public BooleanXorExpression(Expression firstExpression, Expression secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
  }

  public BooleanXorExpression(BooleanXorExpression startExpression, Expression subExpression, ParseInfo parseInfo)
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
    return " ^^ ";
  }

}

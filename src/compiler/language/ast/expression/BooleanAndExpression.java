package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanAndExpression extends LeftRecursiveExpression
{

  public BooleanAndExpression(Expression firstExpression, Expression secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
  }

  public BooleanAndExpression(BooleanAndExpression startExpression, Expression subExpression, ParseInfo parseInfo)
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
    return " && ";
  }

}

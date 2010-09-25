package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanAndExpressionAST extends LeftRecursiveExpressionAST
{

  public BooleanAndExpressionAST(ExpressionAST firstExpression, ExpressionAST secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
  }

  public BooleanAndExpressionAST(BooleanAndExpressionAST startExpression, ExpressionAST subExpression, ParseInfo parseInfo)
  {
    super(startExpression, subExpression, parseInfo);
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.expression.LeftRecursiveExpressionAST#getSeparator()
   */
  @Override
  protected String getSeparator(int index)
  {
    return " && ";
  }

}

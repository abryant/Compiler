package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanXorExpressionAST extends LeftRecursiveExpressionAST
{

  public BooleanXorExpressionAST(ExpressionAST firstExpression, ExpressionAST secondExpression, ParseInfo parseInfo)
  {
    super(firstExpression, secondExpression, parseInfo);
  }

  public BooleanXorExpressionAST(BooleanXorExpressionAST startExpression, ExpressionAST subExpression, ParseInfo parseInfo)
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
    return " ^^ ";
  }

}

package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanOrExpressionAST extends LeftRecursiveExpressionAST
{

  public BooleanOrExpressionAST(ExpressionAST firstExpression, ExpressionAST secondExpression, LexicalPhrase lexicalPhrase)
  {
    super(firstExpression, secondExpression, lexicalPhrase);
  }

  public BooleanOrExpressionAST(BooleanOrExpressionAST startExpression, ExpressionAST subExpression, LexicalPhrase lexicalPhrase)
  {
    super(startExpression, subExpression, lexicalPhrase);
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.expression.LeftRecursiveExpressionAST#getSeparator()
   */
  @Override
  protected String getSeparator(int index)
  {
    return " || ";
  }

}

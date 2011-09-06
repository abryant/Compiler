package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 5 Sep 2011
 */

/**
 * @author Anthony Bryant
 */
public class SubtractionExpressionAST extends BinaryOperatorExpressionAST
{

  public SubtractionExpressionAST(ExpressionAST leftExpression, ExpressionAST rightExpression, LexicalPhrase lexicalPhrase)
  {
    super(leftExpression, rightExpression, lexicalPhrase);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSeparator()
  {
    return " - ";
  }

}

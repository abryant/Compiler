package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BitwiseAndExpressionAST extends BinaryOperatorExpressionAST
{

  public BitwiseAndExpressionAST(ExpressionAST leftExpression, ExpressionAST rightExpression, LexicalPhrase lexicalPhrase)
  {
    super(leftExpression, rightExpression, lexicalPhrase);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSeparator()
  {
    return " & ";
  }

}

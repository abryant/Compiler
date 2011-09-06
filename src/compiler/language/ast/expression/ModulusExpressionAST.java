package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 6 Sep 2011
 */

/**
 * @author Anthony Bryant
 */
public class ModulusExpressionAST extends BinaryOperatorExpressionAST
{

  public ModulusExpressionAST(ExpressionAST leftExpression, ExpressionAST rightExpression, LexicalPhrase lexicalPhrase)
  {
    super(leftExpression, rightExpression, lexicalPhrase);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSeparator()
  {
    return " % ";
  }

}

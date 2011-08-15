package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BitwiseXorExpressionAST extends LeftRecursiveExpressionAST
{

  public BitwiseXorExpressionAST(ExpressionAST firstExpression, ExpressionAST secondExpression, LexicalPhrase lexicalPhrase)
  {
    super(firstExpression, secondExpression, lexicalPhrase);
  }

  public BitwiseXorExpressionAST(BitwiseXorExpressionAST startExpression, ExpressionAST subExpression, LexicalPhrase lexicalPhrase)
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
    return " ^ ";
  }

}

package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class EqualityExpressionAST extends BinaryOperatorExpressionAST
{

  private EqualityExpressionTypeAST separatorType;

  public EqualityExpressionAST(ExpressionAST firstExpression, EqualityExpressionTypeAST separator, ExpressionAST secondExpression, LexicalPhrase lexicalPhrase)
  {
    super(firstExpression, secondExpression, lexicalPhrase);
    this.separatorType = separator;
  }

  /**
   * @return the separatorType
   */
  public EqualityExpressionTypeAST getSeparatorType()
  {
    return separatorType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSeparator()
  {
    switch (separatorType)
    {
    case EQUAL:
      return " == ";
    case NOT_EQUAL:
      return " != ";
    }
    return null;
  }

}

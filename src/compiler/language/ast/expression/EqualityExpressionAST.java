package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class EqualityExpressionAST extends LeftRecursiveExpressionAST
{

  private EqualityExpressionTypeAST[] separatorTypes;

  public EqualityExpressionAST(ExpressionAST firstExpression, EqualityExpressionTypeAST separator, ExpressionAST secondExpression, LexicalPhrase lexicalPhrase)
  {
    super(firstExpression, secondExpression, lexicalPhrase);
    separatorTypes = new EqualityExpressionTypeAST[] {separator};
  }

  public EqualityExpressionAST(EqualityExpressionAST startExpression, EqualityExpressionTypeAST separator, ExpressionAST subExpression, LexicalPhrase lexicalPhrase)
  {
    super(startExpression, subExpression, lexicalPhrase);
    EqualityExpressionTypeAST[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new EqualityExpressionTypeAST[oldList.length + 1];
    System.arraycopy(oldList, 0, separatorTypes, 0, oldList.length);
    separatorTypes[oldList.length] = separator;
  }

  /**
   * {@inheritDoc}
   * @see compiler.language.ast.expression.LeftRecursiveExpressionAST#getSeparator(int)
   */
  @Override
  protected String getSeparator(int index)
  {
    switch (separatorTypes[index])
    {
    case EQUAL:
      return " == ";
    case NOT_EQUAL:
      return " != ";
    }
    return null;
  }

  /**
   * @return the separatorTypes
   */
  public EqualityExpressionTypeAST[] getSeparatorTypes()
  {
    return separatorTypes;
  }

}

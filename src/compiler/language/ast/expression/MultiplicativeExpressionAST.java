package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class MultiplicativeExpressionAST extends LeftRecursiveExpressionAST
{

  private MultiplicativeExpressionTypeAST[] separatorTypes;

  public MultiplicativeExpressionAST(ExpressionAST firstExpression, MultiplicativeExpressionTypeAST separator, ExpressionAST secondExpression, LexicalPhrase lexicalPhrase)
  {
    super(firstExpression, secondExpression, lexicalPhrase);
    separatorTypes = new MultiplicativeExpressionTypeAST[] {separator};
  }

  public MultiplicativeExpressionAST(MultiplicativeExpressionAST startExpression, MultiplicativeExpressionTypeAST separator, ExpressionAST subExpression, LexicalPhrase lexicalPhrase)
  {
    super(startExpression, subExpression, lexicalPhrase);
    MultiplicativeExpressionTypeAST[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new MultiplicativeExpressionTypeAST[oldList.length + 1];
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
    case TIMES:
      return " * ";
    case DIVIDE:
      return " / ";
    case MODULUS:
      return " % ";
    }
    return null;
  }

  /**
   * @return the separatorTypes
   */
  public MultiplicativeExpressionTypeAST[] getSeparatorTypes()
  {
    return separatorTypes;
  }

}

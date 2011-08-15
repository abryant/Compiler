package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ShiftExpressionAST extends LeftRecursiveExpressionAST
{

  private ShiftExpressionTypeAST[] separatorTypes;

  public ShiftExpressionAST(ExpressionAST firstExpression, ShiftExpressionTypeAST separator, ExpressionAST secondExpression, LexicalPhrase lexicalPhrase)
  {
    super(firstExpression, secondExpression, lexicalPhrase);
    separatorTypes = new ShiftExpressionTypeAST[] {separator};
  }

  public ShiftExpressionAST(ShiftExpressionAST startExpression, ShiftExpressionTypeAST separator, ExpressionAST subExpression, LexicalPhrase lexicalPhrase)
  {
    super(startExpression, subExpression, lexicalPhrase);
    ShiftExpressionTypeAST[] oldList = startExpression.getSeparatorTypes();
    separatorTypes = new ShiftExpressionTypeAST[oldList.length + 1];
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
    case LEFT_SHIFT:
      return " << ";
    case ARITHMETIC_RIGHT_SHIFT:
      return " >> ";
    case LOGICAL_RIGHT_SHIFT:
      return " >>> ";
    }
    return null;
  }

  /**
   * @return the separatorTypes
   */
  public ShiftExpressionTypeAST[] getSeparatorTypes()
  {
    return separatorTypes;
  }

}

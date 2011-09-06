package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 6 Sep 2011
 */

/**
 * An abstract binary expression.
 * This should be subclassed by any binary operator expressions that want storage of (and getters for) several fields with almost no code.
 * @author Anthony Bryant
 */
public abstract class BinaryOperatorExpressionAST extends ExpressionAST
{

  private ExpressionAST leftExpression;
  private ExpressionAST rightExpression;

  /**
   * Creates a new BinaryOperatorExpressionAST which holds left and right sub-expressions and a LexicalPhrase
   * @param leftExpression - the left sub-expression
   * @param rightExpression - the right sub-expression
   * @param lexicalPhrase - the LexicalPhrase for this expression
   */
  public BinaryOperatorExpressionAST(ExpressionAST leftExpression, ExpressionAST rightExpression, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.leftExpression = leftExpression;
    this.rightExpression = rightExpression;
  }

  /**
   * @return the leftExpression
   */
  public ExpressionAST getLeftExpression()
  {
    return leftExpression;
  }

  /**
   * @return the rightExpression
   */
  public ExpressionAST getRightExpression()
  {
    return rightExpression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString()
  {
    return leftExpression + getSeparator() + rightExpression;
  }

  /**
   * @return the separator between the two sub-expressions of this expression
   */
  public abstract String getSeparator();

}

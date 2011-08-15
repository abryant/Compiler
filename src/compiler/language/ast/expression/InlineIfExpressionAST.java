package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InlineIfExpressionAST extends ExpressionAST
{

  private ExpressionAST condition;
  private ExpressionAST trueExpression;
  private ExpressionAST falseExpression;

  /**
   * Creates a new InlineIfExpressionAST with the specified condition and results
   * @param condition - the condition of the inline if
   * @param trueExpression - the result if the condition is true
   * @param falseExpression - the result if the condition is false
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public InlineIfExpressionAST(ExpressionAST condition, ExpressionAST trueExpression, ExpressionAST falseExpression, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.condition = condition;
    this.trueExpression = trueExpression;
    this.falseExpression = falseExpression;
  }

  /**
   * @return the condition
   */
  public ExpressionAST getCondition()
  {
    return condition;
  }

  /**
   * @return the trueExpression
   */
  public ExpressionAST getTrueExpression()
  {
    return trueExpression;
  }

  /**
   * @return the falseExpression
   */
  public ExpressionAST getFalseExpression()
  {
    return falseExpression;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return condition + " ? " + trueExpression + " : " + falseExpression;
  }

}

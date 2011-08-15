package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class UnaryPlusExpressionAST extends ExpressionAST
{

  private ExpressionAST subExpression;

  /**
   * Creates a new UnaryPlusExpressionAST with the specified subExpression
   * @param subExpression - the subExpression of this UnaryPlusExpressionAST
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public UnaryPlusExpressionAST(ExpressionAST subExpression, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.subExpression = subExpression;
  }

  /**
   * @return the subExpression
   */
  public ExpressionAST getSubExpression()
  {
    return subExpression;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "+" + subExpression;
  }

}

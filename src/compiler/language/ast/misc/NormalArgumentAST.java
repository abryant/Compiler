package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 16 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class NormalArgumentAST extends ArgumentAST
{

  private ExpressionAST expression;

  /**
   * Creates a new normal argument with the specified expression
   * @param expression - the expression to create this normal argument with
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public NormalArgumentAST(ExpressionAST expression, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.expression = expression;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return String.valueOf(expression);
  }
}

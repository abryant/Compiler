package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleExpressionAST extends ExpressionAST
{

  private ExpressionAST[] expressions;

  /**
   * Creates a new TupleExpressionAST from the specified array of expressions
   * @param expressions - the expressions to store in this TupleExpressionAST
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public TupleExpressionAST(ExpressionAST[] expressions, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.expressions = expressions;
  }

  /**
   * @return the expressions
   */
  public ExpressionAST[] getExpressions()
  {
    return expressions;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < expressions.length; i++)
    {
      buffer.append(expressions[i]);
      if (i != expressions.length - 1)
      {
        buffer.append(", ");
      }
    }
    return buffer.toString();
  }

}

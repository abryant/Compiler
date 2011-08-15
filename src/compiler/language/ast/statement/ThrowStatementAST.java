package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThrowStatementAST extends StatementAST
{

  private ExpressionAST expression;

  /**
   * Creates a new ThrowStatementAST to throw the specified ExpressionAST.
   * @param expression - the expression to throw
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ThrowStatementAST(ExpressionAST expression, LexicalPhrase lexicalPhrase)
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
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "throw " + expression + ";";
  }
}

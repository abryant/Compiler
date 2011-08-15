package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ReturnStatementAST extends StatementAST
{

  private ExpressionAST expression;

  /**
   * Creates a new ReturnStatementAST to return the result of the specified expression.
   * @param expression - the expression to return
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ReturnStatementAST(ExpressionAST expression, LexicalPhrase lexicalPhrase)
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
    return "return " + expression + ";";
  }
}

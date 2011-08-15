package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.IntegerLiteralAST;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleIndexExpressionAST extends ExpressionAST
{

  private ExpressionAST expression;
  private IntegerLiteralAST indexLiteral;

  /**
   * Creates a new TupleIndexExpressionAST with the specified expression and index into the tuple that the expression returns
   * @param expression - the expression to index
   * @param indexLiteral - the index into the tuple, as an IntegerLiteralAST
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public TupleIndexExpressionAST(ExpressionAST expression, IntegerLiteralAST indexLiteral, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.expression = expression;
    this.indexLiteral = indexLiteral;
  }

  /**
   * @return the expression
   */
  public ExpressionAST getExpression()
  {
    return expression;
  }

  /**
   * @return the indexLiteral
   */
  public IntegerLiteralAST getIndexLiteral()
  {
    return indexLiteral;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return expression + " ! " + indexLiteral;
  }

}

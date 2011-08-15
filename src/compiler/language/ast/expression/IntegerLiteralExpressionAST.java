package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.IntegerLiteralAST;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerLiteralExpressionAST extends ExpressionAST
{

  private IntegerLiteralAST literal;

  /**
   * Creates a new IntegerLiteralExpressionAST with the specified literal value.
   * @param literal - the integer literal
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public IntegerLiteralExpressionAST(IntegerLiteralAST literal, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public IntegerLiteralAST getLiteral()
  {
    return literal;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return literal.toString();
  }
}

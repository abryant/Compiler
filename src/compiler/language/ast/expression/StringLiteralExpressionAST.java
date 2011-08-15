package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.StringLiteralAST;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class StringLiteralExpressionAST extends ExpressionAST
{

  private StringLiteralAST literal;

  /**
   * Creates a new StringLiteralExpressionAST with the specified literal value.
   * @param literal - the String literal
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public StringLiteralExpressionAST(StringLiteralAST literal, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public StringLiteralAST getLiteral()
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

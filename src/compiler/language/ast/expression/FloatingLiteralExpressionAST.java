package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.FloatingLiteralAST;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingLiteralExpressionAST extends ExpressionAST
{

  private FloatingLiteralAST literal;

  /**
   * Creates a new FloatingLiteralExpressionAST with the specified literal value.
   * @param literal - the floating literal
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public FloatingLiteralExpressionAST(FloatingLiteralAST literal, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public FloatingLiteralAST getLiteral()
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

package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.CharacterLiteralAST;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CharacterLiteralExpressionAST extends ExpressionAST
{

  private CharacterLiteralAST literal;

  /**
   * Creates a new CharacterLiteralExpressionAST with the specified literal value.
   * @param literal - the character literal
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public CharacterLiteralExpressionAST(CharacterLiteralAST literal, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public CharacterLiteralAST getLiteral()
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

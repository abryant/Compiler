package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
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
   * @param parseInfo - the parsing information
   */
  public CharacterLiteralExpressionAST(CharacterLiteralAST literal, ParseInfo parseInfo)
  {
    super(parseInfo);
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

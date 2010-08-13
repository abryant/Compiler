package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.CharacterLiteral;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CharacterLiteralExpression extends Expression
{

  private CharacterLiteral literal;

  /**
   * Creates a new CharacterLiteralExpression with the specified literal value.
   * @param literal - the character literal
   * @param parseInfo - the parsing information
   */
  public CharacterLiteralExpression(CharacterLiteral literal, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.literal = literal;
  }

  /**
   * @return the literal
   */
  public CharacterLiteral getLiteral()
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

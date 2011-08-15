package compiler.language.ast.terminal;

import compiler.language.LexicalPhrase;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CharacterLiteralAST
{

  private LexicalPhrase lexicalPhrase;

  private char character;
  private String stringRepresentation;

  /**
   * Creates a new character literal with the specified string representation.
   * @param character - the character that this literal represents
   * @param stringRepresentation - the string representation of this character literal, without quotes
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public CharacterLiteralAST(char character, String stringRepresentation, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.character = character;
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

  /**
   * @return the character
   */
  public char getCharacter()
  {
    return character;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return stringRepresentation;
  }

}

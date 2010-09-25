package compiler.language.ast.terminal;

import compiler.language.ast.ParseInfo;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CharacterLiteralAST
{

  private ParseInfo parseInfo;

  private char character;
  private String stringRepresentation;

  /**
   * Creates a new character literal with the specified string representation.
   * @param character - the character that this literal represents
   * @param stringRepresentation - the string representation of this character literal, without quotes
   * @param parseInfo - the parsing information
   */
  public CharacterLiteralAST(char character, String stringRepresentation, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.character = character;
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
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

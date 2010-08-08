package compiler.language.ast.terminal;

import compiler.language.ast.ParseInfo;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerLiteral
{

  private ParseInfo parseInfo;

  private String stringRepresentation;

  /**
   * Creates a new integer literal with the specified string representation.
   * @param stringRepresentation - the string representation of the integer literal
   * @param parseInfo - the parsing information
   */
  public IntegerLiteral(String stringRepresentation, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
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
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return stringRepresentation;
  }
}

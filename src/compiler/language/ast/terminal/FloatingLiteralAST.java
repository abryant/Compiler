package compiler.language.ast.terminal;

import compiler.language.ast.ParseInfo;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingLiteralAST
{

  private ParseInfo parseInfo;

  private String stringRepresentation;

  /**
   * Creates a new floating literal with the specified string representation.
   * @param stringRepresentation - the string representation of the floating literal
   * @param parseInfo - the parsing information
   */
  public FloatingLiteralAST(String stringRepresentation, ParseInfo parseInfo)
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

package compiler.language.ast.terminal;

import compiler.language.ast.ParseInfo;

/*
 * Created on 5 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class StringLiteralAST
{

  private ParseInfo parseInfo;

  private String literalValue;
  private String stringRepresentation;

  /**
   * Creates a new String literal with the specified value
   * @param literalValue - the value of the string literal
   * @param stringRepresentation - the string representation of this literal, including escape sequences and surrounding quotes
   * @param parseInfo - the parsing information
   */
  public StringLiteralAST(String literalValue, String stringRepresentation, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.literalValue = literalValue;
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * @return the literal string
   */
  public String getLiteralValue()
  {
    return literalValue;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return stringRepresentation;
  }

}

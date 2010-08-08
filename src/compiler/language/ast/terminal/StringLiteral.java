package compiler.language.ast.terminal;

import compiler.language.ast.ParseInfo;

/*
 * Created on 5 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class StringLiteral
{

  private ParseInfo parseInfo;

  private String literal;

  /**
   * Creates a new String literal with the specified value
   * @param literal - the value of the string literal
   * @param parseInfo - the parsing information
   */
  public StringLiteral(String literal, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.literal = literal;
  }

  /**
   * @return the literal string
   */
  public String getLiteral()
  {
    return literal;
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
    return literal;
  }

}

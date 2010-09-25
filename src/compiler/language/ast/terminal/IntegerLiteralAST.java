package compiler.language.ast.terminal;

import java.math.BigInteger;

import compiler.language.ast.ParseInfo;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerLiteralAST
{

  private ParseInfo parseInfo;

  private BigInteger value;
  private String stringRepresentation;

  /**
   * Creates a new integer literal with the specified string representation.
   * @param value - the value of this integer literal
   * @param stringRepresentation - the string representation of the integer literal
   * @param parseInfo - the parsing information
   */
  public IntegerLiteralAST(BigInteger value, String stringRepresentation, ParseInfo parseInfo)
  {
    this.value = value;
    this.parseInfo = parseInfo;
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * @return the value
   */
  public BigInteger getValue()
  {
    return value;
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

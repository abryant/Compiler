package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;

/*
 * Created on 30 Jun 2010
 */

/**
 * The base class for all Types.
 * @author Anthony Bryant
 */
public abstract class Type
{

  private ParseInfo parseInfo;

  /**
   * Creates a new Type with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public Type(ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * @param parseInfo - the parseInfo to set
   */
  public void setParseInfo(ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
  }

}

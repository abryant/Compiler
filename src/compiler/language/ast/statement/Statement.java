package compiler.language.ast.statement;

import compiler.language.ast.ParseInfo;

/*
 * Created on 8 Jul 2010
 */

/**
 * The base class for all statements.
 * @author Anthony Bryant
 */
public abstract class Statement
{

  private ParseInfo parseInfo;

  /**
   * Creates a new Statement with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public Statement(ParseInfo parseInfo)
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
   * Sets this Statement's ParseInfo. This should only be used during parsing.
   * @param parseInfo - the parseInfo to set
   */
  public void setParseInfo(ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
  }

}

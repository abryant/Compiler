package compiler.language.ast.expression;

import compiler.language.ast.ParseInfo;

/*
 * Created on 5 Jul 2010
 */

/**
 * The abstract base class for all expressions.
 * @author Anthony Bryant
 */
public abstract class Expression
{

  private ParseInfo parseInfo;

  /**
   * Creates a new Expression with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public Expression(ParseInfo parseInfo)
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

}

package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;

/*
 * Created on 16 Jul 2010
 */

/**
 * The base class for all parameters
 * @author Anthony Bryant
 */
public abstract class Parameter
{

  private ParseInfo parseInfo;

  /**
   * Creates a new Parameter with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public Parameter(ParseInfo parseInfo)
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

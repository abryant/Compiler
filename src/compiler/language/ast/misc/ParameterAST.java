package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;

/*
 * Created on 10 Jul 2010
 */

/**
 * The base class for all parameters
 * @author Anthony Bryant
 */
public abstract class ParameterAST
{

  private ParseInfo parseInfo;

  /**
   * Creates a new ParameterAST with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public ParameterAST(ParseInfo parseInfo)
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

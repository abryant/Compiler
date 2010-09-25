package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;

/*
 * Created on 10 Jul 2010
 */

/**
 * The base class for all arguments
 * @author Anthony Bryant
 */
public abstract class ArgumentAST
{

  private ParseInfo parseInfo;

  /**
   * Creates a new ArgumentAST with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public ArgumentAST(ParseInfo parseInfo)
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

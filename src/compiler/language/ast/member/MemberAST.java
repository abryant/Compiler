package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public abstract class MemberAST
{

  private ParseInfo parseInfo;

  /**
   * Creates a new MemberAST with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public MemberAST(ParseInfo parseInfo)
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

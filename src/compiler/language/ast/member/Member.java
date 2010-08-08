package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public abstract class Member
{

  private ParseInfo parseInfo;

  /**
   * Creates a new Member with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public Member(ParseInfo parseInfo)
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

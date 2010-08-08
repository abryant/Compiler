package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;


/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class Assignee
{

  private ParseInfo parseInfo;

  /**
   * Creates a new Assignee that does not assign to anything. This is represented by an underscore in the language syntax.
   * @param parseInfo - the parsing information
   */
  public Assignee(ParseInfo parseInfo)
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
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "_";
  }
}

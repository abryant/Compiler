package compiler.language.ast.terminal;

import compiler.language.ast.ParseInfo;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class Name
{

  private ParseInfo parseInfo;

  private String name;

  /**
   * Creates a Name with the specified text and position in the file
   * @param name - the text of the name
   * @param parseInfo - the parsing information
   */
  public Name(String name, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
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
    return name;
  }
}

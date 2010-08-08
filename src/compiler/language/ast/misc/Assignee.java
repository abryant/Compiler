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

  private QName name;

  /**
   * Creates a new Assignee that does not assign to anything. This is represented by an underscore in the language syntax.
   * @param parseInfo - the parsing information
   */
  public Assignee(ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    name = null;
  }

  /**
   * Creates a new Assignee that assigns to the variable with the specified name.
   * @param name - the name of the variable to assign to
   * @param parseInfo - the parsing information
   */
  public Assignee(QName name, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
  }

  /**
   * @return the name of the variable that this Assignee represents
   */
  public QName getName()
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
    if (name == null)
    {
      return "_";
    }
    return name.toString();
  }
}

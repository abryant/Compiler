package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.Name;

/*
 * Created on 12 Aug 2010
 */

/**
 * An assignee that can be used in a Field Declaration
 * @author Anthony Bryant
 */
public class DeclarationAssignee
{

  private ParseInfo parseInfo;

  private Name name;

  /**
   * Creates a new DeclarationAssignee with the specified name
   * @param name - the name of the assignee, or null if no name was intended for this assignee
   * @param parseInfo - the parsing information
   */
  public DeclarationAssignee(Name name, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * {@inheritDoc}
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

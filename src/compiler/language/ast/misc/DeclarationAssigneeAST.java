package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 12 Aug 2010
 */

/**
 * An assignee that can be used in a FieldAST Declaration
 * @author Anthony Bryant
 */
public class DeclarationAssigneeAST
{

  private ParseInfo parseInfo;

  private NameAST name;

  /**
   * Creates a new DeclarationAssigneeAST with the specified name
   * @param name - the name of the assignee, or null if no name was intended for this assignee
   * @param parseInfo - the parsing information
   */
  public DeclarationAssigneeAST(NameAST name, ParseInfo parseInfo)
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
  public NameAST getName()
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

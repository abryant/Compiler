package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;


/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class Modifier
{

  private ParseInfo parseInfo;

  private ModifierType type;

  /**
   * Creates a new modifier of the specified type
   * @param type - the type of this modifier
   * @param parseInfo - the parsing information
   */
  public Modifier(ModifierType type, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.type = type;
  }

  /**
   * @return the type
   */
  public ModifierType getType()
  {
    return type;
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
    return type.toString();
  }

}

package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;

/*
 * Created on 5 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AccessSpecifier
{

  private ParseInfo parseInfo;

  private AccessSpecifierType type;

  /**
   * Creates a new access specifier with the specified type.
   * @param type - the type of this access specifier
   * @param parseInfo - the parsing information
   */
  public AccessSpecifier(AccessSpecifierType type, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.type = type;
  }

  /**
   * @return the type
   */
  public AccessSpecifierType getType()
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
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return type.toString();
  }

}

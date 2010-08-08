package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;


/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class CharacterType extends PrimitiveType
{

  /**
   * Creates a new CharacterType with the specified ParseInfo
   * @param parseInfo - the parsing information
   */
  public CharacterType(ParseInfo parseInfo)
  {
    super(parseInfo);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "char";
  }
}

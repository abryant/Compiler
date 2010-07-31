package compiler.language.ast.type;


/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class CharacterType extends PrimitiveType
{
  // stores nothing (yet), but instances represent the char type

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "char";
  }
}

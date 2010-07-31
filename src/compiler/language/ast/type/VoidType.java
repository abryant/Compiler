package compiler.language.ast.type;


/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class VoidType extends Type
{
  // stores nothing (yet), but instances represent the void type

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "void";
  }
}

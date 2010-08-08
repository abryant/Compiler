package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;


/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class NormalTypeParameter extends TypeParameter
{

  private Type type;

  /**
   * Creates a new normal type parameter with the specified type.
   * @param type - the type assigned to this type parameter
   * @param parseInfo - the parsing information
   */
  public NormalTypeParameter(Type type, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.type = type;
  }

  /**
   * @return the type
   */
  public Type getType()
  {
    return type;
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

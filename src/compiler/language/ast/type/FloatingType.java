package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;


/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingType extends PrimitiveType
{

  private FloatingTypeLength typeLength;

  /**
   * Creates a new Floating type with the specified length
   * @param typeLength - the length of the type
   * @param parseInfo - the parsing information
   */
  public FloatingType(FloatingTypeLength typeLength, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.typeLength = typeLength;
  }

  /**
   * @return the typeLength
   */
  public FloatingTypeLength getTypeLength()
  {
    return typeLength;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return typeLength.toString();
  }
}

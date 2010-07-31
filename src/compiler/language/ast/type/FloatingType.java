package compiler.language.ast.type;


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
   */
  public FloatingType(FloatingTypeLength typeLength)
  {
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

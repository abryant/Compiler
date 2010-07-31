package compiler.language.ast.type;


/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerType extends PrimitiveType
{

  private Boolean signed;
  private IntegerTypeLength typeLength;

  /**
   * Creates a new integer type with the specified signedness and type length
   * @param signed - whether or not the type was specified to be signed, or null if the signedness was not specified
   * @param typeLength - the length of this integer type
   */
  public IntegerType(Boolean signed, IntegerTypeLength typeLength)
  {
    this.signed = signed;
    this.typeLength = typeLength;
  }

  /**
   * @return the signed
   */
  public Boolean getSigned()
  {
    return signed;
  }

  /**
   * @return the typeLength
   */
  public IntegerTypeLength getTypeLength()
  {
    return typeLength;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    if (signed == null)
    {
      return typeLength.toString();
    }
    if (signed)
    {
      return "signed " + typeLength.toString();
    }
    return "unsigned " + typeLength.toString();
  }
}

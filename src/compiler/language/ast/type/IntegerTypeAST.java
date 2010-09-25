package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;


/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class IntegerTypeAST extends PrimitiveTypeAST
{

  private Boolean signed;
  private IntegerTypeLengthAST typeLength;

  /**
   * Creates a new integer type with the specified signedness and type length
   * @param signed - whether or not the type was specified to be signed, or null if the signedness was not specified
   * @param typeLength - the length of this integer type
   * @param parseInfo - the parsing information
   */
  public IntegerTypeAST(Boolean signed, IntegerTypeLengthAST typeLength, ParseInfo parseInfo)
  {
    super(parseInfo);
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
  public IntegerTypeLengthAST getTypeLength()
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

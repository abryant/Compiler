package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayType extends Type
{

  private Type baseType;

  /**
   * Creates a new ArrayType with the specified base type
   * @param baseType - the base type of the array
   * @param parseInfo - the parsing information
   */
  public ArrayType(Type baseType, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.baseType = baseType;
  }

  /**
   * @return the baseType
   */
  public Type getBaseType()
  {
    return baseType;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return baseType + "[]";
  }

}

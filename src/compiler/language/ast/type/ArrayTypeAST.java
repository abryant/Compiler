package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayTypeAST extends TypeAST
{

  private TypeAST baseType;

  /**
   * Creates a new ArrayTypeAST with the specified base type
   * @param baseType - the base type of the array
   * @param parseInfo - the parsing information
   */
  public ArrayTypeAST(TypeAST baseType, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.baseType = baseType;
  }

  /**
   * @return the baseType
   */
  public TypeAST getBaseType()
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

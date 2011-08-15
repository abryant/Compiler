package compiler.language.ast.type;

import compiler.language.LexicalPhrase;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayTypeAST extends TypeAST
{

  private TypeAST baseType;
  private boolean isImmutable;

  /**
   * Creates a new ArrayTypeAST with the specified base type
   * @param baseType - the base type of the array
   * @param isImmutable - true if this array type should be immutable, false otherwise
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ArrayTypeAST(TypeAST baseType, boolean isImmutable, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.baseType = baseType;
    this.isImmutable = isImmutable;
  }

  /**
   * @return the baseType
   */
  public TypeAST getBaseType()
  {
    return baseType;
  }

  /**
   * @return true if this type is immutable, false otherwise
   */
  public boolean isImmutable()
  {
    return isImmutable;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return baseType + (isImmutable ? "#" : "") + "[]";
  }

}

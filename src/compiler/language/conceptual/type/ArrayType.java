package compiler.language.conceptual.type;

/*
 * Created on 7 Mar 2011
 */

/**
 * @author Anthony Bryant
 */
public class ArrayType extends Type
{

  private Type baseType;
  private boolean isImmutable;

  /**
   * Creates a new ArrayType with the specified base type and immutability
   * @param baseType - the base type of this array
   * @param isImmutable - true if this array is immutable, false otherwise
   */
  public ArrayType(Type baseType, boolean isImmutable)
  {
    this.baseType = baseType;
    this.isImmutable = isImmutable;
  }

  /**
   * @return the baseType
   */
  public Type getBaseType()
  {
    return baseType;
  }

  /**
   * @return the isImmutable
   */
  public boolean isImmutable()
  {
    return isImmutable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canAssign(Type type)
  {
    if (!(type instanceof ArrayType))
    {
      return false;
    }
    ArrayType other = (ArrayType) type;
    return (baseType == null ? other.baseType == null : baseType.equals(other.baseType)) &&
           (isImmutable || !other.isImmutable);
  }

}

package compiler.language.ast;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class WildcardTypeParameter extends TypeParameter
{

  private PointerType superType;
  private PointerType subType;

  /**
   * Creates a new wildcard type parameter that bounds its type to the specified range of values.
   * @param superType - the super type of this type parameter
   * @param subType - the sub type of this type parameter
   */
  public WildcardTypeParameter(PointerType superType, PointerType subType)
  {
    this.superType = superType;
    this.subType = subType;
  }

  /**
   * @return the super type
   */
  public PointerType getSuperType()
  {
    return superType;
  }

  /**
   * @return the sub type
   */
  public PointerType getSubType()
  {
    return subType;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("?");
    if (superType != null)
    {
      buffer.append(" extends ");
      buffer.append(superType);
    }
    if (subType != null)
    {
      buffer.append(" super ");
      buffer.append(subType);
    }
    return buffer.toString();
  }

}

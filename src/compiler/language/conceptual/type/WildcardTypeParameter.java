package compiler.language.conceptual.type;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class WildcardTypeParameter extends TypeParameter
{

  private PointerType superType;
  private PointerType subType;

  /**
   * Creates a new WildcardTypeParameter with the specified super and sub types
   * @param superType - the supertype of the type parameter
   * @param subType - the subtype of the type parameter
   */
  public WildcardTypeParameter(PointerType superType, PointerType subType)
  {
    this.superType = superType;
    this.subType = subType;
  }

  /**
   * @return the superType
   */
  public PointerType getSuperType()
  {
    return superType;
  }
  /**
   * @return the subType
   */
  public PointerType getSubType()
  {
    return subType;
  }

}

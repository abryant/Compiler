package compiler.language.conceptual.type;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class WildcardTypeParameter extends TypeParameter
{

  private PointerType[] superTypes;
  private PointerType[] subTypes;

  /**
   * Creates a new WildcardTypeParameter with the specified super and sub types
   * @param superTypes - the supertypes of the type parameter
   * @param subTypes - the subtypes of the type parameter
   */
  public WildcardTypeParameter(PointerType[] superTypes, PointerType[] subTypes)
  {
    this.superTypes = superTypes;
    this.subTypes = subTypes;
  }

  /**
   * @return the superTypes
   */
  public PointerType[] getSuperTypes()
  {
    return superTypes;
  }
  /**
   * @return the subTypes
   */
  public PointerType[] getSubTypes()
  {
    return subTypes;
  }

}

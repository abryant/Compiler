package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InterfacePointerType extends PointerType
{

  private ConceptualInterface interfaceType;
  private TypeParameter[] typeParameters;

  /**
   * Creates a new InterfacePointerType with the specified conceptual interface, type parameters and immutability
   * @param interfaceType - the interface type
   * @param typeParameters - the type parameters
   * @param immutable - true if this PointerType should be immutable, false otherwise
   */
  public InterfacePointerType(ConceptualInterface interfaceType, TypeParameter[] typeParameters, boolean immutable)
  {
    super(immutable);
    this.interfaceType = interfaceType;
    this.typeParameters = typeParameters;
  }

  /**
   * @return the interfaceType
   */
  public ConceptualInterface getInterfaceType()
  {
    return interfaceType;
  }
  /**
   * @return the typeParameters
   */
  public TypeParameter[] getTypeParameters()
  {
    return typeParameters;
  }

}

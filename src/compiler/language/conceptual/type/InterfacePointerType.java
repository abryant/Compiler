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
  private TypeArgument[] typeArguments;

  /**
   * Creates a new InterfacePointerType with the specified conceptual interface, type arguments and immutability
   * @param interfaceType - the interface type
   * @param typeArguments - the type arguments
   * @param immutable - true if this PointerType should be immutable, false otherwise
   */
  public InterfacePointerType(ConceptualInterface interfaceType, TypeArgument[] typeArguments, boolean immutable)
  {
    super(immutable);
    this.interfaceType = interfaceType;
    this.typeArguments = typeArguments;
  }

  /**
   * @return the interfaceType
   */
  public ConceptualInterface getInterfaceType()
  {
    return interfaceType;
  }
  /**
   * @return the typeArguments
   */
  public TypeArgument[] getTypeArguments()
  {
    return typeArguments;
  }

}

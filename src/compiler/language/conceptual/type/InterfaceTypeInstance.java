package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InterfaceTypeInstance extends TypeInstance
{

  private ConceptualInterface interfaceType;
  private TypeParameter[] typeParameters;

  /**
   * Creates a new InterfaceTypeInstance with the specified conceptual interface and type parameters
   * @param interfaceType - the interface type
   * @param typeParameters - the type parameters
   */
  public InterfaceTypeInstance(ConceptualInterface interfaceType, TypeParameter[] typeParameters)
  {
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

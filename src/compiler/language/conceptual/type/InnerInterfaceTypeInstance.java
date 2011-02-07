package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualInterface;


/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InnerInterfaceTypeInstance extends InterfaceTypeInstance
{

  private TypeInstance outerTypeInstance;

  /**
   * Creates a new InnerInterfaceTypeInstance with the specified conceptual interface, type parameters and outer type instance
   * @param interfaceType - the interface type
   * @param typeParameters - the type parameters
   * @param outerTypeInstance - the outer type instance
   */
  public InnerInterfaceTypeInstance(ConceptualInterface interfaceType, TypeParameter[] typeParameters, TypeInstance outerTypeInstance)
  {
    super(interfaceType, typeParameters);
    this.outerTypeInstance = outerTypeInstance;
  }

  /**
   * @return the outerTypeInstance
   */
  public TypeInstance getOuterTypeInstance()
  {
    return outerTypeInstance;
  }

}

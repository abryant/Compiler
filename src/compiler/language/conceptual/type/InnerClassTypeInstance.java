package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualClass;


/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InnerClassTypeInstance extends ClassTypeInstance
{

  private TypeInstance outerTypeInstance;

  /**
   * Creates a new InnerClassTypeInstance with the specified conceptual class, type parameters and outer class
   * @param classType - the class type
   * @param typeParameters - the type parameters
   * @param outerTypeInstance - the outer type instance
   */
  public InnerClassTypeInstance(ConceptualClass classType, TypeParameter[] typeParameters, TypeInstance outerTypeInstance)
  {
    super(classType, typeParameters);
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

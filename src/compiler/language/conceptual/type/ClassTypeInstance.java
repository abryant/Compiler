package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualClass;


/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClassTypeInstance extends TypeInstance
{

  private ConceptualClass classType;
  private TypeParameter[] typeParameters;

  /**
   * Creates a new ClassTypeInstance with the specified conceptual class and type parameters
   * @param classType - the class type
   * @param typeParameters - the type parameters
   */
  public ClassTypeInstance(ConceptualClass classType, TypeParameter[] typeParameters)
  {
    this.classType = classType;
    this.typeParameters = typeParameters;
  }

  /**
   * @return the classType
   */
  public ConceptualClass getClassType()
  {
    return classType;
  }
  /**
   * @return the typeParameters
   */
  public TypeParameter[] getTypeParameters()
  {
    return typeParameters;
  }
}

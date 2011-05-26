package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualClass;


/*
 * Created on 21 Oct 2010
 */

/**
 * Represents a class pointer type.
 * This stores multiple nested classes instead of a single class because the outer class is really a special case of the inner class.
 * @author Anthony Bryant
 */
public class ClassPointerType extends PointerType
{

  private ConceptualClass[] nestedClasses;
  private TypeParameter[][] nestedTypeParameters;

  /**
   * Creates a new ClassPointerType with the specified nested conceptual classes and type parameters, and the specified immutability
   * @param nestedClasses - the classes in the nested class chain that leads to this inner class
   * @param nestedTypeParameters - the type parameters associated with the nested classes
   * @param immutable - true if this PointerType should be immutable, false otherwise
   */
  public ClassPointerType(ConceptualClass[] nestedClasses, TypeParameter[][] nestedTypeParameters, boolean immutable)
  {
    super(immutable);
    this.nestedClasses = nestedClasses;
    this.nestedTypeParameters = nestedTypeParameters;
  }

  /**
   * @return the nestedClasses
   */
  public ConceptualClass[] getNestedClasses()
  {
    return nestedClasses;
  }

  /**
   * @return the nestedTypeParameters
   */
  public TypeParameter[][] getNestedTypeParameters()
  {
    return nestedTypeParameters;
  }

  /**
   * @return the classType of the most nested class
   */
  public ConceptualClass getClassType()
  {
    return nestedClasses[nestedClasses.length - 1];
  }
  /**
   * @return the typeParameters of the most nested class
   */
  public TypeParameter[] getTypeParameters()
  {
    return nestedTypeParameters[nestedTypeParameters.length - 1];
  }

}

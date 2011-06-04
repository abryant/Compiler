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
  private TypeArgument[][] nestedTypeArguments;

  /**
   * Creates a new ClassPointerType with the specified nested conceptual classes and type arguments, and the specified immutability
   * @param nestedClasses - the classes in the nested class chain that leads to this inner class
   * @param nestedTypeArguments - the type arguments associated with the nested classes
   * @param immutable - true if this PointerType should be immutable, false otherwise
   */
  public ClassPointerType(ConceptualClass[] nestedClasses, TypeArgument[][] nestedTypeArguments, boolean immutable)
  {
    super(immutable);
    this.nestedClasses = nestedClasses;
    this.nestedTypeArguments = nestedTypeArguments;
  }

  /**
   * @return the nestedClasses
   */
  public ConceptualClass[] getNestedClasses()
  {
    return nestedClasses;
  }

  /**
   * @return the nestedTypeArguments
   */
  public TypeArgument[][] getNestedTypeArguments()
  {
    return nestedTypeArguments;
  }

  /**
   * @return the classType of the most nested class
   */
  public ConceptualClass getClassType()
  {
    return nestedClasses[nestedClasses.length - 1];
  }
  /**
   * @return the typeArguments of the most nested class
   */
  public TypeArgument[] getTypeArguments()
  {
    return nestedTypeArguments[nestedTypeArguments.length - 1];
  }

}

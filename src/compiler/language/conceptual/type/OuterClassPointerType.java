package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualClass;


/*
 * Created on 21 Oct 2010
 */

/**
 * An outer class' PointerType, which is really a special case of an inner class' PointerType, so that inner classes can be used almost wherever outer classes can.
 * @author Anthony Bryant
 */
public class OuterClassPointerType extends ClassPointerType
{

  /**
   * Creates a new OuterClassPointerType with the specified conceptual class, type parameters and immutability
   * @param classType - the class type
   * @param typeParameters - the type parameters
   * @param immutable - true if this PointerType should be immutable, false otherwise
   */
  public OuterClassPointerType(ConceptualClass classType, TypeParameter[] typeParameters, boolean immutable)
  {
    super(new ConceptualClass[] {classType}, new TypeParameter[][] {typeParameters}, immutable);
  }
}

package compiler.language.conceptual.type;

/*
 * Created on 21 Oct 2010
 */

/**
 * The abstract base class for ClassInstance, InterfaceInstance and EnumInstance.
 * Subclasses of this store not only a reference to the Class/ConceptualInterface/ConceptualEnum, but also any generic type parameters associated with an instance of this type.
 * Another subclass is TypeArgumentInstance, which does not store generic type parameters but merely points to a TypeArgument.
 *
 * @author Anthony Bryant
 */
public abstract class TypeInstance
{
  // abstract base class, no members (yet)
}

package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualClass;

/*
 * Created on 28 Apr 2011
 */

/**
 * A pointer type which represents the automatic base class, which is the default base class when no other base class is specified.
 * @author Anthony Bryant
 */
public class AutomaticBaseClassPointerType extends ClassPointerType
{
  /**
   * Creates a new AutomaticBaseClassPointerType to represent the default base type
   */
  public AutomaticBaseClassPointerType()
  {
    super(new ConceptualClass[0], new TypeParameter[0][], false);
  }
}

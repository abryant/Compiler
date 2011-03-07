package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;

/*
 * Created on 19 Feb 2011
 */

/**
 * @author Anthony Bryant
 */
public final class InnerInterface extends ConceptualInterface
{

  private TypeDefinition enclosingTypeDefinition;

  /**
   * Creates a new InnerInterface with the specified enclosing type definition and parameters.
   * @param enclosingTypeDefinition - the enclosing type definition
   * @param accessSpecifier
   * @param isImmutable
   * @param sinceSpecifier
   * @param name
   */
  public InnerInterface(TypeDefinition enclosingTypeDefinition, AccessSpecifier accessSpecifier, boolean isImmutable, SinceSpecifier sinceSpecifier, String name)
  {
    super(accessSpecifier, isImmutable, sinceSpecifier, name);
    this.enclosingTypeDefinition = enclosingTypeDefinition;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.INNER_INTERFACE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Resolvable getParent()
  {
    return enclosingTypeDefinition;
  }

}

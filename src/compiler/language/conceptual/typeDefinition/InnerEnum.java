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
public final class InnerEnum extends ConceptualEnum
{

  private TypeDefinition enclosingTypeDefinition;

  /**
   * Creates a new InnerEnum with the specified enclosing type definition and other parameters
   * @param enclosingTypeDefinition - the enclosing type definition
   * @param accessSpecifier
   * @param sinceSpecifier
   * @param name
   */
  public InnerEnum(TypeDefinition enclosingTypeDefinition, AccessSpecifier accessSpecifier, SinceSpecifier sinceSpecifier, String name)
  {
    super(accessSpecifier, sinceSpecifier, name);
    this.enclosingTypeDefinition = enclosingTypeDefinition;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.INNER_ENUM;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resolvable getParent()
  {
    return enclosingTypeDefinition;
  }

}

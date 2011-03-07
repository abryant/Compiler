package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.topLevel.ConceptualFile;

/*
 * Created on 19 Feb 2011
 */

/**
 * @author Anthony Bryant
 */
public final class OuterEnum extends ConceptualEnum
{

  private ConceptualFile enclosingFile;

  /**
   * Creates a new OuterEnum with the specified enclosing file and other parameters
   * @param enclosingFile - the enclosing file
   * @param accessSpecifier
   * @param sinceSpecifier
   * @param name
   */
  public OuterEnum(ConceptualFile enclosingFile, AccessSpecifier accessSpecifier, SinceSpecifier sinceSpecifier, String name)
  {
    super(accessSpecifier, sinceSpecifier, name);
    this.enclosingFile = enclosingFile;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.OUTER_ENUM;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Resolvable getParent()
  {
    return enclosingFile;
  }

}

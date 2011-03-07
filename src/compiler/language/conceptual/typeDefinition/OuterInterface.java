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
public final class OuterInterface extends ConceptualInterface
{

  private ConceptualFile enclosingFile;

  /**
   * Creates a new OuterInterface with the specified enclosing file and other parameters
   * @param enclosingFile - the enclosing file
   * @param accessSpecifier
   * @param isImmutable
   * @param sinceSpecifier
   * @param name
   */
  public OuterInterface(ConceptualFile enclosingFile, AccessSpecifier accessSpecifier, boolean isImmutable, SinceSpecifier sinceSpecifier, String name)
  {
    super(accessSpecifier, isImmutable, sinceSpecifier, name);
    this.enclosingFile = enclosingFile;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.OUTER_INTERFACE;
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

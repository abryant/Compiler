package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.ScopedResult;
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
   * @see compiler.language.conceptual.typeDefinition.TypeDefinition#resolveEnclosing(compiler.language.conceptual.QName)
   */
  @Override
  protected ScopedResult resolveEnclosing(QName qname) throws NameConflictException
  {
    return enclosingTypeDefinition.resolve(qname, true);
  }



}

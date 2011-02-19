package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.ScopedResult;
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
   * @see compiler.language.conceptual.typeDefinition.TypeDefinition#resolveEnclosing(compiler.language.conceptual.QName)
   */
  @Override
  protected ScopedResult resolveEnclosing(QName qname) throws NameConflictException
  {
    return enclosingFile.resolve(qname, true);
  }

}

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
public final class OuterClass extends ConceptualClass
{

  private ConceptualFile enclosingFile;

  /**
   * Creates a new OuterClass with the specified enclosing file and other parameters.
   * @param enclosingFile - the file which contains this OuterClass
   * @param accessSpecifier
   * @param isAbstract
   * @param isSealed
   * @param isImmutable
   * @param sinceSpecifier
   * @param name
   */
  public OuterClass(ConceptualFile enclosingFile, AccessSpecifier accessSpecifier, boolean isAbstract, boolean isSealed, boolean isImmutable, SinceSpecifier sinceSpecifier, String name)
  {
    super(accessSpecifier, isAbstract, isSealed, isImmutable, sinceSpecifier, name);
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

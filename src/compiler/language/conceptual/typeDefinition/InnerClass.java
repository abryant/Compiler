package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public final class InnerClass extends ConceptualClass
{

  private TypeDefinition enclosingTypeDefinition;
  private boolean isStatic; // true if objects of this type do not store a reference to the outer class

  /**
   * Creates a new InnerClass with the specified properties.
   * @param enclosingTypeDefinition - the enclosing type definition
   * @param accessSpecifier
   * @param isAbstract
   * @param isFinal
   * @param isImmutable
   * @param sinceSpecifier
   * @param name
   * @param isStatic
   */
  public InnerClass(TypeDefinition enclosingTypeDefinition, boolean isStatic,
                    AccessSpecifier accessSpecifier, boolean isAbstract, boolean isFinal,
                    boolean isImmutable, SinceSpecifier sinceSpecifier, String name)
  {
    super(accessSpecifier, isAbstract, isFinal, isImmutable, sinceSpecifier, name);
    this.enclosingTypeDefinition = enclosingTypeDefinition;
    this.isStatic = isStatic;
  }

  /**
   * @return the isStatic
   */
  public boolean isStatic()
  {
    return isStatic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.INNER_CLASS;
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

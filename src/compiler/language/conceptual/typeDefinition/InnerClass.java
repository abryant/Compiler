package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InnerClass extends ConceptualClass
{

  private boolean isStatic; // true if objects of this type do not store a reference to the outer class

  /**
   * Creates a new InnerClass with the specified properties.
   * @param accessSpecifier
   * @param isAbstract
   * @param isFinal
   * @param isImmutable
   * @param sinceSpecifier
   * @param name
   * @param isStatic
   */
  public InnerClass(AccessSpecifier accessSpecifier, boolean isAbstract, boolean isFinal, boolean isImmutable,
                    SinceSpecifier sinceSpecifier, String name, boolean isStatic)
  {
    super(accessSpecifier, isAbstract, isFinal, isImmutable, sinceSpecifier, name);
    this.isStatic = isStatic;
  }

  /**
   * @return the isStatic
   */
  public boolean isStatic()
  {
    return isStatic;
  }
}

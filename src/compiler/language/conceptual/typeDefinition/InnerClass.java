package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.AccessSpecifier;
import compiler.language.conceptual.SinceSpecifier;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.TypeArgument;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InnerClass extends ConceptualClass
{

  /**
   * Creates a new InnerClass with the specified properties.
   * @param accessSpecifier
   * @param isAbstract
   * @param isFinal
   * @param isImmutable
   * @param sinceSpecifier
   * @param name
   * @param typeArguments
   * @param baseClass
   * @param interfaces
   * @param isStatic
   * @param outerClass
   */
  public InnerClass(AccessSpecifier accessSpecifier, boolean isAbstract, boolean isFinal, boolean isImmutable,
                    SinceSpecifier sinceSpecifier, String name, TypeArgument[] typeArguments, PointerType baseClass,
                    PointerType[] interfaces, boolean isStatic, ConceptualClass outerClass)
  {
    super(accessSpecifier, isAbstract, isFinal, isImmutable, sinceSpecifier, name, typeArguments, baseClass, interfaces);
    this.isStatic = isStatic;
    this.outerClass = outerClass;
  }

  private boolean isStatic; // true if objects of this type do not store a reference to the outer class
  private ConceptualClass outerClass;

  /**
   * @return the isStatic
   */
  public boolean isStatic()
  {
    return isStatic;
  }
  /**
   * @param isStatic - the isStatic to set
   */
  public void setStatic(boolean isStatic)
  {
    this.isStatic = isStatic;
  }
  /**
   * @return the outerClass
   */
  public ConceptualClass getOuterClass()
  {
    return outerClass;
  }
  /**
   * @param outerClass - the outerClass to set
   */
  public void setOuterClass(ConceptualClass outerClass)
  {
    this.outerClass = outerClass;
  }

}

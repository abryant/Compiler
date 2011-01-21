package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.Scope;
import compiler.language.conceptual.member.Constructor;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.member.StaticInitializer;
import compiler.language.conceptual.member.VariableInitializers;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.type.PointerType;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualEnum
{
  private AccessSpecifier accessSpecifier;
  private SinceSpecifier sinceSpecifier;
  private String name;
  private PointerType baseClass;
  private PointerType[] interfaces;

  private EnumConstant[] constants;

  // members
  private StaticInitializer staticInitializer;
  private MemberVariable[] staticVariables;
  private Constructor[] constructors;
  private Property[] properties;
  private MemberVariable[] memberVariables;
  private VariableInitializers variableInitializers;
  private Method[] methods;

  private InnerClass[] innerClasses;
  private ConceptualInterface[] innerInterfaces;
  private ConceptualEnum[] innerEnums;

  private Scope scope;

  /**
   * Creates a new Conceptual enum with the specified properties.
   * The members of the new enum and other data must be set later on.
   * @param accessSpecifier
   * @param sinceSpecifier
   * @param name
   */
  public ConceptualEnum(AccessSpecifier accessSpecifier, SinceSpecifier sinceSpecifier, String name)
  {
    this.accessSpecifier = accessSpecifier;
    this.sinceSpecifier = sinceSpecifier;
    this.name = name;
  }

  /**
   * Sets the headers for this conceptual enum
   * @param baseClass
   * @param interfaces
   */
  public void setHeaders(PointerType baseClass, PointerType[] interfaces)
  {
    this.baseClass = baseClass;
    this.interfaces = interfaces;
  }

  /**
   * Sets the enum constants for this conceptual enum
   * @param constants
   */
  public void setConstants(EnumConstant[] constants)
  {
    this.constants = constants;
  }

  /**
   * Sets the members of this conceptual enum.
   * @param staticInitializer
   * @param staticVariables
   * @param constructors
   * @param properties
   * @param memberVariables
   * @param variableInitializers
   * @param methods
   */
  public void setMembers(StaticInitializer staticInitializer, MemberVariable[] staticVariables,
                         Constructor[] constructors, Property[] properties, MemberVariable[] memberVariables,
                         VariableInitializers variableInitializers, Method[] methods)
  {
    this.staticInitializer = staticInitializer;
    this.staticVariables = staticVariables;
    this.constructors = constructors;
    this.properties = properties;
    this.memberVariables = memberVariables;
    this.variableInitializers = variableInitializers;
    this.methods = methods;
  }

  /**
   * Sets the inner type definitions of this conceptual enum.
   * @param innerClasses
   * @param innerInterfaces
   * @param innerEnums
   */
  public void setInnerTypeDefinitions(InnerClass[] innerClasses, ConceptualInterface[] innerInterfaces, ConceptualEnum[] innerEnums)
  {
    this.innerClasses = innerClasses;
    this.innerInterfaces = innerInterfaces;
    this.innerEnums = innerEnums;
  }

  /**
   * @return the scope
   */
  public Scope getScope()
  {
    return scope;
  }

  /**
   * @param scope - the scope to set
   */
  public void setScope(Scope scope)
  {
    this.scope = scope;
  }

  /**
   * @return the accessSpecifier
   */
  public AccessSpecifier getAccessSpecifier()
  {
    return accessSpecifier;
  }
  /**
   * @return the sinceSpecifier
   */
  public SinceSpecifier getSinceSpecifier()
  {
    return sinceSpecifier;
  }
  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }
  /**
   * @return the baseClass
   */
  public PointerType getBaseClass()
  {
    return baseClass;
  }
  /**
   * @return the interfaces
   */
  public PointerType[] getInterfaces()
  {
    return interfaces;
  }
  /**
   * @return the constants
   */
  public EnumConstant[] getConstants()
  {
    return constants;
  }
  /**
   * @return the staticInitializer
   */
  public StaticInitializer getStaticInitializer()
  {
    return staticInitializer;
  }
  /**
   * @return the staticVariables
   */
  public MemberVariable[] getStaticVariables()
  {
    return staticVariables;
  }
  /**
   * @return the constructors
   */
  public Constructor[] getConstructors()
  {
    return constructors;
  }
  /**
   * @return the properties
   */
  public Property[] getProperties()
  {
    return properties;
  }
  /**
   * @return the memberVariables
   */
  public MemberVariable[] getMemberVariables()
  {
    return memberVariables;
  }
  /**
   * @return the variableInitializers
   */
  public VariableInitializers getVariableInitializers()
  {
    return variableInitializers;
  }
  /**
   * @return the methods
   */
  public Method[] getMethods()
  {
    return methods;
  }
  /**
   * @return the innerClasses
   */
  public InnerClass[] getInnerClasses()
  {
    return innerClasses;
  }
  /**
   * @return the innerInterfaces
   */
  public ConceptualInterface[] getInnerInterfaces()
  {
    return innerInterfaces;
  }
  /**
   * @return the innerEnums
   */
  public ConceptualEnum[] getInnerEnums()
  {
    return innerEnums;
  }

}

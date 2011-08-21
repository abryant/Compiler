package compiler.language.conceptual.typeDefinition;

import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.member.StaticInitializer;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.type.InterfacePointerType;
import compiler.language.conceptual.type.TypeParameter;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public abstract class ConceptualInterface extends TypeDefinition
{

  private AccessSpecifier accessSpecifier;
  private boolean isImmutable;
  private SinceSpecifier sinceSpecifier;
  private String name;

  // headers
  private TypeParameter[] typeParameters;
  private InterfacePointerType[] superInterfaces;

  // members
  private StaticInitializer staticInitializer;
  private MemberVariable[] staticVariables;
  private Property[] properties;
  private Method[] methods;

  private InnerClass[] innerClasses;
  private ConceptualInterface[] innerInterfaces;
  private ConceptualEnum[] innerEnums;

  /**
   * Creates a new Conceptual interface with the specified properties
   * The members and other data must be set later.
   * @param accessSpecifier
   * @param isImmutable
   * @param sinceSpecifier
   * @param name
   */
  public ConceptualInterface(AccessSpecifier accessSpecifier, boolean isImmutable, SinceSpecifier sinceSpecifier, String name)
  {
    this.accessSpecifier = accessSpecifier;
    this.isImmutable = isImmutable;
    this.sinceSpecifier = sinceSpecifier;
    this.name = name;
  }

  /**
   * Sets the parent interfaces of this interface
   * @param superInterfaces - the parent interfaces
   */
  public void setSuperInterfaces(InterfacePointerType[] superInterfaces)
  {
    this.superInterfaces = superInterfaces;
  }

  /**
   * Sets the type parameters of this conceptual interface
   * @param typeParameters - the type parameters to set
   */
  public void setTypeParameters(TypeParameter[] typeParameters)
  {
    this.typeParameters = typeParameters;
  }

  /**
   * Sets the members of this conceptual interface
   * @param staticInitializer
   * @param staticVariables
   * @param properties
   * @param methods
   * @param innerClasses
   * @param innerInterfaces
   * @param innerEnums
   */
  public void setMembers(StaticInitializer staticInitializer, MemberVariable[] staticVariables,
                         Property[] properties, Method[] methods,
                         InnerClass[] innerClasses, ConceptualInterface[] innerInterfaces, ConceptualEnum[] innerEnums)
  {
    this.staticInitializer = staticInitializer;
    this.staticVariables = staticVariables;
    this.properties = properties;
    this.methods = methods;
    this.innerClasses = innerClasses;
    this.innerInterfaces = innerInterfaces;
    this.innerEnums = innerEnums;
  }

  /**
   * @return the accessSpecifier
   */
  public AccessSpecifier getAccessSpecifier()
  {
    return accessSpecifier;
  }
  /**
   * @return the isImmutable
   */
  public boolean isImmutable()
  {
    return isImmutable;
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
   * @return the typeParameters
   */
  public TypeParameter[] getTypeParameters()
  {
    return typeParameters;
  }
  /**
   * @return the superInterfaces
   */
  public InterfacePointerType[] getSuperInterfaces()
  {
    return superInterfaces;
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
   * @return the properties
   */
  public Property[] getProperties()
  {
    return properties;
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


  /**
   * {@inheritDoc}
   */
  @Override
  public final Resolvable resolve(String name) throws ConceptualException
  {
    for (TypeParameter typeParameter : typeParameters)
    {
      if (typeParameter.getName().equals(name))
      {
        return typeParameter;
      }
    }
    for (MemberVariable staticVariable : staticVariables)
    {
      if (staticVariable.getName().equals(name))
      {
        return staticVariable;
      }
    }
    for (Property property : properties)
    {
      if (property.getName().equals(name) && property.isStatic())
      {
        return property;
      }
    }
    // TODO: how will methods work here? there can be multiple methods with one name (only static methods should be considered here)
    for (InnerClass innerClass : innerClasses)
    {
      if (innerClass.getName().equals(name))
      {
        return innerClass;
      }
    }
    for (ConceptualInterface innerInterface : innerInterfaces)
    {
      if (innerInterface.getName().equals(name))
      {
        return innerInterface;
      }
    }
    for (ConceptualEnum innerEnum : innerEnums)
    {
      if (innerEnum.getName().equals(name))
      {
        return innerEnum;
      }
    }
    return null;
  }

}

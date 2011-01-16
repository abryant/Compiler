package compiler.language.conceptual.typeDefinition;

import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.terminal.SinceSpecifierAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.AccessSpecifier;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.SinceSpecifier;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.member.StaticInitializer;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.TypeArgument;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualInterface
{

  private AccessSpecifier accessSpecifier;
  private boolean isImmutable;
  private SinceSpecifier sinceSpecifier;
  private String name;

  // headers
  private TypeArgument[] typeArguments;
  private PointerType[] superInterfaces;

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
   * Creates a new ConceptualInterface from the specified InterfaceDefinitionAST.
   * @param interfaceDefinition - the InterfaceDefinitionAST to base the new ConceptualInterface on
   * @return the ConceptualInterface created
   * @throws ConceptualException - if there is a problem converting the AST instance to a Conceptual instance
   */
  public static ConceptualInterface fromAST(InterfaceDefinitionAST interfaceDefinition) throws ConceptualException
  {
    AccessSpecifier access = AccessSpecifier.fromAST(interfaceDefinition.getAccess());
    ModifierAST[] modifiers = interfaceDefinition.getModifiers();
    boolean isImmutable = false;
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : modifiers)
    {
      switch (modifier.getType())
      {
      case IMMUTABLE:
        isImmutable = true;
        break;
      case SINCE_SPECIFIER:
        sinceSpecifier = SinceSpecifier.fromAST((SinceSpecifierAST) modifier);
        break;
      default:
        throw new ConceptualException("Illegal Modifier for an Interface Definition", modifier.getParseInfo());
      }
    }

    return new ConceptualInterface(access, isImmutable, sinceSpecifier, interfaceDefinition.getName().getName());
  }

  /**
   * Sets the headers of this conceptual interface, including type arguments and super-interfaces
   * @param typeArguments
   * @param superInterfaces
   */
  public void setHeaders(TypeArgument[] typeArguments, PointerType[] superInterfaces)
  {
    this.typeArguments = typeArguments;
    this.superInterfaces = superInterfaces;
  }

  /**
   * Sets the members of this conceptual interface.
   * @param staticInitializer
   * @param staticVariables
   * @param properties
   * @param methods
   */
  public void setMembers(StaticInitializer staticInitializer, MemberVariable[] staticVariables, Property[] properties, Method[] methods)
  {
    this.staticInitializer = staticInitializer;
    this.staticVariables = staticVariables;
    this.properties = properties;
    this.methods = methods;
  }

  /**
   * Sets the inner type definitions of this conceptual interface.
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
   * @return the typeArguments
   */
  public TypeArgument[] getTypeArguments()
  {
    return typeArguments;
  }
  /**
   * @return the superInterfaces
   */
  public PointerType[] getSuperInterfaces()
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

}

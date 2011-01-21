package compiler.language.translator.conceptual;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import compiler.language.ast.member.ConstructorAST;
import compiler.language.ast.member.FieldAST;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.member.MethodAST;
import compiler.language.ast.member.PropertyAST;
import compiler.language.ast.member.StaticInitializerAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.SinceSpecifierAST;
import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.ast.topLevel.PackageDeclarationAST;
import compiler.language.ast.topLevel.TypeDefinitionAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.ast.typeDefinition.EnumDefinitionAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.ConceptualProgram;
import compiler.language.conceptual.Scope;
import compiler.language.conceptual.ScopedMemberSet;
import compiler.language.conceptual.member.Constructor;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.member.StaticInitializer;
import compiler.language.conceptual.member.VariableInitializers;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.type.TypeArgument;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
import compiler.language.conceptual.typeDefinition.InnerClass;

/*
 * Created on 19 Jan 2011
 */

/**
 * Handles conversion of an AST into a basic conceptual view.
 * This class does not take care of scoping, or building a scope. It just translates the raw data that it can from the AST into the Conceptual hierarchy.
 * @author Anthony Bryant
 */
public class ASTConverter
{

  private ConceptualProgram program;

  /**
   * Creates a new ASTConverter that will add all converted AST nodes to the specified ConceptualProgram
   * @param program - the ConceptualProgram to add all converted AST nodes to
   */
  public ASTConverter(ConceptualProgram program)
  {
    this.program = program;
  }

  /**
   * Converts the specified CompilationUnitAST and adds the converted classes etc. to the ConceptualProgram that this object was constructed with.
   * @param compilationUnit - the CompilationUnitAST to convert
   * @throws ConceptualException - if there is a problem converting the AST to the conceptual view
   * @throws ScopeException - if there is a naming conflict in the AST
   */
  public void convert(CompilationUnitAST compilationUnit) throws ConceptualException, ScopeException
  {
    PackageDeclarationAST packageDeclaration = compilationUnit.getPackageDeclaration();
    Scope enclosingScope = null;
    if (packageDeclaration == null)
    {
      enclosingScope = program.getRootScope();
    }
    else
    {
      QNameAST packageName = packageDeclaration.getPackageName();
      enclosingScope = ScopeFactory.getPackageScope(program.getRootScope(), packageName.getNameStrings());
    }

    Scope fileScope = ScopeFactory.createFileScope(enclosingScope);

    for (TypeDefinitionAST typeDefinition : compilationUnit.getTypes())
    {
      Scope typeScope = null;
      if (typeDefinition instanceof ClassDefinitionAST)
      {
        ConceptualClass conceptualClass = convert((ClassDefinitionAST) typeDefinition, fileScope);
        typeScope = conceptualClass.getScope();
      }
      else if (typeDefinition instanceof InterfaceDefinitionAST)
      {
        ConceptualInterface conceptualInterface = convert((InterfaceDefinitionAST) typeDefinition, fileScope);
        typeScope = conceptualInterface.getScope();
      }
      else if (typeDefinition instanceof EnumDefinitionAST)
      {
        ConceptualEnum conceptualEnum = convert((EnumDefinitionAST) typeDefinition, fileScope);
        typeScope = conceptualEnum.getScope();
      }
      else
      {
        throw new UnsupportedOperationException("Cannot translate a type definition that does not represent a class, interface or enum.");
      }

      // TODO: don't throw the exception if there is a conflict, just print it out and fail when we've detected all of the errors (probably not with an exception in fact)
      enclosingScope.addChild(typeDefinition.getName().getName(), typeScope);
    }
  }

  /**
   * Converts the specified ClassDefinitionAST into a ConceptualClass.
   * @param classDefinition - the ClassDefinitionAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the ConceptualClass created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private ConceptualClass convert(ClassDefinitionAST classDefinition, Scope enclosingScope) throws ConceptualException
  {
    // convert AccessSpecifier and Modifiers
    AccessSpecifier access = AccessSpecifier.fromAST(classDefinition.getAccess());
    ModifierAST[] modifiers = classDefinition.getModifiers();
    boolean isAbstract = false;
    boolean isFinal = false;
    boolean isImmutable = false;
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : modifiers)
    {
      switch (modifier.getType())
      {
      case ABSTRACT:
        isAbstract = true;
        break;
      case FINAL:
        isFinal = true;
        break;
      case IMMUTABLE:
        isImmutable = true;
        break;
      case SINCE_SPECIFIER:
        sinceSpecifier = SinceSpecifier.fromAST((SinceSpecifierAST) modifier);
        break;
      default:
        throw new ConceptualException("Illegal Modifier for a Class Definition", modifier.getParseInfo());
      }
    }

    // Create the ConceptualClass and the scope for it
    ConceptualClass conceptualClass = new ConceptualClass(access, isAbstract, isFinal, isImmutable, sinceSpecifier, classDefinition.getName().getName());
    Scope scope = ScopeFactory.createClassDefinitionScope(conceptualClass, enclosingScope);
    conceptualClass.setScope(scope);

    // convert the type arguments
    TypeArgumentAST[] typeArgumentASTs = classDefinition.getTypeArguments();
    TypeArgument[] typeArguments = new TypeArgument[typeArgumentASTs.length];
    for (int i = 0; i < typeArgumentASTs.length; i++)
    {
      typeArguments[i] = convert(typeArgumentASTs[i], scope);
      scope.addChild(typeArgumentASTs[i].getName(), typeArguments[i].getScope());
    }


    // convert each of the members in turn, switching on the member type
    // each member is added to a list of the members of its type, and also to membersByName, which stores a ScopedMemberSet for each different name
    Map<String, ScopedMemberSet> membersByName = new HashMap<String, ScopedMemberSet>();
    List<MemberVariable>      staticVariables = new LinkedList<MemberVariable>();
    List<MemberVariable>      variables       = new LinkedList<MemberVariable>();
    List<Property>            properties      = new LinkedList<Property>();
    List<Constructor>         constructors    = new LinkedList<Constructor>();
    List<Method>              methods         = new LinkedList<Method>();
    List<InnerClass>          innerClasses    = new LinkedList<InnerClass>();
    List<ConceptualInterface> innerInterfaces = new LinkedList<ConceptualInterface>();
    List<ConceptualEnum>      innerEnums      = new LinkedList<ConceptualEnum>();

    MemberAST[] memberASTs = classDefinition.getMembers();
    for (MemberAST memberAST : memberASTs)
    {
      if (memberAST instanceof FieldAST)
      {
        MemberVariable[] memberVariables = convert((FieldAST) memberAST, scope);
        for (MemberVariable variable : memberVariables)
        {
          ScopedMemberSet memberSet = new ScopedMemberSet();
          if (variable.isStatic())
          {
            memberSet.addStaticVariable(variable);
            staticVariables.add(variable);
          }
          else
          {
            memberSet.addVariable(variable);
            variables.add(variable);
          }
          combineMembers(membersByName, variable.getName(), memberSet);
        }
      }
      else if (memberAST instanceof PropertyAST)
      {
        Property property = convert((PropertyAST) memberAST, scope);
        ScopedMemberSet memberSet = new ScopedMemberSet();
        memberSet.addProperty(property);
        combineMembers(membersByName, property.getName(), memberSet);
        properties.add(property);
      }
      else if (memberAST instanceof StaticInitializerAST)
      {
        // do nothing, as the static initializer contains no information that we need to convert right now
        // the contained statements will be converted when the scope hierarchy has been built
      }
      else if (memberAST instanceof ConstructorAST)
      {
        Constructor constructor = convert((ConstructorAST) memberAST, scope);
        constructors.add(constructor);
      }
      else if (memberAST instanceof MethodAST)
      {
        Method method = convert((MethodAST) memberAST, scope);
        ScopedMemberSet memberSet = new ScopedMemberSet();
        memberSet.addMethod(method);
        combineMembers(membersByName, method.getName(), memberSet);
        methods.add(method);
      }
      else if (memberAST instanceof ClassDefinitionAST)
      {
        InnerClass innerClass = convertInnerClass((ClassDefinitionAST) memberAST, scope);
        ScopedMemberSet memberSet = new ScopedMemberSet();
        memberSet.addInnerClass(innerClass);
        combineMembers(membersByName, innerClass.getName(), memberSet);
        innerClasses.add(innerClass);
      }
      else if (memberAST instanceof InterfaceDefinitionAST)
      {
        ConceptualInterface innerInterface = convertInnerInterface((InterfaceDefinitionAST) memberAST, scope);
        ScopedMemberSet memberSet = new ScopedMemberSet();
        memberSet.addInnerInterface(innerInterface);
        innerInterfaces.add(innerInterface);
      }
      else if (memberAST instanceof EnumDefinitionAST)
      {
        ConceptualEnum innerEnum = convertInnerEnum((EnumDefinitionAST) memberAST, scope);
        ScopedMemberSet memberSet = new ScopedMemberSet();
        memberSet.addInnerEnum(innerEnum);
        innerEnums.add(innerEnum);
      }
      else
      {
        throw new UnsupportedOperationException("Cannot translate a class member that is not a field, property, static initializer, constructor, method, or type definition.");
      }
    }

    for (Entry<String, ScopedMemberSet> entry : membersByName.entrySet())
    {
      Scope memberSetScope = ScopeFactory.createMemberSetScope(entry.getValue(), scope);
      scope.addChild(entry.getKey(), memberSetScope);
    }

    conceptualClass.setMembers(new StaticInitializer(), new VariableInitializers(),
                               staticVariables.toArray(new MemberVariable[0]),
                               variables.toArray(new MemberVariable[0]),
                               properties.toArray(new Property[0]),
                               constructors.toArray(new Constructor[0]),
                               methods.toArray(new Method[0]),
                               innerClasses.toArray(new InnerClass[0]),
                               innerInterfaces.toArray(new ConceptualInterface[0]),
                               innerEnums.toArray(new ConceptualEnum[0]));

    return conceptualClass;
  }

  /**
   * Converts the specified InterfaceDefinitionAST into a ConceptualInterface.
   * @param interfaceDefinition - the InterfaceDefinitionAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the ConceptualInterface created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private ConceptualInterface convert(InterfaceDefinitionAST interfaceDefinition, Scope enclosingScope) throws ConceptualException
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
        sinceSpecifier = SinceSpecifier.fromAST((SinceSpecifierAST) modifier); // TODO: move to a convert() method
        break;
      default:
        throw new ConceptualException("Illegal Modifier for an Interface Definition", modifier.getParseInfo());
      }
    }

    ConceptualInterface conceptualInterface = new ConceptualInterface(access, isImmutable, sinceSpecifier, interfaceDefinition.getName().getName());
    Scope scope = ScopeFactory.createInterfaceDefinitionScope(conceptualInterface, enclosingScope);
    conceptualInterface.setScope(scope);

    // TODO: add all other information that doesn't need looking up in the scope table
    //       also, add the scopes of the new conceptual objects to this object's scope

    return conceptualInterface;
  }

  /**
   * Converts the specified EnumDefinitionAST into a ConceptualEnum.
   * @param enumDefinition - the EnumDefinitionAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the ConceptualEnum created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private ConceptualEnum convert(EnumDefinitionAST enumDefinition, Scope enclosingScope) throws ConceptualException
  {
    AccessSpecifier accessSpecifier = AccessSpecifier.fromAST(enumDefinition.getAccessSpecifier());
    ModifierAST[] modifiers = enumDefinition.getModifiers();
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : modifiers)
    {
      switch (modifier.getType())
      {
      case SINCE_SPECIFIER:
        sinceSpecifier = SinceSpecifier.fromAST((SinceSpecifierAST) modifier); // TODO: move to a convert() method
        break;
      default:
        throw new ConceptualException("Illegal Modifier for an Enum Definition", modifier.getParseInfo());
      }
    }

    ConceptualEnum conceptualEnum = new ConceptualEnum(accessSpecifier, sinceSpecifier, enumDefinition.getName().getName());
    Scope scope = ScopeFactory.createEnumDefinitionScope(conceptualEnum, enclosingScope);
    conceptualEnum.setScope(scope);

    // TODO: add all other information that doesn't need looking up in the scope table
    //       also, add the scopes of the new conceptual objects to this object's scope

    return conceptualEnum;
  }

  /**
   * Combines the member set with the specified name in the membersByName map with the specified memberSet.
   * If no set with the specified name already exists, the specified set is added to the map.
   * @param membersByName - the mapping from name to a list of members with the specified name
   * @param name - the name of the members to add
   * @param memberSet - the set of members to add
   */
  private static void combineMembers(Map<String, ScopedMemberSet> membersByName, String name, ScopedMemberSet memberSet)
  {
    ScopedMemberSet existingSet = membersByName.get(name);
    if (existingSet == null)
    {
      membersByName.put(name, memberSet);
      return;
    }
    existingSet.addAll(memberSet);
  }

  private Constructor convert(ConstructorAST constructorAST, Scope enclosingScope)
  {

  }

}

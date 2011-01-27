package compiler.language.translator.conceptual;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compiler.language.ast.member.AccessSpecifierAST;
import compiler.language.ast.member.ConstructorAST;
import compiler.language.ast.member.FieldAST;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.member.MethodAST;
import compiler.language.ast.member.PropertyAST;
import compiler.language.ast.member.StaticInitializerAST;
import compiler.language.ast.misc.DeclarationAssigneeAST;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.NativeSpecifierAST;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.IntegerLiteralAST;
import compiler.language.ast.terminal.SinceSpecifierAST;
import compiler.language.ast.terminal.VersionNumberAST;
import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.ast.topLevel.PackageDeclarationAST;
import compiler.language.ast.topLevel.TypeDefinitionAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.ast.typeDefinition.EnumConstantAST;
import compiler.language.ast.typeDefinition.EnumDefinitionAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.ConceptualProgram;
import compiler.language.conceptual.Scope;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.member.Constructor;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.member.StaticInitializer;
import compiler.language.conceptual.member.VariableInitializers;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.NativeSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.type.TypeArgument;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
import compiler.language.conceptual.typeDefinition.EnumConstant;
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
  private Scope rootScope;

  // a map from AST node to Scope
  private Map<Object, Scope> scopes = new HashMap<Object, Scope>();

  // TODO: handle duplicate modifiers properly (instead of just disregarding them)

  /**
   * Creates a new ASTConverter that will add all converted AST nodes to the specified ConceptualProgram
   * @param program - the ConceptualProgram to add all converted AST nodes to
   * @param rootScope - the root scope of the program
   */
  public ASTConverter(ConceptualProgram program, Scope rootScope)
  {
    this.program = program;
    this.rootScope = rootScope;
    scopes.put(program, rootScope);
  }

  public Map<Object, Scope> getScopes()
  {
    return scopes;
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
      enclosingScope = rootScope;
    }
    else
    {
      QNameAST packageName = packageDeclaration.getPackageName();
      enclosingScope = ScopeFactory.getPackageScope(rootScope, packageName.getNameStrings());
    }

    Scope fileScope = ScopeFactory.createFileScope(enclosingScope);
    scopes.put(compilationUnit, fileScope);

    for (TypeDefinitionAST typeDefinition : compilationUnit.getTypes())
    {
      Scope typeScope = null;
      if (typeDefinition instanceof ClassDefinitionAST)
      {
        typeScope = convert((ClassDefinitionAST) typeDefinition, fileScope);
        ConceptualClass conceptualClass = (ConceptualClass) typeScope.getValue();
        program.addClass(conceptualClass);
      }
      else if (typeDefinition instanceof InterfaceDefinitionAST)
      {
        typeScope = convert((InterfaceDefinitionAST) typeDefinition, fileScope);
        ConceptualInterface conceptualInterface = (ConceptualInterface) typeScope.getValue();
        program.addInterface(conceptualInterface);
      }
      else if (typeDefinition instanceof EnumDefinitionAST)
      {
        typeScope = convert((EnumDefinitionAST) typeDefinition, fileScope);
        ConceptualEnum conceptualEnum = (ConceptualEnum) typeScope.getValue();
        program.addEnum(conceptualEnum);
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
   * @return the Scope of the ConceptualClass created, which has the ConceptualClass as its value
   * @throws ConceptualException - if there is a problem with the conversion
   * @throws ScopeException - if there is a scope collision
   */
  private Scope convert(ClassDefinitionAST classDefinition, Scope enclosingScope) throws ConceptualException, ScopeException
  {
    // convert AccessSpecifier and Modifiers
    AccessSpecifier access = convert(classDefinition.getAccess(), AccessSpecifier.PUBLIC);
    ModifierAST[] modifiers = classDefinition.getModifiers();
    boolean isAbstract = false;
    boolean isSealed = false;
    boolean isImmutable = false;
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : modifiers)
    {
      switch (modifier.getType())
      {
      case ABSTRACT:
        isAbstract = true;
        break;
      case SEALED:
        isSealed = true;
        break;
      case IMMUTABLE:
        isImmutable = true;
        break;
      case SINCE_SPECIFIER:
        sinceSpecifier = convert((SinceSpecifierAST) modifier);
        break;
      default:
        throw new ConceptualException("Illegal Modifier for a Class Definition", modifier.getParseInfo());
      }
    }

    // Create the ConceptualClass and the scope for it
    ConceptualClass conceptualClass = new ConceptualClass(access, isAbstract, isSealed, isImmutable, sinceSpecifier, classDefinition.getName().getName());
    Scope scope = ScopeFactory.createClassDefinitionScope(conceptualClass, enclosingScope);
    scopes.put(classDefinition, scope);

    addClassData(scope, classDefinition);

    return scope;
  }

  /**
   * Adds all of the data to a conceptual class that is not specified in the constructor.
   * @param scope - the scope of the class definition, which contains a ConceptualClass as its value
   * @param classDefinition - the ClassDefinitionAST to convert the type arguments and members of
   * @throws ConceptualException - if there is a problem with the conversion
   * @throws ScopeException - if there is a scope collision
   */
  private void addClassData(Scope scope, ClassDefinitionAST classDefinition) throws ConceptualException, ScopeException
  {
    ConceptualClass conceptualClass = (ConceptualClass) scope.getValue();

    // convert the type arguments
    TypeArgumentAST[] typeArgumentASTs = classDefinition.getTypeArguments();
    TypeArgument[] typeArguments = new TypeArgument[typeArgumentASTs.length];
    for (int i = 0; i < typeArgumentASTs.length; i++)
    {
      Scope typeArgumentScope = convert(typeArgumentASTs[i], scope);
      typeArguments[i] = (TypeArgument) typeArgumentScope.getValue();
      scope.addChild(typeArgumentASTs[i].getName().getName(), typeArgumentScope);
    }
    conceptualClass.setTypeArguments(typeArguments);


    // convert each of the members in turn, switching on the member type. each member is added to a list of the members of its type
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
        Scope[] variableScopes = convert((FieldAST) memberAST, scope);
        for (Scope variableScope : variableScopes)
        {
          MemberVariable variable = (MemberVariable) variableScope.getValue();
          variables.add(variable);
          scope.addChild(variable.getName(), variableScope);
        }
      }
      else if (memberAST instanceof PropertyAST)
      {
        Scope propertyScope = convert((PropertyAST) memberAST, scope);
        Property property = (Property) propertyScope.getValue();
        properties.add(property);
        scope.addChild(property.getName(), propertyScope);
      }
      else if (memberAST instanceof StaticInitializerAST)
      {
        // do nothing, as the static initializer contains no information that we need to convert right now
        // the contained statements will be converted when the scope hierarchy has been built
      }
      else if (memberAST instanceof ConstructorAST)
      {
        Scope constructorScope = convert((ConstructorAST) memberAST, scope);
        Constructor constructor = (Constructor) constructorScope.getValue();
        constructors.add(constructor);
        // no need to add to the scope here, you cannot reference a constructor directly, just via the class name
      }
      else if (memberAST instanceof MethodAST)
      {
        Scope methodScope = convert((MethodAST) memberAST, scope);
        Method method = (Method) methodScope.getValue();
        methods.add(method);
        // combine the scopes for existing methods
        Scope existingScope = scope.getChild(method.getName());
        if (existingScope != null && existingScope.getType() == ScopeType.METHOD)
        {
          @SuppressWarnings("unchecked")
          Set<Method> existingMethods = (Set<Method>) existingScope.getValue();
          existingMethods.add(method);
        }
        else
        {
          // if existingScope is not null here then addChild() will fail, as in the addChild() calls for other members
          scope.addChild(method.getName(), methodScope);
        }
      }
      else if (memberAST instanceof ClassDefinitionAST)
      {
        Scope innerClassScope = convertInnerClass((ClassDefinitionAST) memberAST, scope);
        InnerClass innerClass = (InnerClass) innerClassScope.getValue();
        innerClasses.add(innerClass);
        scope.addChild(innerClass.getName(), innerClassScope);
      }
      else if (memberAST instanceof InterfaceDefinitionAST)
      {
        Scope innerInterfaceScope = convertInnerInterface((InterfaceDefinitionAST) memberAST, scope);
        ConceptualInterface innerInterface = (ConceptualInterface) innerInterfaceScope.getValue();
        innerInterfaces.add(innerInterface);
        scope.addChild(innerInterface.getName(), innerInterfaceScope);
      }
      else if (memberAST instanceof EnumDefinitionAST)
      {
        Scope innerEnumScope = convertInnerEnum((EnumDefinitionAST) memberAST, scope);
        ConceptualEnum innerEnum = (ConceptualEnum) innerEnumScope.getValue();
        innerEnums.add(innerEnum);
        scope.addChild(innerEnum.getName(), innerEnumScope);
      }
      else
      {
        throw new UnsupportedOperationException("Cannot translate a class member that is not a field, property, static initializer, constructor, method, or type definition.");
      }
    }

    conceptualClass.setMembers(new StaticInitializer(), new VariableInitializers(),
                               variables.toArray(new MemberVariable[0]),
                               properties.toArray(new Property[0]),
                               constructors.toArray(new Constructor[0]),
                               methods.toArray(new Method[0]),
                               innerClasses.toArray(new InnerClass[0]),
                               innerInterfaces.toArray(new ConceptualInterface[0]),
                               innerEnums.toArray(new ConceptualEnum[0]));
  }

  /**
   * Converts the specified InterfaceDefinitionAST into a ConceptualInterface.
   * @param interfaceDefinition - the InterfaceDefinitionAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the ConceptualInterface created, which has the ConceptualInterface as its value
   * @throws ConceptualException - if there is a problem with the conversion
   * @throws ScopeException - if there is a scope collision
   */
  private Scope convert(InterfaceDefinitionAST interfaceDefinition, Scope enclosingScope) throws ConceptualException, ScopeException
  {
    AccessSpecifier access = convert(interfaceDefinition.getAccess(), AccessSpecifier.PUBLIC);
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
        sinceSpecifier = convert((SinceSpecifierAST) modifier);
        break;
      default:
        throw new ConceptualException("Illegal Modifier for an Interface Definition", modifier.getParseInfo());
      }
    }

    ConceptualInterface conceptualInterface = new ConceptualInterface(access, isImmutable, sinceSpecifier, interfaceDefinition.getName().getName());
    Scope scope = ScopeFactory.createInterfaceDefinitionScope(conceptualInterface, enclosingScope);
    scopes.put(interfaceDefinition, scope);

    // convert the type arguments
    TypeArgumentAST[] typeArgumentASTs = interfaceDefinition.getTypeArguments();
    TypeArgument[] typeArguments = new TypeArgument[typeArgumentASTs.length];
    for (int i = 0; i < typeArgumentASTs.length; i++)
    {
      Scope typeArgumentScope = convert(typeArgumentASTs[i], scope);
      typeArguments[i] = (TypeArgument) typeArgumentScope.getValue();
      scope.addChild(typeArgumentASTs[i].getName().getName(), typeArgumentScope);
    }
    conceptualInterface.setTypeArguments(typeArguments);


    // convert each of the members in turn, switching on the member type. each member is added to a list of the members of its type.
    List<MemberVariable>      variables       = new LinkedList<MemberVariable>();
    List<Property>            properties      = new LinkedList<Property>();
    List<Method>              methods         = new LinkedList<Method>();
    List<InnerClass>          innerClasses    = new LinkedList<InnerClass>();
    List<ConceptualInterface> innerInterfaces = new LinkedList<ConceptualInterface>();
    List<ConceptualEnum>      innerEnums      = new LinkedList<ConceptualEnum>();

    MemberAST[] memberASTs = interfaceDefinition.getMembers();
    for (MemberAST memberAST : memberASTs)
    {
      if (memberAST instanceof FieldAST)
      {
        Scope[] variableScopes = convert((FieldAST) memberAST, scope);
        for (Scope variableScope : variableScopes)
        {
          MemberVariable variable = (MemberVariable) variableScope.getValue();
          if (!variable.isStatic())
          {
            throw new ConceptualException("An interface cannot contain non-static member variables.", memberAST.getParseInfo());
          }
          variables.add(variable);
          scope.addChild(variable.getName(), variableScope);
        }
      }
      else if (memberAST instanceof PropertyAST)
      {
        Scope propertyScope = convert((PropertyAST) memberAST, scope);
        Property property = (Property) propertyScope.getValue();
        properties.add(property);
        scope.addChild(property.getName(), propertyScope);
      }
      else if (memberAST instanceof StaticInitializerAST)
      {
        // do nothing, as the static initializer contains no information that we need to convert right now
        // the contained statements will be converted when the scope hierarchy has been built
      }
      else if (memberAST instanceof MethodAST)
      {
        Scope methodScope = convert((MethodAST) memberAST, scope);
        Method method = (Method) methodScope.getValue();
        methods.add(method);
        // combine the scopes for existing methods
        Scope existingScope = scope.getChild(method.getName());
        if (existingScope != null && existingScope.getType() == ScopeType.METHOD)
        {
          @SuppressWarnings("unchecked")
          Set<Method> existingMethods = (Set<Method>) existingScope.getValue();
          existingMethods.add(method);
        }
        else
        {
          // if existingScope is not null here then addChild() will fail, as in the addChild() calls for other members
          scope.addChild(method.getName(), methodScope);
        }
      }
      else if (memberAST instanceof ClassDefinitionAST)
      {
        Scope innerClassScope = convertInnerClass((ClassDefinitionAST) memberAST, scope);
        InnerClass innerClass = (InnerClass) innerClassScope.getValue();
        innerClasses.add(innerClass);
        scope.addChild(innerClass.getName(), innerClassScope);
      }
      else if (memberAST instanceof InterfaceDefinitionAST)
      {
        Scope innerInterfaceScope = convertInnerInterface((InterfaceDefinitionAST) memberAST, scope);
        ConceptualInterface innerInterface = (ConceptualInterface) innerInterfaceScope.getValue();
        innerInterfaces.add(innerInterface);
        scope.addChild(innerInterface.getName(), innerInterfaceScope);
      }
      else if (memberAST instanceof EnumDefinitionAST)
      {
        Scope innerEnumScope = convertInnerEnum((EnumDefinitionAST) memberAST, scope);
        ConceptualEnum innerEnum = (ConceptualEnum) innerEnumScope.getValue();
        innerEnums.add(innerEnum);
        scope.addChild(innerEnum.getName(), innerEnumScope);
      }
      else
      {
        throw new UnsupportedOperationException("Cannot translate an interface member that is not a static field, property, static initializer, method, or type definition.");
      }
    }

    conceptualInterface.setMembers(new StaticInitializer(),
                                   variables.toArray(new MemberVariable[0]),
                                   properties.toArray(new Property[0]),
                                   methods.toArray(new Method[0]),
                                   innerClasses.toArray(new InnerClass[0]),
                                   innerInterfaces.toArray(new ConceptualInterface[0]),
                                   innerEnums.toArray(new ConceptualEnum[0]));

    return scope;
  }

  /**
   * Converts the specified EnumDefinitionAST into a ConceptualEnum.
   * @param enumDefinition - the EnumDefinitionAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the ConceptualEnum created, which has the ConceptualEnum as its value
   * @throws ConceptualException - if there is a problem with the conversion
   * @throws ScopeException - if there is a scope collision
   */
  private Scope convert(EnumDefinitionAST enumDefinition, Scope enclosingScope) throws ConceptualException, ScopeException
  {
    AccessSpecifier accessSpecifier = convert(enumDefinition.getAccessSpecifier(), AccessSpecifier.PUBLIC);
    ModifierAST[] modifiers = enumDefinition.getModifiers();
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : modifiers)
    {
      switch (modifier.getType())
      {
      case SINCE_SPECIFIER:
        sinceSpecifier = convert((SinceSpecifierAST) modifier);
        break;
      default:
        throw new ConceptualException("Illegal Modifier for an Enum Definition", modifier.getParseInfo());
      }
    }

    ConceptualEnum conceptualEnum = new ConceptualEnum(accessSpecifier, sinceSpecifier, enumDefinition.getName().getName());
    Scope scope = ScopeFactory.createEnumDefinitionScope(conceptualEnum, enclosingScope);
    scopes.put(enumDefinition, scope);

    // convert each of the members in turn, switching on the member type. each member is added to a list of the members of its type.

    // convert the enum constants
    EnumConstantAST[] constantASTs = enumDefinition.getConstants();
    EnumConstant[] constants = new EnumConstant[constantASTs.length];
    for (int i = 0; i < constantASTs.length; i++)
    {
      Scope constantScope = convert(constantASTs[i], scope);
      constants[i] = (EnumConstant) constantScope.getValue();
      scope.addChild(constants[i].getName(), constantScope);
    }
    conceptualEnum.setConstants(constants);

    // convert the other members
    List<MemberVariable>      variables       = new LinkedList<MemberVariable>();
    List<Property>            properties      = new LinkedList<Property>();
    List<Constructor>         constructors    = new LinkedList<Constructor>();
    List<Method>              methods         = new LinkedList<Method>();
    List<InnerClass>          innerClasses    = new LinkedList<InnerClass>();
    List<ConceptualInterface> innerInterfaces = new LinkedList<ConceptualInterface>();
    List<ConceptualEnum>      innerEnums      = new LinkedList<ConceptualEnum>();

    MemberAST[] memberASTs = enumDefinition.getMembers();
    for (MemberAST memberAST : memberASTs)
    {
      if (memberAST instanceof FieldAST)
      {
        Scope[] variableScopes = convert((FieldAST) memberAST, scope);
        for (Scope variableScope : variableScopes)
        {
          MemberVariable variable = (MemberVariable) variableScope.getValue();
          variables.add(variable);
          scope.addChild(variable.getName(), variableScope);
        }
      }
      else if (memberAST instanceof PropertyAST)
      {
        Scope propertyScope = convert((PropertyAST) memberAST, scope);
        Property property = (Property) propertyScope.getValue();
        properties.add(property);
        scope.addChild(property.getName(), propertyScope);
      }
      else if (memberAST instanceof StaticInitializerAST)
      {
        // do nothing, as the static initializer contains no information that we need to convert right now
        // the contained statements will be converted when the scope hierarchy has been built
      }
      else if (memberAST instanceof ConstructorAST)
      {
        Scope constructorScope = convert((ConstructorAST) memberAST, scope);
        Constructor constructor = (Constructor) constructorScope.getValue();
        constructors.add(constructor);
        // no need to add to the scope here, you cannot reference a constructor directly, just via the enum constant definitions
      }
      else if (memberAST instanceof MethodAST)
      {
        Scope methodScope = convert((MethodAST) memberAST, scope);
        Method method = (Method) methodScope.getValue();
        methods.add(method);
        // combine the scopes for existing methods
        Scope existingScope = scope.getChild(method.getName());
        if (existingScope != null && existingScope.getType() == ScopeType.METHOD)
        {
          @SuppressWarnings("unchecked")
          Set<Method> existingMethods = (Set<Method>) existingScope.getValue();
          existingMethods.add(method);
        }
        else
        {
          // if existingScope is not null here then addChild() will fail, as in the addChild() calls for other members
          scope.addChild(method.getName(), methodScope);
        }
      }
      else if (memberAST instanceof ClassDefinitionAST)
      {
        Scope innerClassScope = convertInnerClass((ClassDefinitionAST) memberAST, scope);
        InnerClass innerClass = (InnerClass) innerClassScope.getValue();
        innerClasses.add(innerClass);
        scope.addChild(innerClass.getName(), innerClassScope);
      }
      else if (memberAST instanceof InterfaceDefinitionAST)
      {
        Scope innerInterfaceScope = convertInnerInterface((InterfaceDefinitionAST) memberAST, scope);
        ConceptualInterface innerInterface = (ConceptualInterface) innerInterfaceScope.getValue();
        innerInterfaces.add(innerInterface);
        scope.addChild(innerInterface.getName(), innerInterfaceScope);
      }
      else if (memberAST instanceof EnumDefinitionAST)
      {
        Scope innerEnumScope = convertInnerEnum((EnumDefinitionAST) memberAST, scope);
        ConceptualEnum innerEnum = (ConceptualEnum) innerEnumScope.getValue();
        innerEnums.add(innerEnum);
        scope.addChild(innerEnum.getName(), innerEnumScope);
      }
      else
      {
        throw new UnsupportedOperationException("Cannot translate a class member that is not a field, property, static initializer, constructor, method, or type definition.");
      }
    }

    conceptualEnum.setMembers(new StaticInitializer(), new VariableInitializers(),
                               variables.toArray(new MemberVariable[0]),
                               properties.toArray(new Property[0]),
                               constructors.toArray(new Constructor[0]),
                               methods.toArray(new Method[0]),
                               innerClasses.toArray(new InnerClass[0]),
                               innerInterfaces.toArray(new ConceptualInterface[0]),
                               innerEnums.toArray(new ConceptualEnum[0]));

    return scope;
  }

  /**
   * Converts the specified EnumConstantAST into an EnumConstant.
   * @param enumConstantAST - the EnumConstantAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the EnumConstant created, which has the EnumConstant as its value
   * @throws ConceptualException - if there is a problem with the conversion
   * @throws ScopeException - if there is a scope collision
   */
  private Scope convert(EnumConstantAST enumConstantAST, Scope enclosingScope) throws ConceptualException, ScopeException
  {
    EnumConstant enumConstant = new EnumConstant(enumConstantAST.getName().getName());
    Scope scope = ScopeFactory.createEnumConstantScope(enumConstant, enclosingScope);
    scopes.put(enumConstantAST, scope);

    if (enumConstantAST.getMembers() == null)
    {
      return scope;
    }

    Scope dummyScope = ScopeFactory.createDummyScope(scope);

    // convert the other members
    List<MemberVariable>      variables       = new LinkedList<MemberVariable>();
    List<Property>            properties      = new LinkedList<Property>();
    List<Constructor>         constructors    = new LinkedList<Constructor>();
    List<Method>              methods         = new LinkedList<Method>();
    List<InnerClass>          innerClasses    = new LinkedList<InnerClass>();
    List<ConceptualInterface> innerInterfaces = new LinkedList<ConceptualInterface>();
    List<ConceptualEnum>      innerEnums      = new LinkedList<ConceptualEnum>();

    for (MemberAST memberAST : enumConstantAST.getMembers())
    {
      if (memberAST instanceof FieldAST)
      {
        Scope[] variableScopes = convert((FieldAST) memberAST, dummyScope);
        for (Scope variableScope : variableScopes)
        {
          MemberVariable variable = (MemberVariable) variableScope.getValue();
          variables.add(variable);
          dummyScope.addChild(variable.getName(), variableScope);
        }
      }
      else if (memberAST instanceof PropertyAST)
      {
        Scope propertyScope = convert((PropertyAST) memberAST, dummyScope);
        Property property = (Property) propertyScope.getValue();
        properties.add(property);
        dummyScope.addChild(property.getName(), propertyScope);
      }
      else if (memberAST instanceof StaticInitializerAST)
      {
        // do nothing, as the static initializer contains no information that we need to convert right now
        // the contained statements will be converted when the scope hierarchy has been built
      }
      else if (memberAST instanceof ConstructorAST)
      {
        Scope constructorScope = convert((ConstructorAST) memberAST, dummyScope);
        Constructor constructor = (Constructor) constructorScope.getValue();
        constructors.add(constructor);
        // no need to add to the scope here, you cannot reference a constructor directly
      }
      else if (memberAST instanceof MethodAST)
      {
        Scope methodScope = convert((MethodAST) memberAST, dummyScope);
        Method method = (Method) methodScope.getValue();
        methods.add(method);
        // combine the scopes for existing methods
        Scope existingScope = dummyScope.getChild(method.getName());
        if (existingScope != null && existingScope.getType() == ScopeType.METHOD)
        {
          @SuppressWarnings("unchecked")
          Set<Method> existingMethods = (Set<Method>) existingScope.getValue();
          existingMethods.add(method);
        }
        else
        {
          // if existingScope is not null here then addChild() will fail, as in the addChild() calls for other members
          dummyScope.addChild(method.getName(), methodScope);
        }
      }
      else if (memberAST instanceof ClassDefinitionAST)
      {
        Scope innerClassScope = convertInnerClass((ClassDefinitionAST) memberAST, dummyScope);
        InnerClass innerClass = (InnerClass) innerClassScope.getValue();
        innerClasses.add(innerClass);
        dummyScope.addChild(innerClass.getName(), innerClassScope);
      }
      else if (memberAST instanceof InterfaceDefinitionAST)
      {
        Scope innerInterfaceScope = convertInnerInterface((InterfaceDefinitionAST) memberAST, dummyScope);
        ConceptualInterface innerInterface = (ConceptualInterface) innerInterfaceScope.getValue();
        innerInterfaces.add(innerInterface);
        dummyScope.addChild(innerInterface.getName(), innerInterfaceScope);
      }
      else if (memberAST instanceof EnumDefinitionAST)
      {
        Scope innerEnumScope = convertInnerEnum((EnumDefinitionAST) memberAST, dummyScope);
        ConceptualEnum innerEnum = (ConceptualEnum) innerEnumScope.getValue();
        innerEnums.add(innerEnum);
        dummyScope.addChild(innerEnum.getName(), innerEnumScope);
      }
      else
      {
        throw new UnsupportedOperationException("Cannot translate an enum constant member that is not a field, property, static initializer, constructor, method, or type definition.");
      }
    }

    enumConstant.setMembers(new StaticInitializer(), new VariableInitializers(),
                            variables.toArray(new MemberVariable[0]), properties.toArray(new Property[0]),
                            constructors.toArray(new Constructor[0]), methods.toArray(new Method[0]),
                            innerClasses.toArray(new InnerClass[0]), innerInterfaces.toArray(new ConceptualInterface[0]), innerEnums.toArray(new ConceptualEnum[0]));

    return scope;
  }

  /**
   * Converts the specified TypeArgumentAST into a TypeArgument.
   * @param typeArgumentAST - the TypeArgumentAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the TypeArgument created, which has the TypeArgument as its value
   */
  private Scope convert(TypeArgumentAST typeArgumentAST, Scope enclosingScope)
  {
    TypeArgument typeArgument = new TypeArgument(typeArgumentAST.getName().getName());
    Scope scope = ScopeFactory.createTypeArgumentScope(typeArgument, enclosingScope);
    scopes.put(typeArgumentAST, scope);
    return scope;
  }

  /**
   * Converts the specified FieldAST into an array of MemberVariables.
   * @param fieldAST - the FieldAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual objects' scopes
   * @return the Scopes of the MemberVariables created, which have the MemberVariables as their values
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private Scope[] convert(FieldAST fieldAST, Scope enclosingScope) throws ConceptualException
  {
    AccessSpecifier accessSpecifier = convert(fieldAST.getAccessSpecifier(), AccessSpecifier.PRIVATE);

    boolean isFinal = false;
    boolean isMutable = false;
    boolean isStatic = false;
    boolean isVolatile = false;
    boolean isTransient = false;
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : fieldAST.getModifiers())
    {
      switch (modifier.getType())
      {
      case FINAL:
        isFinal = true;
        break;
      case MUTABLE:
        isMutable = true;
        break;
      case SINCE_SPECIFIER:
        sinceSpecifier = convert((SinceSpecifierAST) modifier);
        break;
      case STATIC:
        isStatic = true;
        break;
      case TRANSIENT:
        isTransient = true;
        break;
      case VOLATILE:
        isVolatile = true;
        break;
      default:
        throw new ConceptualException("Illegal Modifier for a Field", modifier.getParseInfo());
      }
    }

    DeclarationAssigneeAST[] assignees = fieldAST.getAssignees();
    MemberVariable[] memberVariables = new MemberVariable[assignees.length];
    Scope[] memberScopes = new Scope[assignees.length];
    for (int i = 0; i < assignees.length; i++)
    {
      memberVariables[i] = new MemberVariable(accessSpecifier, isFinal, isMutable, isStatic, isVolatile, isTransient, sinceSpecifier, assignees[i].getName().getName());
      memberScopes[i] = ScopeFactory.createMemberVariableScope(memberVariables[i], enclosingScope);
      scopes.put(assignees[i], memberScopes[i]);
    }
    return memberScopes;
  }

  /**
   * Converts the specified PropertyAST into a Property
   * @param propertyAST - the PropertyAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the Property created, which has the Property as its value
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private Scope convert(PropertyAST propertyAST, Scope enclosingScope) throws ConceptualException
  {
    boolean isFinal = false;
    boolean isMutable = false;
    boolean isSealed = false;
    boolean isStatic = false;
    boolean isSynchronized = false;
    boolean isTransient = false;
    boolean isVolatile = false;
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : propertyAST.getModifiers())
    {
      switch (modifier.getType())
      {
      case FINAL:
        isFinal = true;
        break;
      case MUTABLE:
        isMutable = true;
        break;
      case SEALED:
        isSealed = true;
        break;
      case SINCE_SPECIFIER:
        sinceSpecifier = convert((SinceSpecifierAST) modifier);
        break;
      case STATIC:
        isStatic = true;
        break;
      case SYNCHRONIZED:
        isSynchronized = true;
        break;
      case TRANSIENT:
        isTransient = true;
        break;
      case VOLATILE:
        isVolatile = true;
        break;
      default:
        throw new ConceptualException("Illegal Modifier for a Property", modifier.getParseInfo());
      }
    }

    AccessSpecifier retrieveAccessSpecifier = convert(propertyAST.getRetrieveAccess(), AccessSpecifier.PUBLIC);
    AccessSpecifier assignAccessSpecifier = convert(propertyAST.getAssignAccess(), AccessSpecifier.PUBLIC);

    Property property = new Property(isSealed, isMutable, isFinal, isStatic, isSynchronized, isTransient, isVolatile, sinceSpecifier,
                                     propertyAST.getName().getName(), retrieveAccessSpecifier, assignAccessSpecifier);
    Scope scope = ScopeFactory.createPropertyScope(property, enclosingScope);
    scopes.put(propertyAST, scope);

    return scope;
  }

  /**
   * Converts the specified ConstructorAST into a Constructor
   * @param constructorAST - the ConstructorAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the Constructor created, which has the Constructor as its value
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private Scope convert(ConstructorAST constructorAST, Scope enclosingScope) throws ConceptualException
  {
    AccessSpecifier accessSpecifier = convert(constructorAST.getAccessSpecifier(), AccessSpecifier.PUBLIC);
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : constructorAST.getModifiers())
    {
      switch (modifier.getType())
      {
      case SINCE_SPECIFIER:
        sinceSpecifier = convert((SinceSpecifierAST) modifier);
        break;
      default:
        throw new ConceptualException("Illegal Modifier for a Constructor", modifier.getParseInfo());
      }
    }

    Constructor constructor = new Constructor(accessSpecifier, sinceSpecifier, constructorAST.getName().getName());
    Scope scope = ScopeFactory.createConstructorScope(constructor, enclosingScope);
    scopes.put(constructorAST, scope);

    return scope;
  }

  /**
   * Converts the specified MethodAST into a Method
   * @param methodAST - the MethodAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the Method created, which has the Method as its value
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private Scope convert(MethodAST methodAST, Scope enclosingScope) throws ConceptualException
  {
    AccessSpecifier accessSpecifier = convert(methodAST.getAccessSpecifier(), AccessSpecifier.PUBLIC);

    boolean isStatic = false;
    boolean isSealed = false;
    boolean isAbstract = false;
    boolean isImmutable = false;
    boolean isSynchronized = false;
    SinceSpecifier sinceSpecifier = null;
    NativeSpecifier nativeSpecifier = null;
    for (ModifierAST modifier : methodAST.getModifiers())
    {
      switch (modifier.getType())
      {
      case ABSTRACT:
        isAbstract = true;
        break;
      case IMMUTABLE:
        isImmutable = true;
        break;
      case NATIVE_SPECIFIER:
        nativeSpecifier = convert((NativeSpecifierAST) modifier);
        break;
      case SEALED:
        isSealed = true;
        break;
      case SINCE_SPECIFIER:
        sinceSpecifier = convert((SinceSpecifierAST) modifier);
        break;
      case STATIC:
        isStatic = true;
        break;
      case SYNCHRONIZED:
        isSynchronized = true;
        break;
      default:
        throw new ConceptualException("Illegal Modifier for a Method", modifier.getParseInfo());
      }
    }

    Method method = new Method(accessSpecifier, isAbstract, isSealed, isStatic, isSynchronized, isImmutable,
                               sinceSpecifier, nativeSpecifier, methodAST.getName().getName());
    Scope scope = ScopeFactory.createMethodScope(method, enclosingScope);
    scopes.put(methodAST, scope);

    return scope;
  }

  /**
   * Converts the specified ClassDefinitionAST into an InnerClass
   * @param classDefinitionAST - the ClassDefinitionAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the InnerClass created, which has the InnerClass as its value
   * @throws ConceptualException - if there is a problem with the conversion
   * @throws ScopeException - if there is a scope collision
   */
  private Scope convertInnerClass(ClassDefinitionAST classDefinitionAST, Scope enclosingScope) throws ConceptualException, ScopeException
  {
    // convert AccessSpecifier and Modifiers
    AccessSpecifier access = convert(classDefinitionAST.getAccess(), AccessSpecifier.PUBLIC);
    ModifierAST[] modifiers = classDefinitionAST.getModifiers();
    boolean isAbstract = false;
    boolean isSealed = false;
    boolean isImmutable = false;
    boolean isStatic = false;
    SinceSpecifier sinceSpecifier = null;
    for (ModifierAST modifier : modifiers)
    {
      switch (modifier.getType())
      {
      case ABSTRACT:
        isAbstract = true;
        break;
      case SEALED:
        isSealed = true;
        break;
      case STATIC:
        isStatic = true;
        break;
      case IMMUTABLE:
        isImmutable = true;
        break;
      case SINCE_SPECIFIER:
        sinceSpecifier = convert((SinceSpecifierAST) modifier);
        break;
      default:
        throw new ConceptualException("Illegal Modifier for an Inner Class Definition", modifier.getParseInfo());
      }
    }

    // Create the InnerClass and the scope for it
    InnerClass conceptualClass = new InnerClass(access, isAbstract, isSealed, isImmutable, sinceSpecifier, classDefinitionAST.getName().getName(), isStatic);
    Scope scope = ScopeFactory.createClassDefinitionScope(conceptualClass, enclosingScope);
    scopes.put(classDefinitionAST, scope);

    addClassData(scope, classDefinitionAST);

    return scope;

  }

  /**
   * Converts the specified InterfaceDefinitionAST into a ConceptualInterface
   * @param interfaceDefinitionAST - the InterfaceDefinitionAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the ConceptualInterface created, which has the ConceptualInterface as its value
   * @throws ConceptualException - if there is a problem with the conversion
   * @throws ScopeException - if there is a scope collision
   */
  private Scope convertInnerInterface(InterfaceDefinitionAST interfaceDefinitionAST, Scope enclosingScope) throws ConceptualException, ScopeException
  {
    return convert(interfaceDefinitionAST, enclosingScope);
  }

  /**
   * Converts the specified EnumDefinitionAST into a ConceptualEnum
   * @param enumDefinitionAST - the EnumDefinitionAST to convert
   * @param enclosingScope - the scope to make the parent of the new conceptual object's scope
   * @return the Scope of the ConceptualEnum created, which has the ConceptualEnum as its value
   * @throws ConceptualException - if there is a problem with the conversion
   * @throws ScopeException - if there is a scope collision
   */
  private Scope convertInnerEnum(EnumDefinitionAST enumDefinitionAST, Scope enclosingScope) throws ConceptualException, ScopeException
  {
    return convert(enumDefinitionAST, enclosingScope);
  }

  /**
   * Converts the specified AccessSpecifierAST into an AccessSpecifier
   * @param accessSpecifierAST - the AccessSpecifierAST to convert
   * @param defaultValue - the default value to return if the AccessSpecifierAST is null
   * @return the AccessSpecifier converted
   */
  private AccessSpecifier convert(AccessSpecifierAST accessSpecifierAST, AccessSpecifier defaultValue)
  {
    switch (accessSpecifierAST.getType())
    {
    case PACKAGE:
      return AccessSpecifier.PACKAGE;
    case PACKAGE_PROTECTED:
      return AccessSpecifier.PACKAGE_PROTECTED;
    case PRIVATE:
      return AccessSpecifier.PRIVATE;
    case PROTECTED:
      return AccessSpecifier.PROTECTED;
    case PUBLIC:
      return AccessSpecifier.PUBLIC;
    default:
      return defaultValue;
    }
  }

  /**
   * Converts the specified SinceSpecifierAST into a SinceSpecifier
   * @param sinceSpecifierAST - the SinceSpecifierAST to convert
   * @return the SinceSpecifier created
   * @throws ConceptualException - if the version number contains an illegal value (less than 0 or more than Integer.MAX_VALUE)
   */
  private SinceSpecifier convert(SinceSpecifierAST sinceSpecifierAST) throws ConceptualException
  {
    VersionNumberAST version = sinceSpecifierAST.getVersion();
    IntegerLiteralAST[] literals = version.getVersionParts();
    int[] values = new int[literals.length];
    for (int i = 0; i < literals.length; i++)
    {
      BigInteger value = literals[i].getValue();
      int intValue = value.intValue();
      if (intValue < 0 || !BigInteger.valueOf(intValue).equals(value))
      {
        throw new ConceptualException("Illegal value in a Since Specifier: " + value, literals[i].getParseInfo());
      }
      values[i] = intValue;
    }
    return new SinceSpecifier(values);
  }

  /**
   * Converts the specified NativeSpecifierAST into a NativeSpecifier
   * @param nativeSpecifierAST - the NativeSpecifierAST to convert
   * @return the NativeSpecifier created
   */
  private NativeSpecifier convert(NativeSpecifierAST nativeSpecifierAST)
  {
    String nativeName = nativeSpecifierAST.getNativeName().getLiteralValue();
    return new NativeSpecifier(nativeName);
  }

}

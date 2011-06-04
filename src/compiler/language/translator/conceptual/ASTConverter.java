package compiler.language.translator.conceptual;

import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
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
import compiler.language.ast.terminal.IntegerLiteralAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.terminal.SinceSpecifierAST;
import compiler.language.ast.terminal.VersionNumberAST;
import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.ast.topLevel.ImportDeclarationAST;
import compiler.language.ast.topLevel.PackageDeclarationAST;
import compiler.language.ast.topLevel.TypeDefinitionAST;
import compiler.language.ast.type.ArrayTypeAST;
import compiler.language.ast.type.BooleanTypeAST;
import compiler.language.ast.type.CharacterTypeAST;
import compiler.language.ast.type.ClosureTypeAST;
import compiler.language.ast.type.FloatingTypeAST;
import compiler.language.ast.type.FloatingTypeLengthAST;
import compiler.language.ast.type.IntegerTypeAST;
import compiler.language.ast.type.NormalTypeArgumentAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TupleTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.ast.type.VoidTypeAST;
import compiler.language.ast.type.WildcardTypeArgumentAST;
import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.ast.typeDefinition.EnumConstantAST;
import compiler.language.ast.typeDefinition.EnumDefinitionAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.UnresolvableException;
import compiler.language.conceptual.member.Constructor;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.member.StaticInitializer;
import compiler.language.conceptual.member.VariableInitializers;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.misc.NativeSpecifier;
import compiler.language.conceptual.misc.SinceSpecifier;
import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.conceptual.topLevel.ConceptualPackage;
import compiler.language.conceptual.topLevel.Import;
import compiler.language.conceptual.type.ArrayType;
import compiler.language.conceptual.type.ClosureType;
import compiler.language.conceptual.type.NormalTypeArgument;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.PrimitiveType;
import compiler.language.conceptual.type.TupleType;
import compiler.language.conceptual.type.Type;
import compiler.language.conceptual.type.TypeArgument;
import compiler.language.conceptual.type.TypeParameter;
import compiler.language.conceptual.type.WildcardTypeArgument;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
import compiler.language.conceptual.typeDefinition.EnumConstant;
import compiler.language.conceptual.typeDefinition.InnerClass;
import compiler.language.conceptual.typeDefinition.InnerEnum;
import compiler.language.conceptual.typeDefinition.InnerInterface;
import compiler.language.conceptual.typeDefinition.OuterClass;
import compiler.language.conceptual.typeDefinition.OuterEnum;
import compiler.language.conceptual.typeDefinition.OuterInterface;
import compiler.language.conceptual.typeDefinition.TypeDefinition;

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

  private ConceptualPackage rootPackage;

  // a mapping from conceptual node back to AST node, which allows the name resolver to find the names this ASTConverter cannot convert
  private Map<Object, Object> conceptualASTNodes;

  private Map<File, ConceptualFile> convertedFiles = new HashMap<File, ConceptualFile>();

  // TODO: handle duplicate modifiers properly (instead of just disregarding them)

  /**
   * Creates a new ASTConverter that will add all converted AST nodes to the specified ConceptualProgram
   * @param program - the ConceptualProgram to add all converted AST nodes to
   * @param rootPackage - the root package of the program
   */
  public ASTConverter(ConceptualPackage rootPackage)
  {
    this.rootPackage = rootPackage;
    conceptualASTNodes = new HashMap<Object, Object>();
  }

  /**
   * @return the mapping from Conceptual node back to AST node
   */
  public Map<Object, Object> getConceptualASTNodes()
  {
    return conceptualASTNodes;
  }

  /**
   * Converts the specified CompilationUnitAST into a ConceptualFile
   * @param file - the file that is being converted (which was parsed to produce compilationUnit)
   * @param compilationUnit - the CompilationUnitAST to convert
   * @param expectedPackage - the package that this file is expected to be in
   * @return the ConceptualFile converted
   * @throws ConceptualException - if there is a problem converting the AST to the conceptual view
   * @throws NameConflictException - if a name conflict is detected while converting the compilation unit
   */
  public ConceptualFile convert(File file, CompilationUnitAST compilationUnit, ConceptualPackage expectedPackage) throws ConceptualException, NameConflictException
  {
    // convert the package, and look it up in the package hierarchy
    PackageDeclarationAST packageDeclaration = compilationUnit.getPackageDeclaration();
    ConceptualPackage enclosingPackage;
    if (packageDeclaration == null)
    {
      enclosingPackage = rootPackage;
    }
    else
    {
      QName packageName = new QName(packageDeclaration.getPackageName().getNameStrings());
      Resolvable packageResult = null;
      try
      {
        packageResult = rootPackage.resolve(packageName, false);
        if (packageResult == null)
        {
          throw new ConceptualException("Package name cannot be resolved!", packageDeclaration.getPackageName().getParseInfo());
        }
      }
      catch (UnresolvableException e)
      {
        throw new ConceptualException("Package name cannot be resolved!", e, packageDeclaration.getPackageName().getParseInfo());
      }
      if (packageResult.getType() != ScopeType.PACKAGE)
      {
        throw new ConceptualException("Package name does not resolve to a package", packageDeclaration.getPackageName().getParseInfo());
      }
      enclosingPackage = (ConceptualPackage) packageResult;
      if (enclosingPackage != expectedPackage)
      {
        throw new ConceptualException("Expected package name to be \"" + expectedPackage.getName() + "\"", packageDeclaration.getPackageName().getParseInfo());
      }
    }

    // check the cache of converted files first
    ConceptualFile existingConceptualFile = convertedFiles.get(file);
    if (existingConceptualFile != null)
    {
      return existingConceptualFile;
    }

    // convert the imports
    List<Import> imports = new LinkedList<Import>();
    for (ImportDeclarationAST importDeclaration : compilationUnit.getImports())
    {
      QName qname = new QName(importDeclaration.getName().getNameStrings());
      imports.add(new Import(qname, importDeclaration.isAll()));
    }

    ConceptualFile conceptualFile = new ConceptualFile(rootPackage, enclosingPackage, imports);

    // convert the type definitions
    Set<ConceptualClass> classes = new HashSet<ConceptualClass>();
    Set<ConceptualInterface> interfaces = new HashSet<ConceptualInterface>();
    Set<ConceptualEnum> enums = new HashSet<ConceptualEnum>();
    for (TypeDefinitionAST typeDefinition : compilationUnit.getTypes())
    {
      System.out.println("Converting type definition: " + typeDefinition); // TODO
      if (typeDefinition instanceof ClassDefinitionAST)
      {
        classes.add(convert((ClassDefinitionAST) typeDefinition, conceptualFile));
      }
      else if (typeDefinition instanceof InterfaceDefinitionAST)
      {
        interfaces.add(convert((InterfaceDefinitionAST) typeDefinition, conceptualFile));
      }
      else if (typeDefinition instanceof EnumDefinitionAST)
      {
        enums.add(convert((EnumDefinitionAST) typeDefinition, conceptualFile));
      }
      else
      {
        throw new UnsupportedOperationException("Cannot translate a type definition that does not represent a class, interface or enum.");
      }
    }
    conceptualFile.setTypes(classes, interfaces, enums);

    conceptualASTNodes.put(conceptualFile, compilationUnit);
    convertedFiles.put(file, conceptualFile);
    return conceptualFile;
  }

  /**
   * Converts the specified ClassDefinitionAST into a ConceptualClass.
   * @param classDefinition - the ClassDefinitionAST to convert
   * @param enclosingFile - the file to make the parent of the new conceptual class
   * @return the ConceptualClass created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private ConceptualClass convert(ClassDefinitionAST classDefinition, ConceptualFile enclosingFile) throws ConceptualException
  {
    // convert AccessSpecifier and Modifiers
    AccessSpecifier access = convert(classDefinition.getAccess(), AccessSpecifier.PUBLIC);
    if (access != AccessSpecifier.PUBLIC && access != AccessSpecifier.PACKAGE)
    {
      // the getParseInfo() call here will not throw a NullPointerException because if classDefinition.getAccess() was null then access would be PUBLIC
      throw new ConceptualException("Invalid access specifier for a class.", classDefinition.getAccess().getParseInfo());
    }
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
    OuterClass outerClass = new OuterClass(enclosingFile, access, isAbstract, isSealed, isImmutable, sinceSpecifier, classDefinition.getName().getName());

    addClassData(outerClass, classDefinition);

    conceptualASTNodes.put(outerClass, classDefinition);
    return outerClass;
  }

  /**
   * Adds all of the data to a conceptual class that is not specified in the constructor.
   * @param conceptualClass - the converted class definition to add data to
   * @param classDefinition - the ClassDefinitionAST to convert the type parameters and members of
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private void addClassData(ConceptualClass conceptualClass, ClassDefinitionAST classDefinition) throws ConceptualException
  {
    // convert the type arguments
    TypeParameterAST[] typeParameterASTs = classDefinition.getTypeParameters();
    TypeParameter[] typeParameters = new TypeParameter[typeParameterASTs.length];
    for (int i = 0; i < typeParameterASTs.length; i++)
    {
      typeParameters[i] =  convert(typeParameterASTs[i]);
    }
    conceptualClass.setTypeParameters(typeParameters);


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
        MemberVariable[] memberVariables = convert((FieldAST) memberAST, conceptualClass);
        for (MemberVariable variable : memberVariables)
        {
          variables.add(variable);
        }
      }
      else if (memberAST instanceof PropertyAST)
      {
        properties.add(convert((PropertyAST) memberAST, conceptualClass));
      }
      else if (memberAST instanceof StaticInitializerAST)
      {
        // do nothing, as the static initializer contains no information that we need to convert right now
        // the contained statements will be converted when the scope hierarchy has been built
      }
      else if (memberAST instanceof ConstructorAST)
      {
        constructors.add(convert((ConstructorAST) memberAST, conceptualClass));
      }
      else if (memberAST instanceof MethodAST)
      {
        methods.add(convert((MethodAST) memberAST, conceptualClass));
      }
      else if (memberAST instanceof ClassDefinitionAST)
      {
        innerClasses.add(convertInnerClass((ClassDefinitionAST) memberAST, conceptualClass));
      }
      else if (memberAST instanceof InterfaceDefinitionAST)
      {
        innerInterfaces.add(convertInnerInterface((InterfaceDefinitionAST) memberAST, conceptualClass));
      }
      else if (memberAST instanceof EnumDefinitionAST)
      {
        innerEnums.add(convertInnerEnum((EnumDefinitionAST) memberAST, conceptualClass));
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
   * @param enclosingFile - the file to make the parent of the new conceptual interface
   * @return the ConceptualInterface created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private ConceptualInterface convert(InterfaceDefinitionAST interfaceDefinition, ConceptualFile enclosingFile) throws ConceptualException
  {
    AccessSpecifier access = convert(interfaceDefinition.getAccess(), AccessSpecifier.PUBLIC);
    if (access != AccessSpecifier.PUBLIC && access != AccessSpecifier.PACKAGE)
    {
      // the getParseInfo() call here will not throw a NullPointerException because if interfaceDefinition.getAccess() was null then access would be PUBLIC
      throw new ConceptualException("Invalid access specifier for an interface.", interfaceDefinition.getAccess().getParseInfo());
    }
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

    OuterInterface outerInterface = new OuterInterface(enclosingFile, access, isImmutable, sinceSpecifier, interfaceDefinition.getName().getName());

    addInterfaceData(outerInterface, interfaceDefinition);

    conceptualASTNodes.put(outerInterface, interfaceDefinition);
    return outerInterface;
  }

  /**
   * Adds all of the data to a conceptual interface that is not specified in the constructor.
   * @param conceptualInterface - the converted interface definition to add data to
   * @param interfaceDefinition - the InterfaceDefinitionAST to convert the type parameters and members of
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private void addInterfaceData(ConceptualInterface conceptualInterface, InterfaceDefinitionAST interfaceDefinition) throws ConceptualException
  {
    // convert the type arguments
    TypeParameterAST[] typeParameterASTs = interfaceDefinition.getTypeParameters();
    TypeParameter[] typeParameters = new TypeParameter[typeParameterASTs.length];
    for (int i = 0; i < typeParameterASTs.length; i++)
    {
      typeParameters[i] = convert(typeParameterASTs[i]);
    }
    conceptualInterface.setTypeParameters(typeParameters);


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
        MemberVariable[] memberVariables = convert((FieldAST) memberAST, conceptualInterface);
        for (MemberVariable variable : memberVariables)
        {
          if (!variable.isStatic())
          {
            throw new ConceptualException("An interface cannot contain non-static member variables.", memberAST.getParseInfo());
          }
          variables.add(variable);
        }
      }
      else if (memberAST instanceof PropertyAST)
      {
        properties.add(convert((PropertyAST) memberAST, conceptualInterface));
      }
      else if (memberAST instanceof StaticInitializerAST)
      {
        // do nothing, as the static initializer contains no information that we need to convert right now
        // the contained statements will be converted when the scope hierarchy has been built
      }
      else if (memberAST instanceof MethodAST)
      {
        methods.add(convert((MethodAST) memberAST, conceptualInterface));
      }
      else if (memberAST instanceof ClassDefinitionAST)
      {
        innerClasses.add(convertInnerClass((ClassDefinitionAST) memberAST, conceptualInterface));
      }
      else if (memberAST instanceof InterfaceDefinitionAST)
      {
        innerInterfaces.add(convertInnerInterface((InterfaceDefinitionAST) memberAST, conceptualInterface));
      }
      else if (memberAST instanceof EnumDefinitionAST)
      {
        innerEnums.add(convertInnerEnum((EnumDefinitionAST) memberAST, conceptualInterface));
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
  }

  /**
   * Converts the specified EnumDefinitionAST into a ConceptualEnum.
   * @param enumDefinition - the EnumDefinitionAST to convert
   * @param enclosingFile - the file to make the parent of the new conceptual enum
   * @return the ConceptualEnum created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private ConceptualEnum convert(EnumDefinitionAST enumDefinition, ConceptualFile enclosingFile) throws ConceptualException
  {
    AccessSpecifier access = convert(enumDefinition.getAccessSpecifier(), AccessSpecifier.PUBLIC);
    if (access != AccessSpecifier.PUBLIC && access != AccessSpecifier.PACKAGE)
    {
      // the getParseInfo() call here will not throw a NullPointerException because if enumDefinition.getAccessSpecifier() was null then access would be PUBLIC
      throw new ConceptualException("Invalid access specifier for an enum.", enumDefinition.getAccessSpecifier().getParseInfo());
    }
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

    OuterEnum outerEnum = new OuterEnum(enclosingFile, access, sinceSpecifier, enumDefinition.getName().getName());

    addEnumData(outerEnum, enumDefinition);

    conceptualASTNodes.put(outerEnum, enumDefinition);
    return outerEnum;
  }

  /**
   * Adds all of the data to a conceptual enum that is not specified in the constructor.
   * @param conceptualEnum - the converted enum definition to add data to
   * @param enumDefinition - the EnumDefinitionAST to convert the members of
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private void addEnumData(ConceptualEnum conceptualEnum, EnumDefinitionAST enumDefinition) throws ConceptualException
  {
    // convert each of the members in turn, switching on the member type. each member is added to a list of the members of its type.

    // convert the enum constants
    EnumConstantAST[] constantASTs = enumDefinition.getConstants();
    EnumConstant[] constants = new EnumConstant[constantASTs.length];
    for (int i = 0; i < constantASTs.length; i++)
    {
      constants[i] = convert(constantASTs[i]);
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
        MemberVariable[] memberVariables = convert((FieldAST) memberAST, conceptualEnum);
        for (MemberVariable variable : memberVariables)
        {
          variables.add(variable);
        }
      }
      else if (memberAST instanceof PropertyAST)
      {
        properties.add(convert((PropertyAST) memberAST, conceptualEnum));
      }
      else if (memberAST instanceof StaticInitializerAST)
      {
        // do nothing, as the static initializer contains no information that we need to convert right now
        // the contained statements will be converted when the scope hierarchy has been built
      }
      else if (memberAST instanceof ConstructorAST)
      {
        constructors.add(convert((ConstructorAST) memberAST, conceptualEnum));
      }
      else if (memberAST instanceof MethodAST)
      {
        methods.add(convert((MethodAST) memberAST, conceptualEnum));
      }
      else if (memberAST instanceof ClassDefinitionAST)
      {
        innerClasses.add(convertInnerClass((ClassDefinitionAST) memberAST, conceptualEnum));
      }
      else if (memberAST instanceof InterfaceDefinitionAST)
      {
        innerInterfaces.add(convertInnerInterface((InterfaceDefinitionAST) memberAST, conceptualEnum));
      }
      else if (memberAST instanceof EnumDefinitionAST)
      {
        innerEnums.add(convertInnerEnum((EnumDefinitionAST) memberAST, conceptualEnum));
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
  }

  /**
   * Converts the specified EnumConstantAST into an EnumConstant.
   * @param enumConstantAST - the EnumConstantAST to convert
   * @param enclosingEnum - the enum to make the parent of the new enum constant
   * @return the EnumConstant created
   */
  private EnumConstant convert(EnumConstantAST enumConstantAST)
  {
    EnumConstant constant = new EnumConstant(enumConstantAST.getName().getName());
    conceptualASTNodes.put(constant, enumConstantAST);
    return constant;
  }

  /**
   * Converts the specified TypeParameterAST into a TypeParameter.
   * @param typeParameterAST - the TypeParameterAST to convert
   * @return the TypeParameter created
   */
  private TypeParameter convert(TypeParameterAST typeParameterAST)
  {
    return new TypeParameter(typeParameterAST.getName().getName());
  }

  /**
   * Converts the specified FieldAST into an array of MemberVariables.
   * @param fieldAST - the FieldAST to convert
   * @param enclosingTypeDefinition - the type definition to make the parent of the new member variables
   * @return the MemberVariables created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private MemberVariable[] convert(FieldAST fieldAST, TypeDefinition enclosingTypeDefinition) throws ConceptualException
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
    for (int i = 0; i < assignees.length; i++)
    {
      memberVariables[i] = new MemberVariable(enclosingTypeDefinition, accessSpecifier, isFinal, isMutable, isStatic,
                                              isVolatile, isTransient, sinceSpecifier, assignees[i].getName().getName());
      conceptualASTNodes.put(memberVariables[i], assignees[i]);
    }
    return memberVariables;
  }

  /**
   * Converts the specified PropertyAST into a Property
   * @param propertyAST - the PropertyAST to convert
   * @param enclosingTypeDefinition - the enclosing type definition
   * @return the Property created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private Property convert(PropertyAST propertyAST, TypeDefinition enclosingTypeDefinition) throws ConceptualException
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

    AccessSpecifier retrieveAccessSpecifier = convert(propertyAST.getGetterAccess(), AccessSpecifier.PUBLIC);
    AccessSpecifier assignAccessSpecifier = convert(propertyAST.getSetterAccess(), AccessSpecifier.PUBLIC);

    Property property = new Property(enclosingTypeDefinition, isSealed, isMutable, isFinal, isStatic, isSynchronized, isTransient, isVolatile,
                                     sinceSpecifier, propertyAST.getName().getName(), retrieveAccessSpecifier, assignAccessSpecifier);

    conceptualASTNodes.put(property, propertyAST);
    return property;
  }

  /**
   * Converts the specified ConstructorAST into a Constructor
   * @param constructorAST - the ConstructorAST to convert
   * @param enclosingTypeDefinition - the enclosing type defintion
   * @return the Constructor created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private Constructor convert(ConstructorAST constructorAST, TypeDefinition enclosingTypeDefinition) throws ConceptualException
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

    Constructor constructor = new Constructor(enclosingTypeDefinition, accessSpecifier, sinceSpecifier, constructorAST.getName().getName());

    conceptualASTNodes.put(constructor, constructorAST);
    return constructor;
  }

  /**
   * Converts the specified MethodAST into a Method
   * @param methodAST - the MethodAST to convert
   * @param enclosingTypeDefinition - the enclosing type definition
   * @return the Method created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private Method convert(MethodAST methodAST, TypeDefinition enclosingTypeDefinition) throws ConceptualException
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

    Method method = new Method(enclosingTypeDefinition, accessSpecifier, isAbstract, isSealed, isStatic, isSynchronized,
                               isImmutable, sinceSpecifier, nativeSpecifier, methodAST.getName().getName());

    conceptualASTNodes.put(method, methodAST);
    return method;
  }

  /**
   * Converts the specified ClassDefinitionAST into an InnerClass
   * @param classDefinitionAST - the ClassDefinitionAST to convert
   * @param enclosingTypeDefinition - the enclosing type definition
   * @return the InnerClass created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private InnerClass convertInnerClass(ClassDefinitionAST classDefinitionAST, TypeDefinition enclosingTypeDefinition) throws ConceptualException
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
    InnerClass innerClass = new InnerClass(enclosingTypeDefinition, isStatic, access, isAbstract, isSealed, isImmutable, sinceSpecifier, classDefinitionAST.getName().getName());

    addClassData(innerClass, classDefinitionAST);

    conceptualASTNodes.put(innerClass, classDefinitionAST);
    return innerClass;

  }

  /**
   * Converts the specified InterfaceDefinitionAST into a ConceptualInterface
   * @param interfaceDefinition - the InterfaceDefinitionAST to convert
   * @param enclosingTypeDefinition - the enclosing type definition
   * @return the InnerInterface created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private InnerInterface convertInnerInterface(InterfaceDefinitionAST interfaceDefinition, TypeDefinition enclosingTypeDefinition) throws ConceptualException
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

    InnerInterface innerInterface = new InnerInterface(enclosingTypeDefinition, access, isImmutable, sinceSpecifier, interfaceDefinition.getName().getName());

    addInterfaceData(innerInterface, interfaceDefinition);

    conceptualASTNodes.put(innerInterface, interfaceDefinition);
    return innerInterface;
  }

  /**
   * Converts the specified EnumDefinitionAST into a ConceptualEnum
   * @param enumDefinitionAST - the EnumDefinitionAST to convert
   * @param enclosingTypeDefinition - the enclosing type definition
   * @return the ConceptualEnum created
   * @throws ConceptualException - if there is a problem with the conversion
   */
  private InnerEnum convertInnerEnum(EnumDefinitionAST enumDefinition, TypeDefinition enclosingTypeDefinition) throws ConceptualException
  {
    AccessSpecifier access = convert(enumDefinition.getAccessSpecifier(), AccessSpecifier.PUBLIC);
    if (access != AccessSpecifier.PUBLIC && access != AccessSpecifier.PACKAGE)
    {
      // the getParseInfo() call here will not throw a NullPointerException because if enumDefinition.getAccessSpecifier() was null then access would be PUBLIC
      throw new ConceptualException("Invalid access specifier for an enum.", enumDefinition.getAccessSpecifier().getParseInfo());
    }
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

    InnerEnum innerEnum = new InnerEnum(enclosingTypeDefinition, access, sinceSpecifier, enumDefinition.getName().getName());

    addEnumData(innerEnum, enumDefinition);

    conceptualASTNodes.put(innerEnum, enumDefinition);
    return innerEnum;
  }

  /**
   * Converts the specified AccessSpecifierAST into an AccessSpecifier
   * @param accessSpecifierAST - the AccessSpecifierAST to convert
   * @param defaultValue - the default value to return if the AccessSpecifierAST is null
   * @return the AccessSpecifier converted
   */
  private AccessSpecifier convert(AccessSpecifierAST accessSpecifierAST, AccessSpecifier defaultValue)
  {
    if (accessSpecifierAST == null)
    {
      return defaultValue;
    }
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

  // TODO: is this the right place to put the following methods? and should they be static? (they are called from NameResolver)

  /**
   * Converts the specified TypeParameterAST into a TypeParameter.
   * @param typeParameterAST - the TypeParameterAST to convert
   * @param nameResolver - the NameResolver to use to resolve any names (e.g. in super types)
   * @param startScope - the starting scope to lookup names in
   * @return the converted TypeParameter
   * @throws NameConflictException - if a name conflict is detected while looking up any names
   * @throws ConceptualException - if a conceptual problem occurs while converting this type parameter
   * @throws UnresolvableException - if further initialisation must be done before it can be known whether one of the names can be resolved
   */
  private static TypeParameter convert(TypeParameterAST typeParameterAST, NameResolver nameResolver, Resolvable startScope) throws NameConflictException, ConceptualException, UnresolvableException
  {
    TypeParameter typeParameter = new TypeParameter(typeParameterAST.getName().getName());
    PointerTypeAST[] superTypeASTs = typeParameterAST.getSuperTypes();
    PointerTypeAST[] subTypeASTs = typeParameterAST.getSubTypes();
    PointerType[] superTypes = new PointerType[superTypeASTs.length];
    for (int i = 0; i < superTypeASTs.length; i++)
    {
      superTypes[i] = nameResolver.resolvePointerType(superTypeASTs[i], startScope);
    }
    PointerType[] subTypes = new PointerType[subTypeASTs.length];
    for (int i = 0; i < subTypeASTs.length; i++)
    {
      subTypes[i] = nameResolver.resolvePointerType(subTypeASTs[i], startScope);
    }
    typeParameter.setSuperTypes(superTypes);
    typeParameter.setSubTypes(subTypes);
    return typeParameter;
  }

  /**
   * Converts the specified TypeArgumentASTs into TypeArguments.
   * @param typeArgumentASTs - the TypeArgumentASTs to convert
   * @param nameResolver - the NameResolver to use to resolve any names
   * @param startScope - the starting scope to resolve the names from
   * @return the converted TypeArguments
   * @throws NameConflictException - if a name conflict is detected while looking up any names
   * @throws ConceptualException - if a conceptual problem occurs while resolving the type arguments
   * @throws UnresolvableException - if further initialisation must be done before it can be known whether one of the names can be resolved
   */
  public static TypeArgument[] convert(TypeArgumentAST[] typeArgumentASTs, NameResolver nameResolver, Resolvable startScope) throws NameConflictException, ConceptualException, UnresolvableException
  {
    if (typeArgumentASTs == null)
    {
      return null;
    }
    TypeArgument[] typeArguments = new TypeArgument[typeArgumentASTs.length];
    for (int i = 0; i < typeArgumentASTs.length; i++)
    {
      if (typeArgumentASTs[i] instanceof NormalTypeArgumentAST)
      {
        TypeAST typeAST = ((NormalTypeArgumentAST) typeArgumentASTs[i]).getType();
        typeArguments[i] = new NormalTypeArgument(ASTConverter.convert(typeAST, nameResolver, startScope));
      }
      else if (typeArgumentASTs[i] instanceof WildcardTypeArgumentAST)
      {
        WildcardTypeArgumentAST wildcardTypeArgumentAST = (WildcardTypeArgumentAST) typeArgumentASTs[i];
        PointerTypeAST[] superTypeASTs = wildcardTypeArgumentAST.getSuperTypes();
        PointerTypeAST[] subTypeASTs = wildcardTypeArgumentAST.getSubTypes();
        PointerType[] superTypes = new PointerType[superTypeASTs.length];
        for (int j = 0; j < superTypeASTs.length; j++)
        {
          superTypes[j] = nameResolver.resolvePointerType(superTypeASTs[j], startScope);
        }
        PointerType[] subTypes = new PointerType[subTypeASTs.length];
        for (int j = 0; j < subTypeASTs.length; j++)
        {
          subTypes[j] = nameResolver.resolvePointerType(subTypeASTs[j], startScope);
        }
        typeArguments[i] = new WildcardTypeArgument(superTypes, subTypes);
      }
      else
      {
        throw new IllegalArgumentException("Only normal and wildcard type arguments can be resolved");
      }
    }
    return typeArguments;
  }

  /**
   * Converts the specified TypeAST into a Type
   * @param typeAST - the TypeAST to convert
   * @param nameResolver - the NameResolver to use to resolve any names (e.g. in PointerTypeASTs)
   * @param startScope - the starting scope to lookup names in
   * @return the converted Type
   * @throws NameConflictException - if a name conflict is detected while looking up any names
   * @throws ConceptualException - if a conceptual problem occurs while converting this type
   * @throws UnresolvableException - if further initialisation must be done before it can be known whether one of the names can be resolved
   */
  public static Type convert(TypeAST typeAST, NameResolver nameResolver, Resolvable startScope) throws NameConflictException, ConceptualException, UnresolvableException
  {
    if (typeAST instanceof ArrayTypeAST)
    {
      ArrayTypeAST arrayTypeAST = (ArrayTypeAST) typeAST;
      return new ArrayType(convert(arrayTypeAST.getBaseType(), nameResolver, startScope), arrayTypeAST.isImmutable());
    }
    if (typeAST instanceof ClosureTypeAST)
    {
      ClosureTypeAST closureTypeAST = (ClosureTypeAST) typeAST;
      TypeParameterAST[] typeParameterASTs = closureTypeAST.getTypeParameters();
      TypeAST[] argumentTypeASTs = closureTypeAST.getArgumentTypes();
      TypeAST[] resultTypeASTs = closureTypeAST.getResultTypes();
      PointerTypeAST[] exceptionTypeASTs = closureTypeAST.getExceptionTypes();
      TypeParameter[] typeParameters = new TypeParameter[typeParameterASTs.length];
      for (int i = 0; i < typeParameterASTs.length; i++)
      {
        typeParameters[i] = convert(typeParameterASTs[i], nameResolver, startScope);
      }
      Type[] argumentTypes = new Type[argumentTypeASTs.length];
      for (int i = 0; i < argumentTypeASTs.length; i++)
      {
        argumentTypes[i] = convert(argumentTypeASTs[i], nameResolver, startScope);
      }
      Type[] resultTypes = new Type[resultTypeASTs.length];
      for (int i = 0; i < resultTypeASTs.length; i++)
      {
        resultTypes[i] = convert(resultTypeASTs[i], nameResolver, startScope);
      }
      PointerType[] exceptionTypes = new PointerType[exceptionTypeASTs.length];
      for (int i = 0; i < exceptionTypeASTs.length; i++)
      {
        exceptionTypes[i] = nameResolver.resolvePointerType(exceptionTypeASTs[i], startScope);
      }
      return new ClosureType(typeParameters, argumentTypes, resultTypes, exceptionTypes);
    }
    if (typeAST instanceof PointerTypeAST)
    {
      return nameResolver.resolvePointerType((PointerTypeAST) typeAST, startScope);
    }
    if (typeAST instanceof BooleanTypeAST)
    {
      return PrimitiveType.BOOLEAN;
    }
    if (typeAST instanceof CharacterTypeAST)
    {
      return PrimitiveType.CHARACTER;
    }
    if (typeAST instanceof FloatingTypeAST)
    {
      if (((FloatingTypeAST) typeAST).getTypeLength() == FloatingTypeLengthAST.DOUBLE)
      {
        return PrimitiveType.DOUBLE;
      }
      return PrimitiveType.FLOAT;
    }
    if (typeAST instanceof IntegerTypeAST)
    {
      Boolean signed = ((IntegerTypeAST) typeAST).getSigned();
      switch (((IntegerTypeAST) typeAST).getTypeLength())
      {
      case BYTE:
        if (signed != null && signed)
        {
          return PrimitiveType.SIGNED_BYTE;
        }
        return PrimitiveType.UNSIGNED_BYTE;
      case SHORT:
        if (signed == null || signed)
        {
          return PrimitiveType.SIGNED_SHORT;
        }
        return PrimitiveType.UNSIGNED_SHORT;
      case INT:
        if (signed == null || signed)
        {
          return PrimitiveType.SIGNED_INT;
        }
        return PrimitiveType.UNSIGNED_INT;
      case LONG:
        if (signed == null || signed)
        {
          return PrimitiveType.SIGNED_LONG;
        }
        return PrimitiveType.UNSIGNED_LONG;
      }
    }
    if (typeAST instanceof TupleTypeAST)
    {
      TypeAST[] subTypeASTs = ((TupleTypeAST) typeAST).getTypes();
      Type[] subTypes = new Type[subTypeASTs.length];
      for (int i = 0; i < subTypeASTs.length; i++)
      {
        subTypes[i] = convert(subTypeASTs[i], nameResolver, startScope);
      }
      return new TupleType(subTypes);
    }
    if (typeAST instanceof VoidTypeAST)
    {
      return PrimitiveType.VOID;
    }
    throw new IllegalArgumentException();
  }

  /**
   * Converts the specified array of NameASTs into a QName
   * @param names - the NameAST[] to convert
   * @return the QName created
   */
  public static QName convert(NameAST[] names)
  {
    String[] nameStrings = new String[names.length];
    for (int i = 0; i < names.length; i++)
    {
      nameStrings[i] = names[i].getName();
    }
    return new QName(nameStrings);
  }

}

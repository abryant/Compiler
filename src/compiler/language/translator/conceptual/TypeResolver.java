package compiler.language.translator.conceptual;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.member.ConstructorAST;
import compiler.language.ast.member.MethodAST;
import compiler.language.ast.member.PropertyAST;
import compiler.language.ast.misc.DeclarationAssigneeAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.ast.typeDefinition.EnumDefinitionAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.UnresolvableException;
import compiler.language.conceptual.member.Constructor;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.misc.ParameterList;
import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.conceptual.topLevel.ConceptualPackage;
import compiler.language.conceptual.type.ClassPointerType;
import compiler.language.conceptual.type.EnumPointerType;
import compiler.language.conceptual.type.InterfacePointerType;
import compiler.language.conceptual.type.OuterClassPointerType;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.Type;
import compiler.language.conceptual.type.TypeArgument;
import compiler.language.conceptual.type.TypeParameter;
import compiler.language.conceptual.type.TypeParameterPointerType;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
import compiler.language.conceptual.typeDefinition.InnerClass;
import compiler.language.conceptual.typeDefinition.TypeDefinition;

/*
 * Created on 5 Jun 2011
 */

/**
 * @author Anthony Bryant
 */
public class TypeResolver
{

  private Map<Object, Object> conceptualASTNodes;

  private Queue<TypeDefinition> parentsToResolve = new LinkedList<TypeDefinition>();
  private Queue<TypeDefinition> typeBoundsToResolve = new LinkedList<TypeDefinition>();
  private Queue<TypeDefinition> membersToResolve = new LinkedList<TypeDefinition>();

  private static final QName UNIVERSAL_BASE_CLASS_QNAME = new QName(new String[] {"x", "Object"},
                                                                    new LexicalPhrase[] {new LexicalPhrase(1, "x.Object", 1, 2),
                                                                                         new LexicalPhrase(1, "x.Object", 3, 9)});
  private OuterClassPointerType universalBaseClass;

  private AccessSpecifierChecker accessSpecifierChecker;

  /**
   * Creates a new TypeResolver to resolve qualified names into types.
   * @param conceptualASTNodes - the mapping which stores the AST node of each conceptual node which has been converted
   */
  public TypeResolver(Map<Object, Object> conceptualASTNodes)
  {
    this.conceptualASTNodes = conceptualASTNodes;
  }

  /**
   * Represents the state of all of the queues, storing only the size of each of them.
   * This class serves as an an easy way of checking whether addFile() added something to a queue during a call to resolvePointerType()
   * @author Anthony Bryant
   */
  private class QueueState
  {
    private int parentsLength;
    private int typeBoundsLength;
    private int membersLength;

    private QueueState()
    {
      parentsLength    = parentsToResolve.size();
      typeBoundsLength = typeBoundsToResolve.size();
      membersLength    = membersToResolve.size();
    }

    private boolean hasChanged()
    {
      return parentsLength    != parentsToResolve.size()    ||
             typeBoundsLength != typeBoundsToResolve.size() ||
             membersLength    != membersToResolve.size();
    }
  }

  /**
   * Adds all of the type definitions in the specified ConceptualFile to this resolver's queues
   * @param file - the file to add the type definitions of
   */
  public void addFile(ConceptualFile file)
  {
    for (ConceptualClass conceptualClass : file.getClasses())
    {
      parentsToResolve.add(conceptualClass);
      addInnerTypes(conceptualClass.getInnerClasses(), conceptualClass.getInnerInterfaces(), conceptualClass.getInnerEnums());
    }
    for (ConceptualInterface conceptualInterface : file.getInterfaces())
    {
      parentsToResolve.add(conceptualInterface);
      addInnerTypes(conceptualInterface.getInnerClasses(), conceptualInterface.getInnerInterfaces(), conceptualInterface.getInnerEnums());
    }
    for (ConceptualEnum conceptualEnum : file.getEnums())
    {
      parentsToResolve.add(conceptualEnum);
      addInnerTypes(conceptualEnum.getInnerClasses(), conceptualEnum.getInnerInterfaces(), conceptualEnum.getInnerEnums());
    }
  }

  /**
   * Adds the specified inner types to the queues, along with their own inner types.
   * @param innerClasses - the inner classes to add
   * @param innerInterfaces - the inner interfaces to add
   * @param innerEnums - the inner enums to add
   */
  private void addInnerTypes(ConceptualClass[] innerClasses, ConceptualInterface[] innerInterfaces, ConceptualEnum[] innerEnums)
  {
    if (innerClasses != null)
    {
      for (ConceptualClass innerClass : innerClasses)
      {
        parentsToResolve.add(innerClass);
        addInnerTypes(innerClass.getInnerClasses(), innerClass.getInnerInterfaces(), innerClass.getInnerEnums());
      }
    }
    if (innerInterfaces != null)
    {
      for (ConceptualInterface innerInterface : innerInterfaces)
      {
        parentsToResolve.add(innerInterface);
        addInnerTypes(innerInterface.getInnerClasses(), innerInterface.getInnerInterfaces(), innerInterface.getInnerEnums());
      }
    }
    if (innerEnums != null)
    {
      for (ConceptualEnum innerEnum : innerEnums)
      {
        parentsToResolve.add(innerEnum);
        addInnerTypes(innerEnum.getInnerClasses(), innerEnum.getInnerInterfaces(), innerEnum.getInnerEnums());
      }
    }
  }

  /**
   * Resolves the universal base class, so that it can be used later in the place of missing base classes.
   * @param rootPackage - the root package to resolve the universal base class from
   */
  public void buildUniversalBaseClass(ConceptualPackage rootPackage)
  {
    Resolvable baseClass;
    try
    {
      baseClass = rootPackage.resolve(UNIVERSAL_BASE_CLASS_QNAME, false);
    }
    catch (NameConflictException e)
    {
      throw new IllegalStateException("Detected a name conflict while resolving the universal base class: " + UNIVERSAL_BASE_CLASS_QNAME, e);
    }
    catch (UnresolvableException e)
    {
      throw new IllegalStateException("Unable to resolve the universal base class: " + UNIVERSAL_BASE_CLASS_QNAME, e);
    }
    if (baseClass.getType() != ScopeType.OUTER_CLASS)
    {
      throw new IllegalStateException("Universal base class " + UNIVERSAL_BASE_CLASS_QNAME + " does not resolve to an outer class!");
    }
    universalBaseClass = new OuterClassPointerType((ConceptualClass) baseClass, null, false);
    accessSpecifierChecker = new AccessSpecifierChecker(universalBaseClass.getClassType());
  }

  /**
   * Resolves the specified PointerTypeAST into a ClassPointerType
   * @param pointerTypeAST - the PointerTypeAST to resolve
   * @param startScope - the starting scope
   * @return the ClassPointerType converted
   * @throws NameConflictException - if a name conflict is detected while resolving this pointer type
   * @throws ConceptualException - if a conceptual problem occurs while resolving the pointer type
   * @throws UnresolvableException - if further initialisation must be done before it can be known whether one of the names can be resolved
   */
  public ClassPointerType resolveClassPointerType(PointerTypeAST pointerTypeAST, Resolvable startScope) throws NameConflictException, ConceptualException, UnresolvableException
  {
    PointerType result = resolvePointerType(pointerTypeAST, startScope);
    if (!(result instanceof ClassPointerType))
    {
      throw new ConceptualException("This type should resolve to a class, but does not", pointerTypeAST.getLexicalPhrase());
    }
    return (ClassPointerType) result;
  }

  /**
   * Resolves the specified PointerTypeAST into an InterfacePointerType
   * @param pointerTypeAST - the PointerTypeAST to resolve
   * @param startScope - the starting scope
   * @return the InterfacePointerType converted
   * @throws NameConflictException - if a name conflict is detected while resolving this pointer type
   * @throws ConceptualException - if a conceptual problem occurs while resolving the pointer type
   * @throws UnresolvableException - if further initialisation must be done before it can be known whether one of the names can be resolved
   */
  public InterfacePointerType resolveInterfacePointerType(PointerTypeAST pointerTypeAST, Resolvable startScope) throws NameConflictException, ConceptualException, UnresolvableException
  {
    PointerType result = resolvePointerType(pointerTypeAST, startScope);
    if (!(result instanceof InterfacePointerType))
    {
      throw new ConceptualException("This type should resolve to an interface, but does not", pointerTypeAST.getLexicalPhrase());
    }
    return (InterfacePointerType) result;
  }

  /**
   * Resolves the specified PointerTypeAST into a PointerType
   * @param pointerTypeAST - the PointerTypeAST to resolve
   * @param startScope - the starting scope
   * @return the PointerType converted
   * @throws NameConflictException - if a name conflict is detected while resolving this pointer type
   * @throws ConceptualException - if a conceptual problem occurs while resolving the pointer type
   * @throws UnresolvableException - if further initialisation must be done before it can be known whether one of the names can be resolved
   */
  public PointerType resolvePointerType(PointerTypeAST pointerTypeAST, Resolvable startScope) throws NameConflictException, ConceptualException, UnresolvableException
  {
    // resolve the QName without taking the type argument lists into account
    QName qname = pointerTypeAST.getQualifiedName();
    Resolvable resolved = accessSpecifierChecker.resolve(qname, startScope);

    if (resolved == null)
    {
      throw new ConceptualException("Could not resolve PointerType", pointerTypeAST.getLexicalPhrase());
    }

    TypeArgumentAST[][] typeArgumentLists = pointerTypeAST.getTypeArgumentLists();
    TypeArgumentAST[] lastTypeArguments = typeArgumentLists[typeArgumentLists.length - 1];

    if (resolved.getType() == ScopeType.OUTER_CLASS ||
        resolved.getType() == ScopeType.OUTER_INTERFACE ||
        resolved.getType() == ScopeType.OUTER_ENUM ||
        resolved.getType() == ScopeType.INNER_INTERFACE ||
        resolved.getType() == ScopeType.INNER_ENUM ||
        resolved.getType() == ScopeType.TYPE_PARAMETER)
    {
      // make sure all but the last type argument list is null
      for (int i = 0; i < typeArgumentLists.length - 1; i++)
      {
        if (typeArgumentLists[i] != null)
        {
          throw new ConceptualException("Type arguments are not allowed on this name", pointerTypeAST.getQualifiedName().getLexicalPhrases()[i]);
        }
      }
      if (resolved.getType() == ScopeType.OUTER_ENUM ||
          resolved.getType() == ScopeType.INNER_ENUM)
      {
        // make sure the last type argument list is null, as enums do not have type parameters
        if (typeArgumentLists[typeArgumentLists.length - 1] != null)
        {
          throw new ConceptualException("Type arguments are not allowed on this name", pointerTypeAST.getQualifiedName().getLexicalPhrases()[typeArgumentLists.length - 1]);
        }
        return new EnumPointerType((ConceptualEnum) resolved, pointerTypeAST.isImmutable());
      }
      if (resolved.getType() == ScopeType.TYPE_PARAMETER)
      {
        // make sure the last type argument list is null, as type parameters do not have type parameters of their own
        if (typeArgumentLists[typeArgumentLists.length - 1] != null)
        {
          throw new ConceptualException("Type arguments are not allowed on this name", pointerTypeAST.getQualifiedName().getLexicalPhrases()[typeArgumentLists.length - 1]);
        }
        return new TypeParameterPointerType((TypeParameter) resolved, pointerTypeAST.isImmutable());
      }

      // the type definition can accept type arguments, so check that they correspond to the type parameters
      TypeArgument[] typeArguments = null;
      TypeParameter[] typeParameters = null;
      if (resolved.getType() == ScopeType.OUTER_CLASS)
      {
        typeParameters = ((ConceptualClass) resolved).getTypeParameters();
      }
      else // ScopeType.OUTER_INTERFACE or ScopeType.INNER_INTERFACE:
      {
        typeParameters = ((ConceptualInterface) resolved).getTypeParameters();
      }
      if (typeParameters != null && typeParameters.length > 0)
      {
        if (lastTypeArguments == null || lastTypeArguments.length != typeParameters.length)
        {
          throw new ConceptualException("Expected " + typeParameters.length + " type argument" + (typeParameters.length != 1 ? "s" : "") + " after this name, " +
                                        "but found " + (lastTypeArguments == null ? "none" : lastTypeArguments.length),
                                        pointerTypeAST.getQualifiedName().getLexicalPhrases()[typeArgumentLists.length - 1]);
        }
        typeArguments = ASTConverter.convert(lastTypeArguments, this, startScope);
      }

      if (resolved.getType() == ScopeType.OUTER_CLASS)
      {
        return new OuterClassPointerType((ConceptualClass) resolved, typeArguments, pointerTypeAST.isImmutable());
      }
      // ScopeType.OUTER_INTERFACE or ScopeType.INNER_INTERFACE:
      return new InterfacePointerType((ConceptualInterface) resolved, typeArguments, pointerTypeAST.isImmutable());
    }
    else if (resolved.getType() != ScopeType.INNER_CLASS)
    {
      throw new ConceptualException("Cannot refer to a " + resolved.getType() + " as a pointer type", pointerTypeAST.getLexicalPhrase());
    }

    // we have an inner class, so find the type arguments for it
    ConceptualClass currentClass = (ConceptualClass) resolved;
    LinkedList<ConceptualClass> classes = new LinkedList<ConceptualClass>();
    LinkedList<TypeArgument[]> typeArguments = new LinkedList<TypeArgument[]>();
    while (true)
    {
      classes.addFirst(currentClass);
      if (currentClass.getTypeParameters() == null || currentClass.getTypeParameters().length == 0)
      {
        // this class does not need type arguments
        typeArguments.addFirst(null);
      }
      else if (typeArgumentLists.length >= classes.size())
      {
        // get the type arguments from the PointerTypeAST
        TypeArgumentAST[] arguments = typeArgumentLists[typeArgumentLists.length - classes.size()];
        if (arguments == null || arguments.length != currentClass.getTypeParameters().length)
        {
          int parameters = currentClass.getTypeParameters().length;
          throw new ConceptualException("Expected " + parameters + " type argument" + (parameters != 1 ? "s" : "") + " after this name, " +
                                        "but found " + (arguments == null ? "none" : arguments.length),
                                        pointerTypeAST.getQualifiedName().getLexicalPhrases()[typeArgumentLists.length - classes.size()]);
        }
        typeArguments.addFirst(ASTConverter.convert(arguments, this, startScope));
      }
      else
      {
        // TODO: resolve the missing type arguments from elsewhere, e.g. the current class
        // TODO: this will require implicitly generating some TypeParameterPointerTypes, so that the user can do:
        /*
         * class X<A> {
         *   class Y {}
         *   Y y;      // this
         *   X<A>.Y y; // instead of this
         * }
         */
        // TODO: there may also be other situations where missing type arguments can be filled in from elsewhere
        throw new ConceptualException("Not enough type arguments were provided to resolve this pointer type", pointerTypeAST.getLexicalPhrase());
      }

      // find out whether or not we need to progress to the parent type
      // never progress if we are not a non-static inner class
      if (currentClass.getType() != ScopeType.INNER_CLASS || ((InnerClass) currentClass).isStatic())
      {
        break;
      }
      TypeDefinition parent = ((InnerClass) currentClass).getParent();
      // never progress if the parent is not a class
      if (parent.getType() != ScopeType.OUTER_CLASS && parent.getType() != ScopeType.INNER_CLASS)
      {
        break;
      }
      currentClass = (ConceptualClass) parent;
    }

    return new ClassPointerType(classes.toArray(new ConceptualClass[classes.size()]),
                                typeArguments.toArray(new TypeArgument[typeArguments.size()][]),
                                pointerTypeAST.isImmutable());
  }

  /**
   * @return true if this resolver has no more data to resolve from any interfaces, classes or enums; false otherwise
   */
  public boolean finishedProcessing()
  {
    return parentsToResolve.isEmpty() && typeBoundsToResolve.isEmpty() && membersToResolve.isEmpty();
  }

  /**
   * @return true if this resolver has more type definitions to resolve the parents of, false otherwise
   */
  public boolean hasUnresolvedTypeParents()
  {
    return !parentsToResolve.isEmpty();
  }

  /**
   * @return true if this resolver has more type definitions to resolve the type parameter bounds of, false otherwise
   */
  public boolean hasUnresolvedTypeParameterBounds()
  {
    return !typeBoundsToResolve.isEmpty();
  }

  /**
   * @return true if this resolver has more type definitions to resolve the members of, false otherwise
   */
  public boolean hasUnresolvedMembers()
  {
    return !membersToResolve.isEmpty();
  }

  /**
   * Resolves the parent types of all type definitions in the parentsToResolve queue.
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws ConceptualException - if a conceptual problem is detected while resolving parent types
   * @throws NameConflictException - if a name conflict is detected while resolving a PointerType
   */
  public boolean resolveTypeParents(Set<LexicalPhrase> unresolvedLexicalPhrases) throws ConceptualException, NameConflictException
  {
    boolean changed = false;
    Set<TypeDefinition> notFullyResolved = new HashSet<TypeDefinition>();

    // try to resolve the parent interfaces of every type definition
    // this uses a queue instead of an iterator because new type definitions can be added to the list while we are working on it
    // (e.g. if a class extends a class which has not been parsed yet)
    while (!parentsToResolve.isEmpty())
    {
      TypeDefinition toResolve = parentsToResolve.poll();
      if (notFullyResolved.contains(toResolve))
      {
        // all of the types in the queue have been processed since a change has been made
        // (this depends on typesToResolve being a queue)
        return changed;
      }

      ScopeType type = toResolve.getType();
      if (type == ScopeType.OUTER_CLASS || type == ScopeType.INNER_CLASS)
      {
        changed |= resolveClassParents((ConceptualClass) toResolve, unresolvedLexicalPhrases, notFullyResolved);
      }
      else if (type == ScopeType.OUTER_INTERFACE || type == ScopeType.INNER_INTERFACE)
      {
        changed |= resolveInterfaceParents((ConceptualInterface) toResolve, unresolvedLexicalPhrases, notFullyResolved);
      }
      else if (type == ScopeType.OUTER_ENUM || type == ScopeType.INNER_ENUM)
      {
        changed |= resolveEnumParents((ConceptualEnum) toResolve, unresolvedLexicalPhrases, notFullyResolved);
      }
      else
      {
        throw new IllegalStateException("Unrecognised type definition: " + type);
      }
    }

    return changed;
  }

  /**
   * Attempts to resolve the parent types of the specified ConceptualClass.
   * If not all of the parents are resolved, then the specified class is added to notFullyResolved,
   * and is put back on the end of the parentsToResolve queue.
   * The notFullyResolved and unresolvedLexicalPhrases sets are both cleared whenever a change is made.
   * @param toResolve - the ConceptualClass to resolve the parent types of
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made.
   * @param notFullyResolved - the set of types which have parent types which failed to resolve since the last change was made
   * @return true if any changes were made, false otherwise
   * @throws ConceptualException - if a conceptual problem is detected while resolving the parent types
   * @throws NameConflictException - if a name conflict is detected while resolving the parent types
   */
  private boolean resolveClassParents(ConceptualClass toResolve, Set<LexicalPhrase> unresolvedLexicalPhrases, Set<TypeDefinition> notFullyResolved) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    ClassDefinitionAST astNode = (ClassDefinitionAST) conceptualASTNodes.get(toResolve);

    boolean fullyResolved = true;
    PointerTypeAST baseClassAST = astNode.getBaseClass();
    ClassPointerType baseClass = toResolve.getBaseClass();
    // skip the universal base class, as its base class is the only one that should be null
    if (baseClass == null && !universalBaseClass.getClassType().equals(toResolve))
    {
      if (baseClassAST == null)
      {
        baseClass = universalBaseClass;
        toResolve.setBaseClass(baseClass);
        changed = true;
        notFullyResolved.clear();
        unresolvedLexicalPhrases.clear();
      }
      else
      {
        QueueState queueState = new QueueState();
        try
        {
          baseClass = resolveClassPointerType(baseClassAST, toResolve);
          checkParentClass(baseClass, baseClassAST, toResolve);
          toResolve.setBaseClass(baseClass);
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
        catch (UnresolvableException e)
        {
          fullyResolved = false;
          unresolvedLexicalPhrases.add(baseClassAST.getLexicalPhrase());

          if (queueState.hasChanged())
          {
            // one of the queues was modified during the call to resolvePointerType(),
            // because a new file was loaded and addFile() was called.
            // this must count as a change, for reasons described above
            changed = true;
            notFullyResolved.clear();
            unresolvedLexicalPhrases.clear();
          }
        }
      }
    }

    PointerTypeAST[] parentInterfaceASTs = astNode.getInterfaces();
    InterfacePointerType[] parentInterfaces = toResolve.getInterfaces();
    if (parentInterfaces == null)
    {
      parentInterfaces = new InterfacePointerType[parentInterfaceASTs.length];
      toResolve.setInterfaces(parentInterfaces);
    }
    if (parentInterfaceASTs.length != parentInterfaces.length)
    {
      throw new IllegalStateException("Invalid length of parent interfaces array");
    }
    for (int i = 0; i < parentInterfaces.length; i++)
    {
      if (parentInterfaces[i] != null)
      {
        continue;
      }
      QueueState queueState = new QueueState();
      try
      {
        InterfacePointerType parentInterface = resolveInterfacePointerType(parentInterfaceASTs[i], toResolve);
        checkParentInterface(parentInterface, parentInterfaceASTs[i], null);
        parentInterfaces[i] = parentInterface;
        changed = true;
        notFullyResolved.clear();
        unresolvedLexicalPhrases.clear();
      }
      catch (UnresolvableException e)
      {
        // leave parentInterfaces[i] as null
        fullyResolved = false;
        unresolvedLexicalPhrases.add(parentInterfaceASTs[i].getLexicalPhrase());

        if (queueState.hasChanged())
        {
          // one of the queues was modified during the call to resolvePointerType(),
          // because a new file was loaded and addFile() was called.
          // this must count as a change, for reasons described above
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
    }
    if (fullyResolved)
    {
      // we have removed something from the queue, so a change has occurred
      changed = true;
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
      // add the class to the next queue, to resolve its type parameters' bounds
      typeBoundsToResolve.add(toResolve);
    }
    else
    {
      // some parent classes/interfaces still need filling in, so add this to the end of the queue again
      notFullyResolved.add(toResolve);
      parentsToResolve.add(toResolve);
    }

    return changed;
  }

  /**
   * Attempts to resolve the parent types of the specified ConceptualInterface.
   * If not all of the parents are resolved, then the specified interface is added to notFullyResolved,
   * and is put back on the end of the parentsToResolve queue.
   * The notFullyResolved and unresolvedLexicalPhrases sets are both cleared whenever a change is made.
   * @param toResolve - the ConceptualInterface to resolve the parent types of
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made.
   * @param notFullyResolved - the set of types which have parent types which failed to resolve since the last change was made
   * @return true if any changes were made, false otherwise
   * @throws ConceptualException - if a conceptual problem is detected while resolving the parent types
   * @throws NameConflictException - if a name conflict is detected while resolving the parent types
   */
  private boolean resolveInterfaceParents(ConceptualInterface toResolve, Set<LexicalPhrase> unresolvedLexicalPhrases, Set<TypeDefinition> notFullyResolved) throws ConceptualException, NameConflictException
  {
    boolean changed = false;

    InterfaceDefinitionAST astNode = (InterfaceDefinitionAST) conceptualASTNodes.get(toResolve);
    PointerTypeAST[] parentInterfaces = astNode.getInterfaces();
    InterfacePointerType[] pointerTypes = toResolve.getSuperInterfaces();
    if (pointerTypes == null)
    {
      pointerTypes = new InterfacePointerType[parentInterfaces.length];
      toResolve.setSuperInterfaces(pointerTypes);
    }
    if (pointerTypes.length != parentInterfaces.length)
    {
      throw new IllegalStateException("Invalid length of parent interfaces array");
    }
    boolean fullyResolved = true;
    for (int i = 0; i < parentInterfaces.length; i++)
    {
      if (pointerTypes[i] != null)
      {
        continue;
      }
      QueueState queueState = new QueueState();
      try
      {
        InterfacePointerType parentPointerType = resolveInterfacePointerType(parentInterfaces[i], toResolve);
        checkParentInterface(parentPointerType, parentInterfaces[i], toResolve);
        pointerTypes[i] = parentPointerType;
        changed = true;
        notFullyResolved.clear();
        unresolvedLexicalPhrases.clear();
      }
      catch (UnresolvableException e)
      {
        // leave pointerTypes[i] as null
        fullyResolved = false;
        unresolvedLexicalPhrases.add(parentInterfaces[i].getLexicalPhrase());

        if (queueState.hasChanged())
        {
          // one of the queues was modified during the call to resolvePointerType()
          // because a new file was loaded and addFile() was called

          // this must count as a change being made or some nasty edge cases crop up
          // for example (using classes for clarity):
          //   W has an inner class A, which has an inner class B, X extends W, Y extends X.A, Z extends Y.B
          //   initially, the queue contains only Z
          //   when the parents of Z are being resolved, Y is lazily parsed, but we cannot resolve B until Y is also resolved, so Y is added to the queue before Z is re-added
          //   when the parents of Y are being resolved, X is lazily parsed, but we cannot resolve A until X is also resolved, so X is added to the queue before Y is re-added
          //   Z reaches the front of the queue again, and no changes have been made, despite some progress being made, which would cause an "Unresolvable parent" exception to be thrown
          // to counter this, we count adding something to the queue as a change, and clear the unresolvedSinceLastChange set
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
    }
    if (fullyResolved)
    {
      // we have removed something from the queue, so a change has occurred
      changed = true;
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
      // add the interface to the next queue, to resolve its type parameters' bounds
      typeBoundsToResolve.add(toResolve);
    }
    else
    {
      // some parent interfaces still need filling in, so add this to the end of the queue again
      parentsToResolve.add(toResolve);
      notFullyResolved.add(toResolve);
    }

    return changed;
  }

  /**
   * Attempts to resolve the parent types of the specified ConceptualEnum.
   * If not all of the parents are resolved, then the specified enum is added to notFullyResolved,
   * and is put back on the end of the parentsToResolve queue.
   * The notFullyResolved and unresolvedLexicalPhrases sets are both cleared whenever a change is made.
   * @param toResolve - the ConceptualEnum to resolve the parent types of
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made.
   * @param notFullyResolved - the set of types which have parent types which failed to resolve since the last change was made
   * @return true if any changes were made, false otherwise
   * @throws ConceptualException - if a conceptual problem is detected while resolving the parent types
   * @throws NameConflictException - if a name conflict is detected while resolving the parent types
   */
  private boolean resolveEnumParents(ConceptualEnum toResolve, Set<LexicalPhrase> unresolvedLexicalPhrases, Set<TypeDefinition> notFullyResolved) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    boolean fullyResolved = true;
    EnumDefinitionAST astNode = (EnumDefinitionAST) conceptualASTNodes.get(toResolve);
    PointerTypeAST baseClassAST = astNode.getBaseClass();
    ClassPointerType baseClass = toResolve.getBaseClass();
    if (baseClass == null)
    {
      if (baseClassAST == null)
      {
        baseClass = universalBaseClass;
        toResolve.setBaseClass(baseClass);
        changed = true;
        notFullyResolved.clear();
        unresolvedLexicalPhrases.clear();
      }
      else
      {
        QueueState queueState = new QueueState();
        try
        {
          baseClass = resolveClassPointerType(baseClassAST, toResolve);
          toResolve.setBaseClass(baseClass);
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
        catch (UnresolvableException e)
        {
          fullyResolved = false;
          unresolvedLexicalPhrases.add(baseClassAST.getLexicalPhrase());

          if (queueState.hasChanged())
          {
            // one of the queues was modified during the call to resolvePointerType(),
            // because a new file was loaded and addFile() was called.
            // this must count as a change, for reasons described above
            changed = true;
            notFullyResolved.clear();
            unresolvedLexicalPhrases.clear();
          }
        }
      }
    }

    PointerTypeAST[] parentInterfaceASTs = astNode.getInterfaces();
    InterfacePointerType[] parentInterfaces = toResolve.getInterfaces();
    if (parentInterfaces == null)
    {
      parentInterfaces = new InterfacePointerType[parentInterfaceASTs.length];
      toResolve.setInterfaces(parentInterfaces);
    }
    if (parentInterfaceASTs.length != parentInterfaces.length)
    {
      throw new IllegalStateException("Invalid length of parent interfaces array");
    }
    for (int i = 0; i < parentInterfaces.length; i++)
    {
      if (parentInterfaces[i] != null)
      {
        continue;
      }
      QueueState queueState = new QueueState();
      try
      {
        InterfacePointerType parentInterface = resolveInterfacePointerType(parentInterfaceASTs[i], toResolve);
        checkParentInterface(parentInterface, parentInterfaceASTs[i], null);
        parentInterfaces[i] = parentInterface;
        changed = true;
        notFullyResolved.clear();
        unresolvedLexicalPhrases.clear();
      }
      catch (UnresolvableException e)
      {
        // leave parentInterfaces[i] as null
        fullyResolved = false;
        unresolvedLexicalPhrases.add(parentInterfaceASTs[i].getLexicalPhrase());

        if (queueState.hasChanged())
        {
          // one of the queues was modified during the call to resolvePointerType(),
          // because a new file was loaded and addFile() was called.
          // this must count as a change, for reasons described above
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
    }
    if (fullyResolved)
    {
      // we have removed something from the queue, so a change has occurred
      changed = true;
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
      // add this enum to the next queue, to resolve its member variables' types
      membersToResolve.add(toResolve);
    }
    else
    {
      // some parent classes/interfaces still need filling in, so add this to the end of the queue again
      notFullyResolved.add(toResolve);
      parentsToResolve.add(toResolve);
    }

    return changed;
  }

  /**
   * Checks the specified InterfacePointerType as being a valid parent interface.
   * If a child interface is provided, this method checks that the child will not become one of its own parents.
   * @param parent - the parent interface's PointerType, to check
   * @param parentAST - the parent type's PointerTypeAST, to extract LexicalPhrase from in case of error
   * @param child - the prospective child interface, if any
   * @throws ConceptualException - if there is a problem and the PointerType is not a valid parent interface
   */
  private void checkParentInterface(InterfacePointerType parent, PointerTypeAST parentAST, ConceptualInterface child) throws ConceptualException
  {
    if (child == null)
    {
      return;
    }

    // check that child is not parent or one of its parent interfaces, using a breadth first search
    ConceptualInterface startInterface = parent.getInterfaceType();

    Deque<ConceptualInterface> queue = new LinkedList<ConceptualInterface>();
    queue.add(startInterface);
    while (!queue.isEmpty())
    {
      ConceptualInterface current = queue.poll();
      if (child.equals(current))
      {
        throw new ConceptualException("Cycle detected in interface inheritance hierarchy", parentAST.getLexicalPhrase());
      }
      if (current.getSuperInterfaces() == null)
      {
        continue;
      }
      for (InterfacePointerType superType : current.getSuperInterfaces())
      {
        if (superType == null)
        {
          continue;
        }
        queue.add(superType.getInterfaceType());
      }
    }
  }

  /**
   * Checks the specified ClassPointerType as being a valid parent class.
   * If a child class is provided, this method checks that the child will not become one of its own parents.
   * @param parent - the parent class's ClassPointerType, to check
   * @param parentAST - the parent type's PointerTypeAST, to extract LexicalPhrase from in case of error
   * @param child - the prospective child class, if any
   * @throws ConceptualException - if there is a problem and the PointerType is not a valid parent class
   * @throws UnresolvableException - if a parent class cannot be resolved while checking that the parent class is valid
   */
  private void checkParentClass(ClassPointerType parent, PointerTypeAST parentAST, ConceptualClass child) throws ConceptualException, UnresolvableException
  {
    if (child == null)
    {
      return;
    }

    // check that child is not parent or one of its base classes
    ConceptualClass current = parent.getClassType();
    while (current != null)
    {
      if (current.equals(child))
      {
        throw new ConceptualException("Cycle detected in class inheritance hierarchy", parentAST.getLexicalPhrase());
      }
      ClassPointerType baseClass = current.getBaseClass();
      if (baseClass == null)
      {
        break;
      }
      current = baseClass.getClassType();
    }

    // check that if parent has any outer classes then child also has them
    if (parent.getClassType() instanceof InnerClass)
    {
      InnerClass parentClass = (InnerClass) parent.getClassType();
      TypeDefinition parentOuterType = parentClass.getParent();
      if (!parentClass.isStatic())
      {
        if (parentOuterType instanceof ConceptualClass)
        {
          // the parent class is not static and has an outer class, so the child must also be an inner class
          // and its outer class must be derived from the parent's outer class
          if (!(child instanceof InnerClass))
          {
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer class", parentAST.getLexicalPhrase());
          }
          InnerClass childClass = (InnerClass) child;
          TypeDefinition childOuterType = childClass.getParent();
          if (childClass.isStatic() || !(childOuterType instanceof ConceptualClass || childOuterType instanceof ConceptualEnum))
          {
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer class", parentAST.getLexicalPhrase());
          }
          ConceptualClass currentClass;
          if (childOuterType instanceof ConceptualEnum)
          {
            // take the base class of the enum as the current class
            ClassPointerType baseClass = ((ConceptualEnum) childOuterType).getBaseClass();
            if (baseClass == null)
            {
              // we have not reached the top of the inheritance hierarchy, so wait until more of the hierarchy has been resolved
              throw new UnresolvableException("Error checking the parents of an inner class", parentAST.getLexicalPhrase());
            }
            currentClass = baseClass.getClassType();
          }
          else
          {
            // childOuterType must be a ConceptualClass, so use it as currentClass
            currentClass = (ConceptualClass) childOuterType;
          }
          while (!currentClass.equals(parentOuterType))
          {
            ClassPointerType baseClass = currentClass.getBaseClass();
            if (baseClass == null)
            {
              if (!currentClass.equals(universalBaseClass.getClassType()))
              {
                // we have not reached the top of the inheritance hierarchy, so wait until more of the hierarchy has been resolved
                throw new UnresolvableException("Error checking the parents of an inner class", parentAST.getLexicalPhrase());
              }
              throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer class", parentAST.getLexicalPhrase());
            }
            currentClass = baseClass.getClassType();
          }
        }
        else if (parentOuterType instanceof ConceptualEnum)
        {
          // the parent class is not static and has an outer enum, so the child must also be an inner class
          // and its outer enum must be the same as the parent's outer enum
          if (!(child instanceof InnerClass))
          {
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer enum", parentAST.getLexicalPhrase());
          }
          InnerClass childClass = (InnerClass) child;
          TypeDefinition childOuterType = childClass.getParent();
          if (childClass.isStatic() || !(childOuterType instanceof ConceptualEnum))
          {
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer enum", parentAST.getLexicalPhrase());
          }
          if (!childOuterType.equals(parentOuterType))
          {
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer enum", parentAST.getLexicalPhrase());
          }
        }
      }
    }
  }

  /**
   * Resolves the type parameter bounds of all type definitions in the typeBoundsToResolve queue.
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws ConceptualException - if a conceptual problem is detected while resolving type parameter bounds
   * @throws NameConflictException - if a name conflict is detected while resolving a PointerType
   */
  public boolean resolveTypeParameterBounds(Set<LexicalPhrase> unresolvedLexicalPhrases) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    Set<TypeDefinition> notFullyResolved = new HashSet<TypeDefinition>();

    while (parentsToResolve.isEmpty() && !typeBoundsToResolve.isEmpty())
    {
      TypeDefinition toResolve = typeBoundsToResolve.poll();
      if (notFullyResolved.contains(toResolve))
      {
        // all of the type definitions in the queue have been processed since a change has been made
        // (this depends on typeBoundsToResolve being a queue)
        return changed;
      }

      ScopeType type = toResolve.getType();
      if (type == ScopeType.OUTER_CLASS || type == ScopeType.INNER_CLASS)
      {
        changed |= resolveClassTypeParameterBounds((ConceptualClass) toResolve, unresolvedLexicalPhrases, notFullyResolved);
      }
      else if (type == ScopeType.OUTER_INTERFACE || type == ScopeType.INNER_INTERFACE)
      {
        changed |= resolveInterfaceTypeParameterBounds((ConceptualInterface) toResolve, unresolvedLexicalPhrases, notFullyResolved);
      }
    }

    return changed;
  }

  /**
   * Attempts to resolve the type parameter bounds of the specified ConceptualClass. Both super-type and sub-type bounds are resolved.
   * If not all of the bounds are resolved, then the specified class is added to notFullyResolved,
   * and is put back on the end of the typeBoundsToResolve queue.
   * The notFullyResolved and unresolvedLexicalPhrases sets are both cleared whenever a change is made.
   * @param toResolve - the ConceptualClass to resolve the type parameter bounds of
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made.
   * @param notFullyResolved - the set of types which have bounds which failed to resolve since the last change was made
   * @return true if any changes were made, false otherwise
   * @throws NameConflictException - if a name conflict is detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem is detected while resolving the type parameter bounds
   */
  private boolean resolveClassTypeParameterBounds(ConceptualClass toResolve, Set<LexicalPhrase> unresolvedLexicalPhrases, Set<TypeDefinition> notFullyResolved) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    ClassDefinitionAST astNode = (ClassDefinitionAST) conceptualASTNodes.get(toResolve);
    TypeParameterAST[] parameterASTs = astNode.getTypeParameters();
    if (parameterASTs == null)
    {
      // the class has been removed from the queue, so this counts as a change
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
      return true;
    }
    TypeParameter[] parameters = toResolve.getTypeParameters();
    if (parameters == null)
    {
      parameters = new TypeParameter[parameterASTs.length];
      toResolve.setTypeParameters(parameters);
    }
    if (parameterASTs.length != parameters.length)
    {
      throw new IllegalStateException("Illegal length of type parameter array");
    }
    boolean fullyResolved = true;
    for (int i = 0; i < parameterASTs.length; i++)
    {
      // resolve the super- and sub-types of this type parameter
      PointerTypeAST[] superTypeASTs = parameterASTs[i].getSuperTypes();
      PointerTypeAST[] subTypeASTs = parameterASTs[i].getSubTypes();
      if (superTypeASTs != null)
      {
        PointerType[] superTypes = parameters[i].getSuperTypes();
        if (superTypes == null)
        {
          superTypes = new PointerType[superTypeASTs.length];
          parameters[i].setSuperTypes(superTypes);
        }
        for (int j = 0; j < superTypeASTs.length; j++)
        {
          if (superTypes[j] != null)
          {
            continue;
          }
          QueueState queueState = new QueueState();
          try
          {
            superTypes[j] = resolvePointerType(superTypeASTs[j], toResolve);
            changed = true;
            notFullyResolved.clear();
            unresolvedLexicalPhrases.clear();
          }
          catch (UnresolvableException e)
          {
            fullyResolved = false;
            unresolvedLexicalPhrases.add(superTypeASTs[j].getLexicalPhrase());

            if (queueState.hasChanged())
            {
              // one of the queues was modified during the call to resolvePointerType(),
              // because a new file was loaded and addFile() was called.
              // this must count as a change, for reasons described above
              changed = true;
              notFullyResolved.clear();
              unresolvedLexicalPhrases.clear();
            }
          }
        }
      }
      if (subTypeASTs != null)
      {
        PointerType[] subTypes = parameters[i].getSubTypes();
        if (subTypes == null)
        {
          subTypes = new PointerType[subTypeASTs.length];
          parameters[i].setSubTypes(subTypes);
        }
        for (int j = 0; j < subTypeASTs.length; j++)
        {
          if (subTypes[j] != null)
          {
            continue;
          }
          QueueState queueState = new QueueState();
          try
          {
            subTypes[j] = resolvePointerType(subTypeASTs[j], toResolve);
            changed = true;
            notFullyResolved.clear();
            unresolvedLexicalPhrases.clear();
          }
          catch (UnresolvableException e)
          {
            fullyResolved = false;
            unresolvedLexicalPhrases.add(subTypeASTs[j].getLexicalPhrase());

            if (queueState.hasChanged())
            {
              // one of the queues was modified during the call to resolvePointerType(),
              // because a new file was loaded and addFile() was called.
              // this must count as a change, for reasons described above
              changed = true;
              notFullyResolved.clear();
              unresolvedLexicalPhrases.clear();
            }
          }
        }
      }
    }

    if (fullyResolved)
    {
      // we have removed something from the queue, so a change has occurred
      changed = true;
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
      // add this class to the next queue, to resolve its member variables' types
      membersToResolve.add(toResolve);
    }
    else
    {
      // some type parameter bounds still need filling in, so add this to the end of the queue again
      typeBoundsToResolve.add(toResolve);
      notFullyResolved.add(toResolve);
    }
    return changed;
  }

  /**
   * Attempts to resolve the type parameter bounds of the specified ConceptualInterface. Both super-type and sub-type bounds are resolved.
   * If not all of the bounds are resolved, then the specified interface is added to notFullyResolved,
   * and is put back on the end of the typeBoundsToResolve queue.
   * The notFullyResolved and unresolvedLexicalPhrases sets are both cleared whenever a change is made.
   * @param toResolve - the ConceptualInterface to resolve the type parameter bounds of
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made.
   * @param notFullyResolved - the set of types which have bounds which failed to resolve since the last change was made
   * @return true if any changes were made, false otherwise
   * @throws NameConflictException - if a name conflict is detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem is detected while resolving the type parameter bounds
   */
  private boolean resolveInterfaceTypeParameterBounds(ConceptualInterface toResolve, Set<LexicalPhrase> unresolvedLexicalPhrases, Set<TypeDefinition> notFullyResolved) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    InterfaceDefinitionAST astNode = (InterfaceDefinitionAST) conceptualASTNodes.get(toResolve);
    TypeParameterAST[] parameterASTs = astNode.getTypeParameters();
    if (parameterASTs == null)
    {
      // the interface has been removed from the queue, so this counts as a change
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
      return true;
    }
    TypeParameter[] parameters = toResolve.getTypeParameters();
    if (parameters == null)
    {
      parameters = new TypeParameter[parameterASTs.length];
      toResolve.setTypeParameters(parameters);
    }
    if (parameterASTs.length != parameters.length)
    {
      throw new IllegalStateException("Illegal length of type parameter array");
    }
    boolean fullyResolved = true;
    for (int i = 0; i < parameterASTs.length; i++)
    {
      // resolve the super- and sub-types of this type parameter
      PointerTypeAST[] superTypeASTs = parameterASTs[i].getSuperTypes();
      PointerTypeAST[] subTypeASTs = parameterASTs[i].getSubTypes();
      if (superTypeASTs != null)
      {
        PointerType[] superTypes = parameters[i].getSuperTypes();
        if (superTypes == null)
        {
          superTypes = new PointerType[superTypeASTs.length];
          parameters[i].setSuperTypes(superTypes);
        }
        for (int j = 0; j < superTypeASTs.length; j++)
        {
          if (superTypes[j] != null)
          {
            continue;
          }
          QueueState queueState = new QueueState();
          try
          {
            superTypes[j] = resolvePointerType(superTypeASTs[j], toResolve);
            changed = true;
            notFullyResolved.clear();
            unresolvedLexicalPhrases.clear();
          }
          catch (UnresolvableException e)
          {
            fullyResolved = false;
            unresolvedLexicalPhrases.add(superTypeASTs[j].getLexicalPhrase());

            if (queueState.hasChanged())
            {
              // one of the queues was modified during the call to resolvePointerType(),
              // because a new file was loaded and addFile() was called.
              // this must count as a change, for reasons described above
              changed = true;
              notFullyResolved.clear();
              unresolvedLexicalPhrases.clear();
            }
          }
        }
      }
      if (subTypeASTs != null)
      {
        PointerType[] subTypes = parameters[i].getSubTypes();
        if (subTypes == null)
        {
          subTypes = new PointerType[subTypeASTs.length];
          parameters[i].setSubTypes(subTypes);
        }
        for (int j = 0; j < subTypeASTs.length; j++)
        {
          if (subTypes[j] != null)
          {
            continue;
          }
          QueueState queueState = new QueueState();
          try
          {
            subTypes[j] = resolvePointerType(subTypeASTs[j], toResolve);
            changed = true;
            notFullyResolved.clear();
            unresolvedLexicalPhrases.clear();
          }
          catch (UnresolvableException e)
          {
            fullyResolved = false;
            unresolvedLexicalPhrases.add(subTypeASTs[j].getLexicalPhrase());

            if (queueState.hasChanged())
            {
              // one of the queues was modified during the call to resolvePointerType(),
              // because a new file was loaded and addFile() was called.
              // this must count as a change, for reasons described above
              changed = true;
              notFullyResolved.clear();
              unresolvedLexicalPhrases.clear();
            }
          }
        }
      }
    }

    if (fullyResolved)
    {
      // we have removed something from the queue, so a change has occurred
      changed = true;
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
      // add this interface to the next queue, to resolve its member variables' types
      membersToResolve.add(toResolve);
    }
    else
    {
      // some type parameter bounds still need filling in, so add this to the end of the queue again
      typeBoundsToResolve.add(toResolve);
      notFullyResolved.add(toResolve);
    }

    return changed;
  }

  /**
   * Resolves the members of all type definitions in the membersToResolve queue.
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws ConceptualException - if a conceptual problem is detected while resolving members
   * @throws NameConflictException - if a name conflict is detected while resolving a PointerType
   */
  public boolean resolveTypeMembers(Set<LexicalPhrase> unresolvedLexicalPhrases) throws ConceptualException, NameConflictException
  {
    boolean changed = false;
    Set<TypeDefinition> notFullyResolved = new HashSet<TypeDefinition>();

    while (parentsToResolve.isEmpty() && typeBoundsToResolve.isEmpty() && !membersToResolve.isEmpty())
    {
      TypeDefinition toResolve = membersToResolve.poll();
      if (notFullyResolved.contains(toResolve))
      {
        // all of the type definitions in the queue have been processed since a change has been made
        // (this depends on membersToResolve being a queue)
        return changed;
      }

      ScopeType type = toResolve.getType();
      if (type == ScopeType.OUTER_CLASS || type == ScopeType.INNER_CLASS)
      {
        changed |= resolveClassMembers((ConceptualClass) toResolve, unresolvedLexicalPhrases, notFullyResolved);
      }
      else if (type == ScopeType.OUTER_INTERFACE || type == ScopeType.INNER_INTERFACE)
      {
        changed |= resolveInterfaceMembers((ConceptualInterface) toResolve, unresolvedLexicalPhrases, notFullyResolved);
      }
      else if (type == ScopeType.OUTER_ENUM || type == ScopeType.INNER_ENUM)
      {
        changed |= resolveEnumMembers((ConceptualEnum) toResolve, unresolvedLexicalPhrases, notFullyResolved);
      }
    }
    return changed;
  }

  /**
   * Attempts to resolve the members of the specified ConceptualClass.
   * If not all member types are resolved, then the specified class is added to notFullyResolved,
   * and is put back on the end of the membersToResolve queue.
   * The notFullyResolved and unresolvedLexicalPhrases sets are both cleared whenever a change is made.
   * @param toResolve - the ConceptualClass to resolve the member types of
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made.
   * @param notFullyResolved - the set of types which have bounds which failed to resolve since the last change was made
   * @return true if any changes were made, false otherwise
   * @throws ConceptualException - if a conceptual problem is detected while resolving the member types
   * @throws NameConflictException - if a name conflict is detected while resolving a PointerType
   */
  private boolean resolveClassMembers(ConceptualClass toResolve, Set<LexicalPhrase> unresolvedLexicalPhrases, Set<TypeDefinition> notFullyResolved) throws ConceptualException, NameConflictException
  {
    boolean changed = false;
    QueueState queueState = new QueueState();
    try
    {
      for (Method method : toResolve.getMethods())
      {
        if (resolveMethodType(method, (MethodAST) conceptualASTNodes.get(method)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      for (Property property : toResolve.getProperties())
      {
        if (resolvePropertyType(property, (PropertyAST) conceptualASTNodes.get(property)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      for (MemberVariable variable : toResolve.getVariables())
      {
        if (resolveVariableType(variable, (DeclarationAssigneeAST) conceptualASTNodes.get(variable)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      for (Constructor constructor : toResolve.getConstructors())
      {
        if (resolveConstructorType(constructor, (ConstructorAST) conceptualASTNodes.get(constructor)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      // if we get here then everything has been processed
      // we have removed something from the queue, so a change has occurred
      changed = true;
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
    }
    catch (UnresolvableException e)
    {
      notFullyResolved.add(toResolve);
      unresolvedLexicalPhrases.add(e.getLexicalPhrase());

      // some members still need filling in, so add this to the end of the queue again
      membersToResolve.add(toResolve);

      if (queueState.hasChanged())
      {
        // one of the queues was modified during a call to resolvePointerType(),
        // because a new file was loaded and addFile() was called.
        // this must count as a change, for reasons described above
        changed = true;
        notFullyResolved.clear();
        unresolvedLexicalPhrases.clear();
      }
    }

    return changed;
  }

  /**
   * Attempts to resolve the members of the specified ConceptualInterface.
   * If not all member types are resolved, then the specified interface is added to notFullyResolved,
   * and is put back on the end of the membersToResolve queue.
   * The notFullyResolved and unresolvedLexicalPhrases sets are both cleared whenever a change is made.
   * @param toResolve - the ConceptualInterface to resolve the member types of
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made.
   * @param notFullyResolved - the set of types which have bounds which failed to resolve since the last change was made
   * @return true if any changes were made, false otherwise
   * @throws ConceptualException - if a conceptual problem is detected while resolving the member types
   * @throws NameConflictException - if a name conflict is detected while resolving a PointerType
   */
  private boolean resolveInterfaceMembers(ConceptualInterface toResolve, Set<LexicalPhrase> unresolvedLexicalPhrases, Set<TypeDefinition> notFullyResolved) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    QueueState queueState = new QueueState();
    try
    {
      for (Method method : toResolve.getMethods())
      {
        if (resolveMethodType(method, (MethodAST) conceptualASTNodes.get(method)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      for (Property property : toResolve.getProperties())
      {
        if (resolvePropertyType(property, (PropertyAST) conceptualASTNodes.get(property)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      for (MemberVariable variable : toResolve.getStaticVariables())
      {
        if (resolveVariableType(variable, (DeclarationAssigneeAST) conceptualASTNodes.get(variable)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      // if we get here then everything has been processed
      // we have removed something from the queue, so a change has occurred
      changed = true;
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
    }
    catch (UnresolvableException e)
    {
      notFullyResolved.add(toResolve);
      unresolvedLexicalPhrases.add(e.getLexicalPhrase());

      // some members still need filling in, so add this to the end of the queue again
      membersToResolve.add(toResolve);

      if (queueState.hasChanged())
      {
        // one of the queues was modified during a call to resolvePointerType(),
        // because a new file was loaded and addFile() was called.
        // this must count as a change, for reasons described above
        changed = true;
        notFullyResolved.clear();
        unresolvedLexicalPhrases.clear();
      }
    }

    return changed;
  }

  /**
   * Attempts to resolve the members of the specified ConceptualEnum.
   * If not all member types are resolved, then the specified enum is added to notFullyResolved,
   * and is put back on the end of the membersToResolve queue.
   * The notFullyResolved and unresolvedLexicalPhrases sets are both cleared whenever a change is made.
   * @param toResolve - the ConceptualEnum to resolve the member types of
   * @param unresolvedLexicalPhrases - the set containing the LexicalPhrase of each QName which has been tried for resolution unsuccessfully since the last change was made.
   * @param notFullyResolved - the set of types which have bounds which failed to resolve since the last change was made
   * @return true if any changes were made, false otherwise
   * @throws ConceptualException - if a conceptual problem is detected while resolving the member types
   * @throws NameConflictException - if a name conflict is detected while resolving a PointerType
   */
  private boolean resolveEnumMembers(ConceptualEnum toResolve, Set<LexicalPhrase> unresolvedLexicalPhrases, Set<TypeDefinition> notFullyResolved) throws ConceptualException, NameConflictException
  {
    boolean changed = false;
    QueueState queueState = new QueueState();
    try
    {
      for (Method method : toResolve.getMethods())
      {
        if (resolveMethodType(method, (MethodAST) conceptualASTNodes.get(method)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      for (Property property : toResolve.getProperties())
      {
        if (resolvePropertyType(property, (PropertyAST) conceptualASTNodes.get(property)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      for (MemberVariable variable : toResolve.getVariables())
      {
        if (resolveVariableType(variable, (DeclarationAssigneeAST) conceptualASTNodes.get(variable)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      for (Constructor constructor : toResolve.getConstructors())
      {
        if (resolveConstructorType(constructor, (ConstructorAST) conceptualASTNodes.get(constructor)))
        {
          changed = true;
          notFullyResolved.clear();
          unresolvedLexicalPhrases.clear();
        }
      }
      // if we get here then everything has been processed
      // we have removed something from the queue, so a change has occurred
      changed = true;
      notFullyResolved.clear();
      unresolvedLexicalPhrases.clear();
    }
    catch (UnresolvableException e)
    {
      notFullyResolved.add(toResolve);
      unresolvedLexicalPhrases.add(e.getLexicalPhrase());

      // some members still need filling in, so add this to the end of the queue again
      membersToResolve.add(toResolve);

      if (queueState.hasChanged())
      {
        // one of the queues was modified during a call to resolvePointerType(),
        // because a new file was loaded and addFile() was called.
        // this must count as a change, for reasons described above
        changed = true;
        notFullyResolved.clear();
        unresolvedLexicalPhrases.clear();
      }
    }
    return changed;
  }

  /**
   * Resolves the type of the specified MemberVariable.
   * @param variable - the variable to resolve the type of
   * @param astNode - the AST node to get the names to resolve from
   * @return true if a change was made, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving the variable's type
   * @throws UnresolvableException - if further initialisation must be done before one of the names can be resolved
   */
  private boolean resolveVariableType(MemberVariable variable, DeclarationAssigneeAST astNode) throws NameConflictException, ConceptualException, UnresolvableException
  {
    if (variable.getVariableType() == null)
    {
      variable.setVariableType(ASTConverter.convert(astNode.getEnclosingField().getType(), this, variable));
      return true;
    }
    return false;
  }

  /**
   * Resolves the type of the specified Property.
   * @param property - the property to resolve the type of
   * @param astNode - the AST node to get the names to resolve from
   * @return true if a change was made, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving the property's type
   * @throws UnresolvableException - if further initialisation must be done before one of the names can be resolved
   */
  private boolean resolvePropertyType(Property property, PropertyAST astNode) throws NameConflictException, ConceptualException, UnresolvableException
  {
    if (property.getPropertyType() == null)
    {
      property.setPropertyType(ASTConverter.convert(astNode.getType(), this, property));
      return true;
    }
    return false;
  }

  /**
   * Resolves the type of the specified Constructor, including type parameters and thrown types
   * @param constructor - the Constructor to resolve the type of
   * @param astNode - the AST node to get the names to resolve from
   * @return true if a change was made, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving the constructor's type
   * @throws UnresolvableException - if further initialisation must be done before one of the names can be resolved
   */
  private boolean resolveConstructorType(Constructor constructor, ConstructorAST astNode) throws ConceptualException, NameConflictException, UnresolvableException
  {
    if (constructor.getParameters() == null || constructor.getThrownTypes() == null)
    {
      ParameterList parameterList = ASTConverter.convert(astNode.getParameters(),  this, constructor);
      PointerType[] thrownTypes   = ASTConverter.convert(astNode.getThrownTypes(), this, constructor);
      constructor.setHeader(parameterList, thrownTypes);
      return true;
    }
    return false;
  }

  /**
   * Resolves the type of the specified Method, including type parameters, parameters, return type, and thrown types.
   * @param method - the method to resolve the type of
   * @param astNode - the AST node to get the names to resolve from
   * @return true if a change was made, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving the method's type
   * @throws UnresolvableException - if further initialisation must be done before one of the names can be resolved
   */
  private boolean resolveMethodType(Method method, MethodAST astNode) throws ConceptualException, NameConflictException, UnresolvableException
  {
    if (method.getTypeParameters() == null || method.getParameters() == null || method.getReturnType() == null || method.getThrownTypes() == null)
    {
      TypeParameter[] typeParameters = ASTConverter.convert(astNode.getTypeParameters(), this, method);
      ParameterList parameterList    = ASTConverter.convert(astNode.getParameters(),     this, method);
      Type returnType                = ASTConverter.convert(astNode.getReturnType(),     this, method);
      PointerType[] thrownTypes      = ASTConverter.convert(astNode.getThrownTypes(),    this, method);
      method.setHeader(typeParameters, parameterList, returnType, thrownTypes);
      return true;
    }
    return false;
  }

}

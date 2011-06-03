package compiler.language.translator.conceptual;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.ast.typeDefinition.EnumDefinitionAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.UnresolvableException;
import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.conceptual.topLevel.ConceptualPackage;
import compiler.language.conceptual.type.ClassPointerType;
import compiler.language.conceptual.type.EnumPointerType;
import compiler.language.conceptual.type.InterfacePointerType;
import compiler.language.conceptual.type.OuterClassPointerType;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.TypeParameter;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
import compiler.language.conceptual.typeDefinition.InnerClass;
import compiler.language.conceptual.typeDefinition.TypeDefinition;

/*
 * Created on 20 Feb 2011
 */

/**
 * @author Anthony Bryant
 */
public final class NameResolver
{

  private Map<Object, Object> conceptualASTNodes;

  private Queue<ConceptualInterface> interfacesToResolve = new LinkedList<ConceptualInterface>();
  private Queue<ConceptualClass> classesToResolve = new LinkedList<ConceptualClass>();
  private Queue<ConceptualEnum> enumsToResolve = new LinkedList<ConceptualEnum>();

  private static final QName UNIVERSAL_BASE_CLASS_QNAME = new QName("x", "Object");
  private OuterClassPointerType universalBaseClass;

  /**
   * Creates a new NameResolver with the specified mapping from Conceptual node to AST node.
   * @param conceptualASTNodes - the mapping which stores the AST node of each conceptual node which has been converted
   */
  public NameResolver(Map<Object, Object> conceptualASTNodes)
  {
    this.conceptualASTNodes = conceptualASTNodes;
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
  }

  /**
   * Adds all of the type definitions in the specified ConceptualFile to this resolver's queues
   * @param file - the file to add the type definitions of
   */
  public void addFile(ConceptualFile file)
  {
    for (ConceptualClass conceptualClass : file.getClasses())
    {
      classesToResolve.add(conceptualClass);
    }
    for (ConceptualInterface conceptualInterface : file.getInterfaces())
    {
      interfacesToResolve.add(conceptualInterface);
    }
    for (ConceptualEnum conceptualEnum : file.getEnums())
    {
      enumsToResolve.add(conceptualEnum);
    }
  }

  /**
   * Resolves the parent types (base classes and super-interfaces) of all types in files which have been added via addFile()
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent types
   */
  public void resolveParents() throws NameConflictException, ConceptualException
  {
    // resolve base types
    boolean changed = true;
    // TODO: change this set to a Map<TypeDefinition, PointerTypeAST>, so that we can point out exactly which names could not be resolved
    Set<TypeDefinition> unresolvedSinceLastChange = new HashSet<TypeDefinition>();

    while (changed && !(interfacesToResolve.isEmpty() && classesToResolve.isEmpty() && enumsToResolve.isEmpty()))
    {
      // clear the unresolvedSinceLastChange set, as it will be repopulated during the loop
      unresolvedSinceLastChange.clear();

      // resolve the super-interfaces of all unresolved interfaces
      changed = resolveInterfaceParents(unresolvedSinceLastChange);

      // resolve the base class and super-interfaces of all unresolved classes
      changed = resolveClassParents(unresolvedSinceLastChange) || changed;

      // resolve the base class and super-interfaces of all unresolved enums
      changed = resolveEnumParents(unresolvedSinceLastChange) || changed;
    }

    if (!interfacesToResolve.isEmpty() || !classesToResolve.isEmpty() || !enumsToResolve.isEmpty())
    {
      // something could not be resolved, so throw an exception with the correct ParseInfo
      List<ParseInfo> parseInfo = new LinkedList<ParseInfo>();
      for (TypeDefinition typeDefinition : unresolvedSinceLastChange)
      {
        if (typeDefinition instanceof ConceptualInterface)
        {
          parseInfo.add(((InterfaceDefinitionAST) conceptualASTNodes.get(typeDefinition)).getParseInfo());
        }
        else if (typeDefinition instanceof ConceptualClass)
        {
          parseInfo.add(((ClassDefinitionAST) conceptualASTNodes.get(typeDefinition)).getParseInfo());
        }
        else if (typeDefinition instanceof ConceptualEnum)
        {
          parseInfo.add(((EnumDefinitionAST) conceptualASTNodes.get(typeDefinition)).getParseInfo());
        }
        else
        {
          throw new IllegalStateException("Invalid type definition encountered while building error message for \"Unresolvable parent type(s)\"");
        }
      }
      throw new ConceptualException("Unresolvable parent type(s)", parseInfo.toArray(new ParseInfo[parseInfo.size()]));
    }
  }

  /**
   * Resolves the parent interfaces of all interfaces in the interfacesToResolve queue.
   * @param unresolvedSinceLastChange - the set of all type definitions which have been tried for resolution unsuccessfully since the last change was made
   * @return true if changes were made to the conceptual hierarchy, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent interfaces
   */
  private boolean resolveInterfaceParents(Set<TypeDefinition> unresolvedSinceLastChange) throws NameConflictException, ConceptualException
  {
    boolean changed = false;

    // try to resolve the parent interfaces of every interface
    // this uses a queue instead of an iterator because new interfaces can be added to the list while we are working on it
    // (e.g. if an interface extends an interface which has not been parsed yet)
    while (!interfacesToResolve.isEmpty())
    {
      ConceptualInterface toResolve = interfacesToResolve.poll();
      if (unresolvedSinceLastChange.contains(toResolve))
      {
        // all of the interfaces in the queue have been processed since a change has been made
        // (this depends on interfacesToResolve being a queue)
        return changed;
      }
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
        int queueSize = interfacesToResolve.size();
        try
        {
          InterfacePointerType parentPointerType = resolveInterfacePointerType(parentInterfaces[i], toResolve);
          checkParentInterface(parentPointerType, parentInterfaces[i], toResolve);
          pointerTypes[i] = parentPointerType;
          changed = true;
          unresolvedSinceLastChange.clear();
        }
        catch (UnresolvableException e)
        {
          // leave pointerTypes[i] as null
          fullyResolved = false;

          if (queueSize < interfacesToResolve.size())
          {
            // the queue of interfaces to resolve was modified during the call to resolvePointerType()
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
            unresolvedSinceLastChange.clear();
          }
        }
      }
      if (!fullyResolved)
      {
        // some parent interfaces still need filling in, so add this to the end of the queue again
        unresolvedSinceLastChange.add(toResolve);
        interfacesToResolve.add(toResolve);
      }
    }
    return changed;
  }

  /**
   * Checks the specified InterfacePointerType as being a valid parent interface.
   * If a child interface is provided, this method checks that the child will not become one of its own parents.
   * @param parent - the parent interface's PointerType, to check
   * @param parentAST - the parent type's PointerTypeAST, to extract ParseInfo from in case of error
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
        throw new ConceptualException("Cycle detected in interface inheritance hierarchy", parentAST.getParseInfo());
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
   * Resolves the parent classes and interfaces of all classes in the classesToResolve queue.
   * @param unresolvedSinceLastChange - the set of all type definitions which have been tried for resolution unsuccessfully since the last change was made
   * @return true if changes were made to the conceptual hierarchy, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent classes/interfaces
   */
  private boolean resolveClassParents(Set<TypeDefinition> unresolvedSinceLastChange) throws NameConflictException, ConceptualException
  {
    boolean changed = false;

    // try to resolve the parent classes and interfaces of every class
    // this uses a queue instead of an iterator because new classes can be added to the list while we are working on it
    // (e.g. if a class extends another class which has not been parsed yet)
    while (interfacesToResolve.isEmpty() && !classesToResolve.isEmpty())
    {
      ConceptualClass toResolve = classesToResolve.poll();
      if (unresolvedSinceLastChange.contains(toResolve))
      {
        // all of the classes in the queue have been processed since a change has been made
        // (this depends on classesToResolve being a queue)
        return changed;
      }
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
          unresolvedSinceLastChange.clear();
        }
        else
        {
          int queueSize = classesToResolve.size();
          try
          {
            baseClass = resolveClassPointerType(baseClassAST, toResolve);
            checkParentClass(baseClass, baseClassAST, toResolve);
            toResolve.setBaseClass(baseClass);
            changed = true;
            unresolvedSinceLastChange.clear();
          }
          catch (UnresolvableException e)
          {
            fullyResolved = false;
            if (queueSize != classesToResolve.size())
            {
              // the queue of classes to resolve was modified during the call to resolvePointerType(),
              // because a new file was loaded and addFile() was called.
              // this must count as a change, for reasons described above
              changed = true;
              unresolvedSinceLastChange.clear();
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
        int queueSize = classesToResolve.size();
        try
        {
          InterfacePointerType parentInterface = resolveInterfacePointerType(parentInterfaceASTs[i], toResolve);
          checkParentInterface(parentInterface, parentInterfaceASTs[i], null);
          parentInterfaces[i] = parentInterface;
          changed = true;
          unresolvedSinceLastChange.clear();
        }
        catch (UnresolvableException e)
        {
          // leave parentInterfaces[i] as null
          fullyResolved = false;

          if (queueSize < classesToResolve.size())
          {
            // the queue of classes to resolve was modified during the call to resolvePointerType(),
            // because a new file was loaded and addFile() was called.
            // this must count as a change, for reasons described above
            changed = true;
            unresolvedSinceLastChange.clear();
          }
        }
      }
      if (!fullyResolved)
      {
        // some parent classes/interfaces still need filling in, so add this to the end of the queue again
        unresolvedSinceLastChange.add(toResolve);
        classesToResolve.add(toResolve);
      }
    }
    return changed;
  }

  /**
   * Checks the specified ClassPointerType as being a valid parent class.
   * If a child class is provided, this method checks that the child will not become one of its own parents.
   * @param parent - the parent class's ClassPointerType, to check
   * @param parentAST - the parent type's PointerTypeAST, to extract ParseInfo from in case of error
   * @param child - the prospective child class, if any
   * @throws ConceptualException - if there is a problem and the PointerType is not a valid parent class
   */
  private void checkParentClass(ClassPointerType parent, PointerTypeAST parentAST, ConceptualClass child) throws ConceptualException
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
        throw new ConceptualException("Cycle detected in class inheritance hierarchy", parentAST.getParseInfo());
      }
      ClassPointerType baseClass = current.getBaseClass();
      if (baseClass == null)
      {
        break;
      }
      current = baseClass.getClassType();
    }
  }

  /**
   * Resolves the parent classes and interfaces of all enums in the enumsToResolve queue.
   * @param unresolvedSinceLastChange - the set of all type definitions which have been tried for resolution unsuccessfully since the last change was made
   * @return true if changes were made to the conceptual hierarchy, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent classes/interfaces
   */
  private boolean resolveEnumParents(Set<TypeDefinition> unresolvedSinceLastChange) throws NameConflictException, ConceptualException
  {
    boolean changed = false;

    // try to resolve the parent classes and interfaces of every class
    // this uses a queue instead of an iterator because new classes can be added to the list while we are working on it
    // (e.g. if a class extends another class which has not been parsed yet)
    while (interfacesToResolve.isEmpty() && classesToResolve.isEmpty() && !enumsToResolve.isEmpty())
    {
      ConceptualEnum toResolve = enumsToResolve.poll();
      if (unresolvedSinceLastChange.contains(toResolve))
      {
        // all of the enums in the queue have been processed since a change has been made
        // (this depends on enumsToResolve being a queue)
        return changed;
      }
      EnumDefinitionAST astNode = (EnumDefinitionAST) conceptualASTNodes.get(toResolve);

      boolean fullyResolved = true;
      PointerTypeAST baseClassAST = astNode.getBaseClass();
      ClassPointerType baseClass = toResolve.getBaseClass();
      if (baseClass == null)
      {
        if (baseClassAST == null)
        {
          baseClass = universalBaseClass;
          toResolve.setBaseClass(baseClass);
          changed = true;
          unresolvedSinceLastChange.clear();
        }
        else
        {
          int queueSize = enumsToResolve.size();
          try
          {
            baseClass = resolveClassPointerType(baseClassAST, toResolve);
            toResolve.setBaseClass(baseClass);
            changed = true;
            unresolvedSinceLastChange.clear();
          }
          catch (UnresolvableException e)
          {
            fullyResolved = false;
            if (queueSize != enumsToResolve.size())
            {
              // the queue of enums to resolve was modified during the call to resolvePointerType(),
              // because a new file was loaded and addFile() was called.
              // this must count as a change, for reasons described above
              changed = true;
              unresolvedSinceLastChange.clear();
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
        int queueSize = enumsToResolve.size();
        try
        {
          InterfacePointerType parentInterface = resolveInterfacePointerType(parentInterfaceASTs[i], toResolve);
          checkParentInterface(parentInterface, parentInterfaceASTs[i], null);
          parentInterfaces[i] = parentInterface;
          changed = true;
          unresolvedSinceLastChange.clear();
        }
        catch (UnresolvableException e)
        {
          // leave parentInterfaces[i] as null
          fullyResolved = false;

          if (queueSize < enumsToResolve.size())
          {
            // the queue of enums to resolve was modified during the call to resolvePointerType(),
            // because a new file was loaded and addFile() was called.
            // this must count as a change, for reasons described above
            changed = true;
            unresolvedSinceLastChange.clear();
          }
        }
      }
      if (!fullyResolved)
      {
        // some parent classes/interfaces still need filling in, so add this to the end of the queue again
        unresolvedSinceLastChange.add(toResolve);
        enumsToResolve.add(toResolve);
      }
    }
    return changed;
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
      throw new ConceptualException("This type should resolve to a class, but does not", pointerTypeAST.getParseInfo());
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
      throw new ConceptualException("This type should resolve to an interface, but does not", pointerTypeAST.getParseInfo());
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
    // resolve the QName without taking the type parameter lists into account
    QName qname = ASTConverter.convert(pointerTypeAST.getNames());
    Resolvable resolved = startScope.resolve(qname, true);

    if (resolved == null)
    {
      throw new ConceptualException("Could not resolve PointerType", pointerTypeAST.getParseInfo());
    }

    TypeParameterAST[][] typeParameterLists = pointerTypeAST.getTypeParameterLists();
    TypeParameterAST[] lastTypeParameters = typeParameterLists[typeParameterLists.length - 1];

    if (resolved.getType() == ScopeType.OUTER_CLASS ||
        resolved.getType() == ScopeType.OUTER_INTERFACE ||
        resolved.getType() == ScopeType.OUTER_ENUM ||
        resolved.getType() == ScopeType.INNER_INTERFACE ||
        resolved.getType() == ScopeType.INNER_ENUM)
    {
      // make sure all but the last type parameter list is null
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        if (typeParameterLists[i] != null)
        {
          throw new ConceptualException("Type parameters are not allowed on this name", pointerTypeAST.getNames()[i].getParseInfo());
        }
      }
      if (resolved.getType() == ScopeType.OUTER_CLASS)
      {
        return new OuterClassPointerType((ConceptualClass) resolved, ASTConverter.convert(lastTypeParameters, this, startScope), pointerTypeAST.isImmutable());
      }
      if (resolved.getType() == ScopeType.OUTER_INTERFACE ||
          resolved.getType() == ScopeType.INNER_INTERFACE)
      {
        return new InterfacePointerType((ConceptualInterface) resolved, ASTConverter.convert(lastTypeParameters, this, startScope), pointerTypeAST.isImmutable());
      }
      return new EnumPointerType((ConceptualEnum) resolved, pointerTypeAST.isImmutable());
    }
    else if (resolved.getType() != ScopeType.INNER_CLASS)
    {
      throw new ConceptualException("Cannot refer to a " + resolved.getType() + " as a pointer type", pointerTypeAST.getParseInfo());
    }

    // we have an inner class, so find the type parameters for it
    InnerClass innerClass = (InnerClass) resolved;
    LinkedList<ConceptualClass> classes = new LinkedList<ConceptualClass>();
    LinkedList<TypeParameter[]> typeParameters = new LinkedList<TypeParameter[]>();
    while (true)
    {
      classes.addFirst(innerClass);
      if (innerClass.getTypeArguments() == null || innerClass.getTypeArguments().length == 0)
      {
        // this class does not need type parameters
        typeParameters.addFirst(null);
      }
      else if (typeParameterLists.length >= classes.size())
      {
        // get the type parameters from the PointerTypeAST
        typeParameters.addFirst(ASTConverter.convert(typeParameterLists[typeParameterLists.length - classes.size()], this, startScope));
      }
      else
      {
        // TODO: resolve the missing type parameters from elsewhere, e.g. the current class
        // TODO: this will require implicitly generating some TypeArgumentPointerTypes, so that the user can do:
        /*
         * class X<A> {
         *   class Y {}
         *   Y y;      // this
         *   X<A>.Y y; // instead of this
         * }
         */
        // TODO: there may also be other situations where missing type parameters can be filled in from elsewhere
        throw new ConceptualException("Not enough type parameters were provided to resolve this pointer type", pointerTypeAST.getParseInfo());
      }

      TypeDefinition parent = innerClass.getParent();
      if (parent.getType() == ScopeType.OUTER_CLASS)
      {
        classes.addFirst((ConceptualClass) parent);
        break;
      }
      if (parent.getType() != ScopeType.INNER_CLASS || ((InnerClass) parent).isStatic())
      {
        // the parent is either not a class, or it is a static inner class, so leave the list here
        break;
      }
      // we now know that parent.getType() == ScopeType.INNER_CLASS, so we can cast safely for the next iteration
      innerClass = (InnerClass) parent;
    }

    return new ClassPointerType(classes.toArray(new ConceptualClass[classes.size()]),
                                typeParameters.toArray(new TypeParameter[typeParameters.size()][]),
                                pointerTypeAST.isImmutable());
  }
}

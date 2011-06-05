package compiler.language.translator.conceptual;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
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
import compiler.language.conceptual.type.TypeArgument;
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
      addInnerTypes(conceptualClass.getInnerClasses(), conceptualClass.getInnerInterfaces(), conceptualClass.getInnerEnums());
    }
    for (ConceptualInterface conceptualInterface : file.getInterfaces())
    {
      interfacesToResolve.add(conceptualInterface);
      addInnerTypes(conceptualInterface.getInnerClasses(), conceptualInterface.getInnerInterfaces(), conceptualInterface.getInnerEnums());
    }
    for (ConceptualEnum conceptualEnum : file.getEnums())
    {
      enumsToResolve.add(conceptualEnum);
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
        classesToResolve.add(innerClass);
        addInnerTypes(innerClass.getInnerClasses(), innerClass.getInnerInterfaces(), innerClass.getInnerEnums());
      }
    }
    if (innerInterfaces != null)
    {
      for (ConceptualInterface innerInterface : innerInterfaces)
      {
        interfacesToResolve.add(innerInterface);
        addInnerTypes(innerInterface.getInnerClasses(), innerInterface.getInnerInterfaces(), innerInterface.getInnerEnums());
      }
    }
    if (innerEnums != null)
    {
      for (ConceptualEnum innerEnum : innerEnums)
      {
        enumsToResolve.add(innerEnum);
        addInnerTypes(innerEnum.getInnerClasses(), innerEnum.getInnerInterfaces(), innerEnum.getInnerEnums());
      }
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
    Set<ParseInfo> unresolvedParseInfo = new HashSet<ParseInfo>();

    while (changed)
    {
      // clear the unresolvedParseInfo set, as it will be repopulated during the loop, and should only contain the unresolved ParseInfo since the last change
      unresolvedParseInfo.clear();

      changed = resolveNames(unresolvedParseInfo);
    }

    if (!interfacesToResolve.isEmpty() || !classesToResolve.isEmpty() || !enumsToResolve.isEmpty())
    {
      // something could not be resolved, so throw an exception with the correct ParseInfo
      throw new ConceptualException("Unresolvable parent type(s)", unresolvedParseInfo.toArray(new ParseInfo[unresolvedParseInfo.size()]));
    }
  }

  /**
   * Resolves as many QNames as possible from a single queue.
   * For example, if there are interfaces to resolve the parents of then this method resolves as many as possible, and then returns.
   * The queues are checked in a fixed order, so that interface parents will always be resolved in preference to class parents.
   * @param unresolvedParseInfo - the set of ParseInfo objects for names which could not be resolved since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a name
   * @throws ConceptualException - if a conceptual problem occurs while resolving a name
   */
  private boolean resolveNames(Set<ParseInfo> unresolvedParseInfo) throws NameConflictException, ConceptualException
  {
    if (!interfacesToResolve.isEmpty())
    {
      return resolveInterfaceParents(unresolvedParseInfo);
    }
    if (!classesToResolve.isEmpty())
    {
      return resolveClassParents(unresolvedParseInfo);
    }
    if (!enumsToResolve.isEmpty())
    {
      return resolveEnumParents(unresolvedParseInfo);
    }
    return false;
  }

  /**
   * Resolves the parent interfaces of all interfaces in the interfacesToResolve queue.
   * @param unresolvedParseInfo - the set containing the ParseInfo of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent interfaces
   */
  private boolean resolveInterfaceParents(Set<ParseInfo> unresolvedParseInfo) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    Set<ConceptualInterface> notFullyResolved = new HashSet<ConceptualInterface>();

    // try to resolve the parent interfaces of every interface
    // this uses a queue instead of an iterator because new interfaces can be added to the list while we are working on it
    // (e.g. if an interface extends an interface which has not been parsed yet)
    while (!interfacesToResolve.isEmpty())
    {
      ConceptualInterface toResolve = interfacesToResolve.poll();
      if (notFullyResolved.contains(toResolve))
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
          notFullyResolved.clear();
          unresolvedParseInfo.clear();
        }
        catch (UnresolvableException e)
        {
          // leave pointerTypes[i] as null
          fullyResolved = false;
          unresolvedParseInfo.add(parentInterfaces[i].getParseInfo());

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
            notFullyResolved.clear();
            unresolvedParseInfo.clear();
          }
        }
      }
      if (fullyResolved)
      {
        // we have removed something from the queue, so a change has occurred
        changed = true;
        notFullyResolved.clear();
        unresolvedParseInfo.clear();
      }
      else
      {
        // some parent interfaces still need filling in, so add this to the end of the queue again
        interfacesToResolve.add(toResolve);
        notFullyResolved.add(toResolve);
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
   * @param unresolvedParseInfo - the set containing the ParseInfo of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent classes/interfaces
   */
  private boolean resolveClassParents(Set<ParseInfo> unresolvedParseInfo) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    Set<ConceptualClass> notFullyResolved = new HashSet<ConceptualClass>();

    // try to resolve the parent classes and interfaces of every class
    // this uses a queue instead of an iterator because new classes can be added to the list while we are working on it
    // (e.g. if a class extends another class which has not been parsed yet)
    while (interfacesToResolve.isEmpty() && !classesToResolve.isEmpty())
    {
      ConceptualClass toResolve = classesToResolve.poll();
      if (notFullyResolved.contains(toResolve))
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
          notFullyResolved.clear();
          unresolvedParseInfo.clear();
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
            notFullyResolved.clear();
            unresolvedParseInfo.clear();
          }
          catch (UnresolvableException e)
          {
            fullyResolved = false;
            unresolvedParseInfo.add(baseClassAST.getParseInfo());

            if (queueSize != classesToResolve.size())
            {
              // the queue of classes to resolve was modified during the call to resolvePointerType(),
              // because a new file was loaded and addFile() was called.
              // this must count as a change, for reasons described above
              changed = true;
              notFullyResolved.clear();
              unresolvedParseInfo.clear();
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
          notFullyResolved.clear();
          unresolvedParseInfo.clear();
        }
        catch (UnresolvableException e)
        {
          // leave parentInterfaces[i] as null
          fullyResolved = false;
          unresolvedParseInfo.add(parentInterfaceASTs[i].getParseInfo());

          if (queueSize < classesToResolve.size())
          {
            // the queue of classes to resolve was modified during the call to resolvePointerType(),
            // because a new file was loaded and addFile() was called.
            // this must count as a change, for reasons described above
            changed = true;
            notFullyResolved.clear();
            unresolvedParseInfo.clear();
          }
        }
      }
      if (fullyResolved)
      {
        // we have removed something from the queue, so a change has occurred
        changed = true;
        notFullyResolved.clear();
        unresolvedParseInfo.clear();
      }
      else
      {
        // some parent classes/interfaces still need filling in, so add this to the end of the queue again
        notFullyResolved.add(toResolve);
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
   * @param unresolvedParseInfo - the set containing the ParseInfo of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent classes/interfaces
   */
  private boolean resolveEnumParents(Set<ParseInfo> unresolvedParseInfo) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    Set<ConceptualEnum> notFullyResolved = new HashSet<ConceptualEnum>();

    // try to resolve the parent classes and interfaces of every class
    // this uses a queue instead of an iterator because new classes can be added to the list while we are working on it
    // (e.g. if a class extends another class which has not been parsed yet)
    while (interfacesToResolve.isEmpty() && classesToResolve.isEmpty() && !enumsToResolve.isEmpty())
    {
      ConceptualEnum toResolve = enumsToResolve.poll();
      if (notFullyResolved.contains(toResolve))
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
          notFullyResolved.clear();
          unresolvedParseInfo.clear();
        }
        else
        {
          int queueSize = enumsToResolve.size();
          try
          {
            baseClass = resolveClassPointerType(baseClassAST, toResolve);
            toResolve.setBaseClass(baseClass);
            changed = true;
            notFullyResolved.clear();
            unresolvedParseInfo.clear();
          }
          catch (UnresolvableException e)
          {
            fullyResolved = false;
            unresolvedParseInfo.add(baseClassAST.getParseInfo());

            if (queueSize != enumsToResolve.size())
            {
              // the queue of enums to resolve was modified during the call to resolvePointerType(),
              // because a new file was loaded and addFile() was called.
              // this must count as a change, for reasons described above
              changed = true;
              notFullyResolved.clear();
              unresolvedParseInfo.clear();
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
          notFullyResolved.clear();
          unresolvedParseInfo.clear();
        }
        catch (UnresolvableException e)
        {
          // leave parentInterfaces[i] as null
          fullyResolved = false;
          unresolvedParseInfo.add(parentInterfaceASTs[i].getParseInfo());

          if (queueSize < enumsToResolve.size())
          {
            // the queue of enums to resolve was modified during the call to resolvePointerType(),
            // because a new file was loaded and addFile() was called.
            // this must count as a change, for reasons described above
            changed = true;
            notFullyResolved.clear();
            unresolvedParseInfo.clear();
          }
        }
      }
      if (fullyResolved)
      {
        // we have removed something from the queue, so a change has occurred
        changed = true;
        notFullyResolved.clear();
        unresolvedParseInfo.clear();
      }
      else
      {
        // some parent classes/interfaces still need filling in, so add this to the end of the queue again
        notFullyResolved.add(toResolve);
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
    // resolve the QName without taking the type argument lists into account
    QName qname = ASTConverter.convert(pointerTypeAST.getNames());
    Resolvable resolved = startScope.resolve(qname, true);

    if (resolved == null)
    {
      throw new ConceptualException("Could not resolve PointerType", pointerTypeAST.getParseInfo());
    }

    TypeArgumentAST[][] typeArgumentLists = pointerTypeAST.getTypeArgumentLists();
    TypeArgumentAST[] lastTypeArguments = typeArgumentLists[typeArgumentLists.length - 1];

    if (resolved.getType() == ScopeType.OUTER_CLASS ||
        resolved.getType() == ScopeType.OUTER_INTERFACE ||
        resolved.getType() == ScopeType.OUTER_ENUM ||
        resolved.getType() == ScopeType.INNER_INTERFACE ||
        resolved.getType() == ScopeType.INNER_ENUM)
    {
      // make sure all but the last type argument list is null
      for (int i = 0; i < typeArgumentLists.length - 1; i++)
      {
        if (typeArgumentLists[i] != null)
        {
          throw new ConceptualException("Type arguments are not allowed on this name", pointerTypeAST.getNames()[i].getParseInfo());
        }
      }
      if (resolved.getType() == ScopeType.OUTER_CLASS)
      {
        return new OuterClassPointerType((ConceptualClass) resolved, ASTConverter.convert(lastTypeArguments, this, startScope), pointerTypeAST.isImmutable());
      }
      if (resolved.getType() == ScopeType.OUTER_INTERFACE ||
          resolved.getType() == ScopeType.INNER_INTERFACE)
      {
        return new InterfacePointerType((ConceptualInterface) resolved, ASTConverter.convert(lastTypeArguments, this, startScope), pointerTypeAST.isImmutable());
      }
      return new EnumPointerType((ConceptualEnum) resolved, pointerTypeAST.isImmutable());
    }
    else if (resolved.getType() != ScopeType.INNER_CLASS)
    {
      throw new ConceptualException("Cannot refer to a " + resolved.getType() + " as a pointer type", pointerTypeAST.getParseInfo());
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
        typeArguments.addFirst(ASTConverter.convert(typeArgumentLists[typeArgumentLists.length - classes.size()], this, startScope));
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
        throw new ConceptualException("Not enough type arguments were provided to resolve this pointer type", pointerTypeAST.getParseInfo());
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
}

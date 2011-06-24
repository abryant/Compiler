package compiler.language.translator.conceptual;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.FieldAST;
import compiler.language.ast.member.MethodAST;
import compiler.language.ast.member.PropertyAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeArgumentAST;
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

  private Queue<ConceptualInterface> interfacesToResolveParents = new LinkedList<ConceptualInterface>();
  private Queue<ConceptualClass> classesToResolveParents = new LinkedList<ConceptualClass>();
  private Queue<ConceptualEnum> enumsToResolveParents = new LinkedList<ConceptualEnum>();

  // TODO: implement methods to resolve things from these queues:
  private Queue<ConceptualInterface> interfacesToResolveTypeBounds = new LinkedList<ConceptualInterface>();
  private Queue<ConceptualClass> classesToResolveTypeBounds = new LinkedList<ConceptualClass>();

  private Queue<ConceptualInterface> interfacesToResolveMembers = new LinkedList<ConceptualInterface>();
  private Queue<ConceptualClass> classesToResolveMembers = new LinkedList<ConceptualClass>();
  private Queue<ConceptualEnum> enumsToResolveMembers = new LinkedList<ConceptualEnum>();

  private static final QName UNIVERSAL_BASE_CLASS_QNAME = new QName("x", "Object");
  private OuterClassPointerType universalBaseClass;


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
    private int interfaceParentsLength;
    private int classParentsLength;
    private int enumParentsLength;
    private int interfaceTypeBoundsLength;
    private int classTypeBoundsLength;
    private int interfaceMembersLength;
    private int classMembersLength;
    private int enumMembersLength;

    private QueueState()
    {
      interfaceParentsLength    = interfacesToResolveParents.size();
      classParentsLength        = classesToResolveParents.size();
      enumParentsLength         = enumsToResolveParents.size();
      interfaceTypeBoundsLength = interfacesToResolveTypeBounds.size();
      classTypeBoundsLength     = classesToResolveTypeBounds.size();
      interfaceMembersLength    = interfacesToResolveMembers.size();
      classMembersLength        = classesToResolveMembers.size();
      enumMembersLength         = enumsToResolveMembers.size();
    }

    private boolean hasChanged()
    {
      return interfaceParentsLength    != interfacesToResolveParents.size()    ||
             classParentsLength        != classesToResolveParents.size()       ||
             enumParentsLength         != enumsToResolveParents.size()         ||
             interfaceTypeBoundsLength != interfacesToResolveTypeBounds.size() ||
             classTypeBoundsLength     != classesToResolveTypeBounds.size()    ||
             interfaceMembersLength    != interfacesToResolveMembers.size()    ||
             classMembersLength        != classesToResolveMembers.size()       ||
             enumMembersLength         != enumsToResolveMembers.size();
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
      classesToResolveParents.add(conceptualClass);
      addInnerTypes(conceptualClass.getInnerClasses(), conceptualClass.getInnerInterfaces(), conceptualClass.getInnerEnums());
    }
    for (ConceptualInterface conceptualInterface : file.getInterfaces())
    {
      interfacesToResolveParents.add(conceptualInterface);
      addInnerTypes(conceptualInterface.getInnerClasses(), conceptualInterface.getInnerInterfaces(), conceptualInterface.getInnerEnums());
    }
    for (ConceptualEnum conceptualEnum : file.getEnums())
    {
      enumsToResolveParents.add(conceptualEnum);
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
        classesToResolveParents.add(innerClass);
        addInnerTypes(innerClass.getInnerClasses(), innerClass.getInnerInterfaces(), innerClass.getInnerEnums());
      }
    }
    if (innerInterfaces != null)
    {
      for (ConceptualInterface innerInterface : innerInterfaces)
      {
        interfacesToResolveParents.add(innerInterface);
        addInnerTypes(innerInterface.getInnerClasses(), innerInterface.getInnerInterfaces(), innerInterface.getInnerEnums());
      }
    }
    if (innerEnums != null)
    {
      for (ConceptualEnum innerEnum : innerEnums)
      {
        enumsToResolveParents.add(innerEnum);
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
        resolved.getType() == ScopeType.INNER_ENUM ||
        resolved.getType() == ScopeType.TYPE_PARAMETER)
    {
      // make sure all but the last type argument list is null
      for (int i = 0; i < typeArgumentLists.length - 1; i++)
      {
        if (typeArgumentLists[i] != null)
        {
          throw new ConceptualException("Type arguments are not allowed on this name", pointerTypeAST.getNames()[i].getParseInfo());
        }
      }
      if (resolved.getType() == ScopeType.OUTER_ENUM ||
          resolved.getType() == ScopeType.INNER_ENUM)
      {
        // make sure the last type argument list is null, as enums do not have type parameters
        if (typeArgumentLists[typeArgumentLists.length - 1] != null)
        {
          throw new ConceptualException("Type arguments are not allowed on this name", pointerTypeAST.getNames()[typeArgumentLists.length - 1].getParseInfo());
        }
        return new EnumPointerType((ConceptualEnum) resolved, pointerTypeAST.isImmutable());
      }
      if (resolved.getType() == ScopeType.TYPE_PARAMETER)
      {
        // make sure the last type argument list is null, as type parameters do not have type parameters of their own
        if (typeArgumentLists[typeArgumentLists.length - 1] != null)
        {
          throw new ConceptualException("Type arguments are not allowed on this name", pointerTypeAST.getNames()[typeArgumentLists.length - 1].getParseInfo());
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
                                        pointerTypeAST.getNames()[typeArgumentLists.length - 1].getParseInfo());
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
        TypeArgumentAST[] arguments = typeArgumentLists[typeArgumentLists.length - classes.size()];
        if (arguments == null || arguments.length != currentClass.getTypeParameters().length)
        {
          int parameters = currentClass.getTypeParameters().length;
          throw new ConceptualException("Expected " + parameters + " type argument" + (parameters != 1 ? "s" : "") + " after this name, " +
                                        "but found " + (arguments == null ? "none" : arguments.length),
                                        pointerTypeAST.getNames()[typeArgumentLists.length - classes.size()].getParseInfo());
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

  /**
   * @return true if this resolver has no more data to resolve from any interfaces, classes or enums; false otherwise
   */
  public boolean finishedProcessing()
  {
    return interfacesToResolveParents.isEmpty()    && classesToResolveParents.isEmpty()    && enumsToResolveParents.isEmpty() &&
           interfacesToResolveTypeBounds.isEmpty() && classesToResolveTypeBounds.isEmpty() &&
           interfacesToResolveMembers.isEmpty();
  }

  /**
   * @return true if this resolver has more interfaces to resolve the parents of, false otherwise
   */
  public boolean hasUnresolvedInterfaces()
  {
    return !interfacesToResolveParents.isEmpty();
  }

  /**
   * @return true if this resolver has more classes to resolve the parents of, false otherwise
   */
  public boolean hasUnresolvedClasses()
  {
    return !classesToResolveParents.isEmpty();
  }

  /**
   * @return true if this resolver has more enums to resolve the parents of, false otherwise
   */
  public boolean hasUnresolvedEnums()
  {
    return !enumsToResolveParents.isEmpty();
  }

  /**
   * @return true if this resolver has more interfaces to resolve the type parameter bounds of, false otherwise
   */
  public boolean hasUnresolvedInterfaceTypeBounds()
  {
    return !interfacesToResolveTypeBounds.isEmpty();
  }

  /**
   * @return true if this resolver has more classes to resolve the type parameter bounds of, false otherwise
   */
  public boolean hasUnresolvedClassTypeBounds()
  {
    return !classesToResolveTypeBounds.isEmpty();
  }

  /**
   * @return true if this resolver has more interfaces to resolve the members of, false otherwise
   */
  public boolean hasUnresolvedInterfaceMembers()
  {
    return !interfacesToResolveMembers.isEmpty();
  }

  /**
   * Resolves the parent interfaces of all interfaces in the interfacesToResolve queue.
   * @param unresolvedParseInfo - the set containing the ParseInfo of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent interfaces
   */
  public boolean resolveInterfaceParents(Set<ParseInfo> unresolvedParseInfo) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    Set<ConceptualInterface> notFullyResolved = new HashSet<ConceptualInterface>();

    // try to resolve the parent interfaces of every interface
    // this uses a queue instead of an iterator because new interfaces can be added to the list while we are working on it
    // (e.g. if an interface extends an interface which has not been parsed yet)
    while (!interfacesToResolveParents.isEmpty())
    {
      ConceptualInterface toResolve = interfacesToResolveParents.poll();
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
        QueueState queueState = new QueueState();
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
        // add the interface to the next queue, to resolve its type parameters' bounds
        interfacesToResolveTypeBounds.add(toResolve);
      }
      else
      {
        // some parent interfaces still need filling in, so add this to the end of the queue again
        interfacesToResolveParents.add(toResolve);
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
  public boolean resolveClassParents(Set<ParseInfo> unresolvedParseInfo) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    Set<ConceptualClass> notFullyResolved = new HashSet<ConceptualClass>();

    // try to resolve the parent classes and interfaces of every class
    // this uses a queue instead of an iterator because new classes can be added to the list while we are working on it
    // (e.g. if a class extends another class which has not been parsed yet)
    while (interfacesToResolveParents.isEmpty() && !classesToResolveParents.isEmpty())
    {
      ConceptualClass toResolve = classesToResolveParents.poll();
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
          QueueState queueState = new QueueState();
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

            if (queueState.hasChanged())
            {
              // one of the queues was modified during the call to resolvePointerType(),
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
        QueueState queueState = new QueueState();
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

          if (queueState.hasChanged())
          {
            // one of the queues was modified during the call to resolvePointerType(),
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
        // add the class to the next queue, to resolve its type parameters' bounds
        classesToResolveTypeBounds.add(toResolve);
      }
      else
      {
        // some parent classes/interfaces still need filling in, so add this to the end of the queue again
        notFullyResolved.add(toResolve);
        classesToResolveParents.add(toResolve);
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
        throw new ConceptualException("Cycle detected in class inheritance hierarchy", parentAST.getParseInfo());
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
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer class", parentAST.getParseInfo());
          }
          InnerClass childClass = (InnerClass) child;
          TypeDefinition childOuterType = childClass.getParent();
          if (childClass.isStatic() || !(childOuterType instanceof ConceptualClass || childOuterType instanceof ConceptualEnum))
          {
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer class", parentAST.getParseInfo());
          }
          ConceptualClass currentClass;
          if (childOuterType instanceof ConceptualEnum)
          {
            // take the base class of the enum as the current class
            ClassPointerType baseClass = ((ConceptualEnum) childOuterType).getBaseClass();
            if (baseClass == null)
            {
              // we have not reached the top of the inheritance hierarchy, so wait until more of the hierarchy has been resolved
              throw new UnresolvableException("Error checking the parents of an inner class", parentAST.getParseInfo());
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
                throw new UnresolvableException("Error checking the parents of an inner class", parentAST.getParseInfo());
              }
              throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer class", parentAST.getParseInfo());
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
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer enum", parentAST.getParseInfo());
          }
          InnerClass childClass = (InnerClass) child;
          TypeDefinition childOuterType = childClass.getParent();
          if (childClass.isStatic() || !(childOuterType instanceof ConceptualEnum))
          {
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer enum", parentAST.getParseInfo());
          }
          if (!childOuterType.equals(parentOuterType))
          {
            throw new ConceptualException("Subclassing a non-static inner class requires an enclosing instance of the outer enum", parentAST.getParseInfo());
          }
        }
      }
    }
  }

  /**
   * Resolves the parent classes and interfaces of all enums in the enumsToResolve queue.
   * @param unresolvedParseInfo - the set containing the ParseInfo of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving parent classes/interfaces
   */
  public boolean resolveEnumParents(Set<ParseInfo> unresolvedParseInfo) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    Set<ConceptualEnum> notFullyResolved = new HashSet<ConceptualEnum>();

    // try to resolve the parent classes and interfaces of every class
    // this uses a queue instead of an iterator because new classes can be added to the list while we are working on it
    // (e.g. if a class extends another class which has not been parsed yet)
    while (interfacesToResolveParents.isEmpty() && classesToResolveParents.isEmpty() && !enumsToResolveParents.isEmpty())
    {
      ConceptualEnum toResolve = enumsToResolveParents.poll();
      if (notFullyResolved.contains(toResolve))
      {
        // all of the enums in the queue have been processed since a change has been made
        // (this depends on enumsToResolveParents being a queue)
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
          QueueState queueState = new QueueState();
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

            if (queueState.hasChanged())
            {
              // one of the queues was modified during the call to resolvePointerType(),
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
        QueueState queueState = new QueueState();
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

          if (queueState.hasChanged())
          {
            // one of the queues was modified during the call to resolvePointerType(),
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
        // add this enum to the next queue, to resolve its member variables' types
        enumsToResolveMembers.add(toResolve);
      }
      else
      {
        // some parent classes/interfaces still need filling in, so add this to the end of the queue again
        notFullyResolved.add(toResolve);
        enumsToResolveParents.add(toResolve);
      }
    }
    return changed;
  }

  /**
   * Resolves the bounds on each of the type parameters of the interfaces in the queue. Both super- and sub-type bounds are resolved.
   * @param unresolvedParseInfo - the set containing the ParseInfo of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving the type bounds
   */
  public boolean resolveInterfaceTypeParameterBounds(Set<ParseInfo> unresolvedParseInfo) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    Set<ConceptualInterface> notFullyResolved = new HashSet<ConceptualInterface>();

    while (interfacesToResolveParents.isEmpty() && classesToResolveParents.isEmpty() && enumsToResolveParents.isEmpty() &&
           !interfacesToResolveTypeBounds.isEmpty())
    {
      ConceptualInterface toResolve = interfacesToResolveTypeBounds.poll();
      if (notFullyResolved.contains(toResolve))
      {
        // all of the interfaces in the queue have been processed since a change has been made
        // (this depends on interfacesToResolveTypeBounds being a queue)
        return changed;
      }
      InterfaceDefinitionAST astNode = (InterfaceDefinitionAST) conceptualASTNodes.get(toResolve);
      TypeParameterAST[] parameterASTs = astNode.getTypeParameters();
      if (parameterASTs == null)
      {
        changed = true;
        notFullyResolved.clear();
        unresolvedParseInfo.clear();
        continue;
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
              unresolvedParseInfo.clear();
            }
            catch (UnresolvableException e)
            {
              fullyResolved = false;
              unresolvedParseInfo.add(superTypeASTs[j].getParseInfo());

              if (queueState.hasChanged())
              {
                // one of the queues was modified during the call to resolvePointerType(),
                // because a new file was loaded and addFile() was called.
                // this must count as a change, for reasons described above
                changed = true;
                notFullyResolved.clear();
                unresolvedParseInfo.clear();
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
              unresolvedParseInfo.clear();
            }
            catch (UnresolvableException e)
            {
              fullyResolved = false;
              unresolvedParseInfo.add(subTypeASTs[j].getParseInfo());

              if (queueState.hasChanged())
              {
                // one of the queues was modified during the call to resolvePointerType(),
                // because a new file was loaded and addFile() was called.
                // this must count as a change, for reasons described above
                changed = true;
                notFullyResolved.clear();
                unresolvedParseInfo.clear();
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
        unresolvedParseInfo.clear();
        // add this interface to the next queue, to resolve its member variables' types
        interfacesToResolveMembers.add(toResolve);
      }
      else
      {
        // some parent classes/interfaces still need filling in, so add this to the end of the queue again
        interfacesToResolveTypeBounds.add(toResolve);
        notFullyResolved.add(toResolve);
      }

    }

    return changed;
  }

  /**
   * Resolves the bounds on each of the type parameters of the classes in the queue. Both super- and sub-type bounds are resolved.
   * @param unresolvedParseInfo - the set containing the ParseInfo of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving the type bounds
   */
  public boolean resolveClassTypeParameterBounds(Set<ParseInfo> unresolvedParseInfo) throws NameConflictException, ConceptualException
  {
    boolean changed = false;
    Set<ConceptualClass> notFullyResolved = new HashSet<ConceptualClass>();

    while (interfacesToResolveParents.isEmpty() && classesToResolveParents.isEmpty() && enumsToResolveParents.isEmpty() &&
           interfacesToResolveTypeBounds.isEmpty() && !classesToResolveTypeBounds.isEmpty())
    {
      ConceptualClass toResolve = classesToResolveTypeBounds.poll();
      if (notFullyResolved.contains(toResolve))
      {
        // all of the classes in the queue have been processed since a change has been made
        // (this depends on classesToResolveTypeBounds being a queue)
        return changed;
      }
      ClassDefinitionAST astNode = (ClassDefinitionAST) conceptualASTNodes.get(toResolve);
      TypeParameterAST[] parameterASTs = astNode.getTypeParameters();
      if (parameterASTs == null)
      {
        changed = true;
        notFullyResolved.clear();
        unresolvedParseInfo.clear();
        continue;
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
              unresolvedParseInfo.clear();
            }
            catch (UnresolvableException e)
            {
              fullyResolved = false;
              unresolvedParseInfo.add(superTypeASTs[j].getParseInfo());

              if (queueState.hasChanged())
              {
                // one of the queues was modified during the call to resolvePointerType(),
                // because a new file was loaded and addFile() was called.
                // this must count as a change, for reasons described above
                changed = true;
                notFullyResolved.clear();
                unresolvedParseInfo.clear();
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
              unresolvedParseInfo.clear();
            }
            catch (UnresolvableException e)
            {
              fullyResolved = false;
              unresolvedParseInfo.add(subTypeASTs[j].getParseInfo());

              if (queueState.hasChanged())
              {
                // one of the queues was modified during the call to resolvePointerType(),
                // because a new file was loaded and addFile() was called.
                // this must count as a change, for reasons described above
                changed = true;
                notFullyResolved.clear();
                unresolvedParseInfo.clear();
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
        unresolvedParseInfo.clear();
      }
      else
      {
        // some parent classes/interfaces still need filling in, so add this to the end of the queue again
        classesToResolveTypeBounds.add(toResolve);
        notFullyResolved.add(toResolve);
      }

    }

    return changed;
  }

  /**
   * Resolves the types of the members of each of the interfaces in the queue.
   * @param unresolvedParseInfo - the set containing the ParseInfo of each QName which has been tried for resolution unsuccessfully since the last change was made
   * @return true if any successful processing was done, false otherwise
   * @throws NameConflictException - if a name conflict was detected while resolving a PointerType
   * @throws ConceptualException - if a conceptual problem occurs while resolving one of the members' types
   */
  public boolean resolveInterfaceMembers(Set<ParseInfo> unresolvedParseInfo) throws ConceptualException, NameConflictException
  {
    boolean changed = false;
    Set<ConceptualInterface> notFullyResolved = new HashSet<ConceptualInterface>();

    while (interfacesToResolveParents.isEmpty() && classesToResolveParents.isEmpty() && enumsToResolveParents.isEmpty() &&
           interfacesToResolveTypeBounds.isEmpty() && classesToResolveTypeBounds.isEmpty() &&
           !interfacesToResolveMembers.isEmpty())
    {
      ConceptualInterface toResolve = interfacesToResolveMembers.poll();
      if (notFullyResolved.contains(toResolve))
      {
        // all of the interfaces in the queue have been processed since a change has been made
        // (this depends on interfacesToResolveMembers being a queue)
        return changed;
      }
      QueueState queueState = new QueueState();
      try
      {
        for (Method method : toResolve.getMethods())
        {
          if (resolveMethodType(method, (MethodAST) conceptualASTNodes.get(method)))
          {
            changed = true;
            notFullyResolved.clear();
            unresolvedParseInfo.clear();
          }
        }
        for (Property property : toResolve.getProperties())
        {
          if (resolvePropertyType(property, (PropertyAST) conceptualASTNodes.get(property)))
          {
            changed = true;
            notFullyResolved.clear();
            unresolvedParseInfo.clear();
          }
        }
        for (MemberVariable variable : toResolve.getStaticVariables())
        {
          if (resolveVariableType(variable, (FieldAST) conceptualASTNodes.get(variable)))
          {
            changed = true;
            notFullyResolved.clear();
            unresolvedParseInfo.clear();
          }
        }
        // if we get here then everything has been processed
        // we have removed something from the queue, so a change has occurred
        changed = true;
        notFullyResolved.clear();
        unresolvedParseInfo.clear();
      }
      catch (UnresolvableException e)
      {
        notFullyResolved.add(toResolve);
        unresolvedParseInfo.add(e.getParseInfo());

        // some members still need filling in, so add this to the end of the queue again
        interfacesToResolveMembers.add(toResolve);

        if (queueState.hasChanged())
        {
          // one of the queues was modified during a call to resolvePointerType(),
          // because a new file was loaded and addFile() was called.
          // this must count as a change, for reasons described above
          changed = true;
          notFullyResolved.clear();
          unresolvedParseInfo.clear();
        }
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
  private boolean resolveVariableType(MemberVariable variable, FieldAST astNode) throws NameConflictException, ConceptualException, UnresolvableException
  {
    if (variable.getVariableType() == null)
    {
      variable.setVariableType(ASTConverter.convert(astNode.getType(), this, variable));
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

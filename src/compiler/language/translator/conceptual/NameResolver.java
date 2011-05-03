package compiler.language.translator.conceptual;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.ast.typeDefinition.ClassDefinitionAST;
import compiler.language.ast.typeDefinition.EnumDefinitionAST;
import compiler.language.ast.typeDefinition.InterfaceDefinitionAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.UnresolvableException;
import compiler.language.conceptual.topLevel.ConceptualFile;
import compiler.language.conceptual.type.AutomaticBaseTypeInstance;
import compiler.language.conceptual.type.ClassTypeInstance;
import compiler.language.conceptual.type.EnumTypeInstance;
import compiler.language.conceptual.type.InnerClassTypeInstance;
import compiler.language.conceptual.type.InnerEnumTypeInstance;
import compiler.language.conceptual.type.InnerInterfaceTypeInstance;
import compiler.language.conceptual.type.InterfaceTypeInstance;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.TypeArgument;
import compiler.language.conceptual.type.TypeArgumentInstance;
import compiler.language.conceptual.type.TypeInstance;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
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

  /**
   * Creates a new NameResolver with the specified mapping from Conceptual node to AST node.
   * @param conceptualASTNodes - the mapping which stores the AST node of each conceptual node which has been converted
   */
  public NameResolver(Map<Object, Object> conceptualASTNodes)
  {
    this.conceptualASTNodes = conceptualASTNodes;
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
      PointerType[] pointerTypes = toResolve.getSuperInterfaces();
      if (pointerTypes == null)
      {
        pointerTypes = new PointerType[parentInterfaces.length];
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
          PointerType parentPointerType = resolvePointerType(parentInterfaces[i], toResolve);
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
   * Checks the specified PointerType as being a valid parent interface. This just means checking that it represents an interface.
   * If a child interface is provided, this method checks that the child will not become one of its own parents.
   * @param parent - the parent interface's PointerType, to check
   * @param parentAST - the parent type's PointerTypeAST, to extract ParseInfo from in case of error
   * @param child - the prospective child interface, if any
   * @throws ConceptualException - if there is a problem and the PointerType is not a valid parent interface
   */
  private void checkParentInterface(PointerType parent, PointerTypeAST parentAST, ConceptualInterface child) throws ConceptualException
  {
    TypeInstance typeInstance = parent.getTypeInstance();
    if (!(typeInstance instanceof InterfaceTypeInstance))
    {
      throw new ConceptualException("Only interfaces can be implemented.", parentAST.getParseInfo());
    }
    if (child == null)
    {
      return;
    }

    // check that child is not parent or one of its parent interfaces, using a breadth first search
    ConceptualInterface startInterface = ((InterfaceTypeInstance) typeInstance).getInterfaceType();

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
      for (PointerType superType : current.getSuperInterfaces())
      {
        if (superType == null)
        {
          continue;
        }
        if (!(superType.getTypeInstance() instanceof InterfaceTypeInstance))
        {
          throw new IllegalStateException("A parent interface's PointerType has a non-interface type instance.");
        }
        queue.add(((InterfaceTypeInstance) superType.getTypeInstance()).getInterfaceType());
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
      PointerType baseClass = toResolve.getBaseClass();
      if (baseClass == null)
      {
        if (baseClassAST == null)
        {
          baseClass = new PointerType(false, new AutomaticBaseTypeInstance());
          toResolve.setBaseClass(baseClass);
          changed = true;
          unresolvedSinceLastChange.clear();
        }
        else
        {
          int queueSize = classesToResolve.size();
          try
          {
            baseClass = resolvePointerType(baseClassAST, toResolve);
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
      PointerType[] parentInterfaces = toResolve.getInterfaces();
      if (parentInterfaces == null)
      {
        parentInterfaces = new PointerType[parentInterfaceASTs.length];
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
          PointerType parentInterface = resolvePointerType(parentInterfaceASTs[i], toResolve);
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
   * Checks the specified PointerType as being a valid parent class. This just means checking that it represents a class.
   * If a child class is provided, this method checks that the child will not become one of its own parents.
   * @param parent - the parent class's PointerType, to check
   * @param parentAST - the parent type's PointerTypeAST, to extract ParseInfo from in case of error
   * @param child - the prospective child class, if any
   * @throws ConceptualException - if there is a problem and the PointerType is not a valid parent class
   */
  private void checkParentClass(PointerType parent, PointerTypeAST parentAST, ConceptualClass child) throws ConceptualException
  {
    TypeInstance typeInstance = parent.getTypeInstance();
    if (!(typeInstance instanceof ClassTypeInstance))
    {
      throw new ConceptualException("Only classes can be extended", parentAST.getParseInfo());
    }
    if (child == null)
    {
      return;
    }

    // check that child is not parent or one of its base classes
    ConceptualClass current = ((ClassTypeInstance) typeInstance).getClassType();
    while (current != null)
    {
      if (current.equals(child))
      {
        throw new ConceptualException("Cycle detected in class inheritance hierarchy", parentAST.getParseInfo());
      }
      if (current.getBaseClass() == null)
      {
        break;
      }
      TypeInstance baseTypeInstance = current.getBaseClass().getTypeInstance();
      if (baseTypeInstance instanceof AutomaticBaseTypeInstance)
      {
        break;
      }
      if (!(baseTypeInstance instanceof ClassTypeInstance))
      {
        throw new IllegalStateException("A parent class' PointerType has a non-class type instance.");
      }
      current = ((ClassTypeInstance) baseTypeInstance).getClassType();
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
      PointerType baseClass = toResolve.getBaseClass();
      if (baseClass == null)
      {
        if (baseClassAST == null)
        {
          baseClass = new PointerType(false, new AutomaticBaseTypeInstance());
          toResolve.setBaseClass(baseClass);
          changed = true;
          unresolvedSinceLastChange.clear();
        }
        else
        {
          int queueSize = enumsToResolve.size();
          try
          {
            baseClass = resolvePointerType(baseClassAST, toResolve);
            checkParentClass(baseClass, baseClassAST, null);
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
      PointerType[] parentInterfaces = toResolve.getInterfaces();
      if (parentInterfaces == null)
      {
        parentInterfaces = new PointerType[parentInterfaceASTs.length];
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
          PointerType parentInterface = resolvePointerType(parentInterfaceASTs[i], toResolve);
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
    NameAST[] names = pointerTypeAST.getNames();
    TypeParameterAST[][] typeParameterLists = pointerTypeAST.getTypeParameterLists();

    // go through the names, and look them up in current each iteration to find the new current resolvable
    // also build a type instance hierarchy while doing this, to give to the PointerType
    Resolvable current = startScope.resolve(new QName(names[0].getName()), true);
    TypeInstance currentTypeInstance = null;
    for (int i = 0; i < names.length; i++)
    {
      if (i > 0) // the first name is looked up separately
      {
        // lookup this name in the current Resolvable object
        // this can fail if the Resolvable has not had all of its data filled in yet,
        // for example if current is a class and does not contain this name itself but must fall back on a not-yet-filled-in parent class/interface
        current = current.resolve(names[i].getName());
      }
      if (current == null)
      {
        throw new ConceptualException("Could not resolve name", names[i].getParseInfo());
      }
      switch (current.getType())
      {
      case PACKAGE:
        if (typeParameterLists[i].length > 0)
        {
          ParseInfo[] parseInfo = new ParseInfo[typeParameterLists[i].length];
          for (int j = 0; j < typeParameterLists[i].length; j++)
          {
            parseInfo[j] = typeParameterLists[i][j].getParseInfo();
          }
          throw new ConceptualException("A package cannot have type parameters", parseInfo);
        }
        break;
      case OUTER_CLASS:
        if (currentTypeInstance != null)
        {
          throw new IllegalStateException();
        }
        currentTypeInstance = new ClassTypeInstance((ConceptualClass) current,
                                                    ASTConverter.convert(typeParameterLists[i], this, startScope));
        break;
      case INNER_CLASS:
        if (currentTypeInstance == null)
        {
          throw new IllegalStateException();
        }
        currentTypeInstance = new InnerClassTypeInstance((ConceptualClass) current,
                                                         ASTConverter.convert(typeParameterLists[i], this, startScope),
                                                         currentTypeInstance);
        break;
      case OUTER_INTERFACE:
        if (currentTypeInstance != null)
        {
          throw new IllegalStateException();
        }
        currentTypeInstance = new InterfaceTypeInstance((ConceptualInterface) current,
                                                        ASTConverter.convert(typeParameterLists[i], this, startScope));
        break;
      case INNER_INTERFACE:
        if (currentTypeInstance == null)
        {
          throw new IllegalStateException();
        }
        currentTypeInstance = new InnerInterfaceTypeInstance((ConceptualInterface) current,
                                                             ASTConverter.convert(typeParameterLists[i], this, startScope),
                                                             currentTypeInstance);
        break;
      case OUTER_ENUM:
        if (currentTypeInstance != null)
        {
          throw new IllegalStateException();
        }
        if (typeParameterLists[i].length > 0)
        {
          ParseInfo[] parseInfo = new ParseInfo[typeParameterLists[i].length];
          for (int j = 0; j < typeParameterLists[i].length; j++)
          {
            parseInfo[j] = typeParameterLists[i][j].getParseInfo();
          }
          throw new ConceptualException("An enum cannot have type parameters", parseInfo);
        }
        currentTypeInstance = new EnumTypeInstance((ConceptualEnum) current);
        break;
      case INNER_ENUM:
        if (currentTypeInstance == null)
        {
          throw new IllegalStateException();
        }
        if (typeParameterLists[i].length > 0)
        {
          ParseInfo[] parseInfo = new ParseInfo[typeParameterLists[i].length];
          for (int j = 0; j < typeParameterLists[i].length; j++)
          {
            parseInfo[j] = typeParameterLists[i][j].getParseInfo();
          }
          throw new ConceptualException("An enum cannot have type parameters", parseInfo);
        }
        currentTypeInstance = new InnerEnumTypeInstance((ConceptualEnum) current, currentTypeInstance);
        break;
      case TYPE_ARGUMENT:
        // TODO: check this
        currentTypeInstance = new TypeArgumentInstance((TypeArgument) current);
        break;
      default:
        throw new ConceptualException("Cannot reference a " + current.getType() + " in a pointer type", names[i].getParseInfo());
      }
    }
    if (currentTypeInstance == null)
    {
      // the type instance can only be null if the last name processed was a package
      throw new ConceptualException("Cannot create a pointer type to represent a package", pointerTypeAST.getParseInfo());
    }
    return new PointerType(pointerTypeAST.isImmutable(), currentTypeInstance);
  }
}

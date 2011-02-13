package compiler.language.translator.conceptual;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.ast.topLevel.ImportDeclarationAST;
import compiler.language.ast.topLevel.TypeDefinitionAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.Scope;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.member.MemberVariable;
import compiler.language.conceptual.member.Method;
import compiler.language.conceptual.member.Property;
import compiler.language.conceptual.misc.AccessSpecifier;
import compiler.language.conceptual.type.ClassTypeInstance;
import compiler.language.conceptual.type.InterfaceTypeInstance;
import compiler.language.conceptual.type.PointerType;
import compiler.language.conceptual.type.TypeInstance;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;

/*
 * Created on 27 Jan 2011
 */

/**
 * @author Anthony Bryant
 */
public class NameResolver
{

  // TODO: load and resolve this first
  private static final String[] UNIVERSAL_BASE_TYPE_LOCATION = new String[] {"x", "Object"};

  private ConceptualClass universalBaseType;

  private Scope rootScope;
  private Map<Object, Scope> astScopes;

  private Queue<CompilationUnitAST> toResolveImports;
  private Queue<TypeDefinitionAST>  toResolveParents;
  private Queue<TypeDefinitionAST>  toResolveTypes;
  private Queue<TypeDefinitionAST>  toResolveBlocks; // TODO: change the type of object in this queue?

  /**
   * Creates a new NameResolver to resolve names on the specified compilation units.
   * @param rootScope - the root scope that contains the fully qualified names and maps them to the conceptual hierarchy
   * @param compilationUnits - the compilation units to resolve the qualified names in
   * @param astScopes - a map from the AST nodes in the compilation units to scope objects
   */
  public NameResolver(List<CompilationUnitAST> compilationUnits, Scope rootScope, Map<Object, Scope> astScopes)
  {
    this.rootScope = rootScope;
    this.astScopes = astScopes;
    this.toResolveImports = new LinkedList<CompilationUnitAST>(compilationUnits);
    this.toResolveParents = new LinkedList<TypeDefinitionAST>();
    this.toResolveTypes   = new LinkedList<TypeDefinitionAST>();
    this.toResolveBlocks  = new LinkedList<TypeDefinitionAST>();
  }

  /**
   * Adds the specified compilation unit to the resolving queue.
   * @param compilationUnit - the compilation unit to add
   */
  public void addCompilationUnit(CompilationUnitAST compilationUnit)
  {
    toResolveImports.add(compilationUnit);
  }

  /**
   * Resolves all of the names in all compilation units that have been added to this NameResolver
   * (as well as any compilation units which are loaded during name resolution)
   * @throws ConceptualException - if there is an error during name resolution
   */
  public void resolveNames() throws ConceptualException
  {
    // resolve the universal base type, so that it can be distinguished from other objects and we can tell which classes do not yet have their base types filled in
    try
    {
      Scope baseTypeScope = resolveType(UNIVERSAL_BASE_TYPE_LOCATION, rootScope, new ParseInfo[UNIVERSAL_BASE_TYPE_LOCATION.length]);
      if (baseTypeScope.getType() != ScopeType.CLASS)
      {
        throw new IllegalStateException("Universal base type does not resolve to a class. Instead it resolves to: " + baseTypeScope.getType());
      }
      universalBaseType = (ConceptualClass) baseTypeScope.getValue();
    }
    catch (ConceptualException e)
    {
      throw new IllegalStateException("Failed to resolve the universal base type.", e);
    }

    // do not finish until everything has been resolved
    while (!toResolveImports.isEmpty() || !toResolveParents.isEmpty() || !toResolveTypes.isEmpty() || !toResolveBlocks.isEmpty())
    {

      // resolve all imports
      while (!toResolveImports.isEmpty())
      {
        CompilationUnitAST toResolve = toResolveImports.poll();
        resolveImports(toResolve);
        for (TypeDefinitionAST typeDefinition : toResolve.getTypes())
        {
          toResolveParents.add(typeDefinition);
        }
      }

      // resolve the inheritance hierarchies
      while (toResolveImports.isEmpty() && !toResolveParents.isEmpty())
      {
        // TODO: next resolve the inheritance hierarchies, as their scopes need to be combined
        // also, check that the inheritance hierarchy is not at all cyclic
        // this will require a "while (!resolvedMore)" loop, as some supertypes may need to be resolved before others can be
        // i.e. if A extends B and A has an inner class X, and Foo extends B.X
      }

      // TODO: do the rest, including generating the missing parts of the conceptual hierarchy
    }
  }

  /**
   * Resolves the imports of the specified compilation unit.
   * @param compilationUnit - the compilation unit to resolve the imports of
   * @throws ConceptualException - if an import cannot be resolved, even by loading more classes
   */
  private void resolveImports(CompilationUnitAST compilationUnit) throws ConceptualException
  {
    Scope fileScope = astScopes.get(compilationUnit);
    ImportDeclarationAST[] imports = compilationUnit.getImports();
    for (ImportDeclarationAST importDeclaration : imports)
    {
      String[] nameStrings = importDeclaration.getName().getNameStrings();
      Scope importScope = rootScope.lookup(nameStrings); // TODO: change to another resolve() method
      if (importScope == null) // TODO: eventually this null check will become unnecessary, as the new resolve method will throw an exception instead of returning null
      {
        // TODO: accumulate error messages instead of just throwing one
        throw new ConceptualException("Unresolved import: " + importDeclaration.getName(),
                                      importDeclaration.getName().getParseInfo());
      }
      if (importDeclaration.isAll())
      {
        for (Entry<String, Scope> entry : importScope.getChildren().entrySet())
        {
          importScopeEntry(entry.getKey(), entry.getValue(), fileScope, importDeclaration.getParseInfo());
        }
      }
      else
      {
        String name = nameStrings[nameStrings.length - 1];
        importScopeEntry(name, importScope, fileScope, importDeclaration.getParseInfo());
      }
    }
  }

  /**
   * Attempts to import the specified importScope into fileScope, under the specified name.
   * This method checks that importScope is of a valid scope type and can be imported into a file's scope.
   * @param name - the name to try to import the scope under
   * @param importScope - the scope to try to import
   * @param fileScope - the file scope to import the scope into
   * @param importParseInfo - the ParseInfo of the import statement, in case a name conflict is detected
   * @throws ConceptualException - if there is a conflict between the new name and an existing one
   */
  private void importScopeEntry(String name, Scope importScope, Scope fileScope, ParseInfo importParseInfo) throws ConceptualException
  {
    ScopeType type = importScope.getType();
    if ( type == ScopeType.PACKAGE       ||
         type == ScopeType.CLASS         ||
         type == ScopeType.INTERFACE     ||
         type == ScopeType.ENUM          ||
         type == ScopeType.ENUM_CONSTANT ||
        (type == ScopeType.MEMBER_VARIABLE && ((MemberVariable) importScope.getValue()).isStatic()) ||
        (type == ScopeType.PROPERTY        && ((Property)       importScope.getValue()).isStatic()) ||
        (type == ScopeType.METHOD          && ((Method)         importScope.getValue()).isStatic()))
    {
      try
      {
        fileScope.addChild(name, importScope);
      }
      catch (ScopeException e)
      {
        throw new ConceptualException("Conflicting import declarations: " + name, importParseInfo);
      }
    }
  }

  // TODO: write a new resolve method here, which takes into account inheritance and Access Specifiers
  // then use it from resolveImports() and everywhere else in this class
  // it should also try to load external QNames from files if possible
  // if it fails to resolve a name, it should throw an exception instead of returning null

  // TODO: this should return sets/lists of matching Scopes in case there are multiple methods with the same name but different arguments
  // or even a method on a subclass with the same name as one in the superclass

  private Scope resolveType(String[] nameStrings, Scope initialScope, ParseInfo[] namesParseInfo) throws ConceptualException
  {
    // TODO: check and finish
    Scope root = initialScope;
    while (root != null)
    {
      Scope current = root;
      for (int i = 0; i < nameStrings.length; i++)
      {
        String name = nameStrings[i];
        current = current.getChild(name);
        if (current == null)
        {
          break;
        }
        try
        {
          switch (current.getType())
          {
          case PACKAGE:
            // packages are always accessible
            break;
          case CLASS:
            // check the class' access specifier
            ConceptualClass testClass = (ConceptualClass) current.getValue();
            if (!isAccessible(current, initialScope, testClass.getAccessSpecifier()))
            {
              throw new ConceptualException("Unable to access " + testClass.getAccessSpecifier() + " class: " + name, namesParseInfo[i]);
            }
            break;
          case INTERFACE:
            // check the interface's access specifier
            ConceptualInterface testInterface = (ConceptualInterface) current.getValue();
            if (!isAccessible(current, initialScope, testInterface.getAccessSpecifier()))
            {
              throw new ConceptualException("Unable to access " + testInterface.getAccessSpecifier() + " interface: " + name, namesParseInfo[i]);
            }
            break;
          case ENUM:
            // check the enum's access specifier
            ConceptualEnum testEnum = (ConceptualEnum) current.getValue();
            if (!isAccessible(current, initialScope, testEnum.getAccessSpecifier()))
            {
              throw new ConceptualException("Unable to access " + testEnum.getAccessSpecifier() + " enum: " + name, namesParseInfo[i]);
            }
            break;
          case TYPE_ARGUMENT:
            // TODO: decide how this should work, it is only accessible given that it is being accessed from a non-static context in the class which the type argument is defined in
            break;
          default:

            break;
          }
        }
        catch (ScopeException e)
        {
          throw new ConceptualException("Unable to determine whether the access to \"" + name + "\" is legal.", namesParseInfo[i]);
        }
      }
      if (current != null)
      {
        return current;
      }
      root = root.getParent();
    }
    // TODO: load from a file (try from the root package and the current package)
    throw new ConceptualException("Could not resolve qualified name.", ParseInfo.combine(namesParseInfo));
  }

  /**
   * Checks whether the specified scope can be accessed from the other scope, given the access specifier
   * @param accessed - the scope that is being accessed
   * @param from - the scope that the access is coming from
   * @param accessSpecifier - the access specifier of the value of the scope being accessed
   * @return true if the access is legal, false if it is definitely not legal
   * @throws ScopeException - if the legality of the access is currently unknown, due to an incomplete type hierarchy
   */
  private boolean isAccessible(Scope accessed, Scope from, AccessSpecifier accessSpecifier) throws ScopeException
  {
    switch (accessSpecifier)
    {
    case PUBLIC:
      return true;
    case PACKAGE:
      return getEnclosingPackage(accessed).equals(getEnclosingPackage(from));
    case PACKAGE_PROTECTED:
      return getEnclosingPackage(accessed).equals(getEnclosingPackage(from)) ||
             isProtectedlyAccessible(accessed, from);
    case PROTECTED:
      return isProtectedlyAccessible(accessed, from);
    case PRIVATE:
      return isPrivatelyAccessible(accessed, from);
    default:
      throw new IllegalStateException("Invalid Access Specifier");
    }
  }


  /**
   * Finds the package that the specified scope was declared in.
   * @param scope - the scope to find the enclosing package of
   * @return the enclosing package scope of the specified scope
   */
  private static Scope getEnclosingPackage(Scope scope)
  {
    Scope current = scope;
    while (current != null)
    {
      if (current.getType() == ScopeType.PACKAGE)
      {
        return current;
      }
      current = current.getParent();
    }
    throw new IllegalStateException("Top level scope was not a PACKAGE scope.");
  }

  /**
   * Determines whether the specified scope can be accessed from the other scope, given that the access specifier is PROTECTED
   * @param accessed - the scope that is being accessed
   * @param from - the scope that the access is coming from
   * @return true if the access is legal, false if it is definitely not legal
   * @throws ScopeException - if the legality of the access is currently unknown, due to an incomplete type hierarchy
   */
  private boolean isProtectedlyAccessible(Scope accessed, Scope from) throws ScopeException // TODO: make sure this exception is always caught, as it just means that we cannot yet make a decision for sure
  {
    // find the first class/interface/enum scope above the accessed scope
    // this should always just be the parent scope of accessed, since things with access specifiers can only go directly inside a class/interface/enum
    Scope protectedTo = accessed.getParent();

    // go up through the parents of from, and check whether any of them are inherited from protectedTo
    Scope current = from;
    while (current != null)
    {
      // check whether current is descended from protectedTo, and throw a ScopeException if nothing matches but not all of the supertypes of current have yet been filled in
      Queue<ConceptualClass> classes = new LinkedList<ConceptualClass>();
      Queue<ConceptualInterface> interfaces = new LinkedList<ConceptualInterface>();
      Queue<ConceptualEnum> enums = new LinkedList<ConceptualEnum>();
      boolean foundAll = true;

      if (current.getType() == ScopeType.CLASS)
      {
        classes.add((ConceptualClass) current.getValue());
      }
      if (current.getType() == ScopeType.INTERFACE)
      {
        interfaces.add((ConceptualInterface) current.getValue());
      }
      if (current.getType() == ScopeType.ENUM)
      {
        enums.add((ConceptualEnum) current.getValue());
      }

      // maintain a visited set in this breadth-first search, in case there is a loop in the inheritance hierarchy
      Set<Object> visited = new HashSet<Object>();
      while (!enums.isEmpty() || !classes.isEmpty() || !interfaces.isEmpty())
      {
        // determine whether the next item is protectedTo
        // if it is not, fill in the base class and interfaces that we should search in future
        PointerType baseClass = null;
        PointerType[] superInterfaces;
        boolean hasBaseClass = true; // should be true if we are expecting this type to have a base class
        if (!classes.isEmpty())
        {
          ConceptualClass testClass = classes.poll();
          if (visited.contains(testClass))
          {
            continue;
          }
          if (testClass.equals(protectedTo.getValue()))
          {
            return true;
          }
          visited.add(testClass);
          // do not try to look at the universal base class' base class
          if (testClass.equals(universalBaseType))
          {
            hasBaseClass = false;
          }
          else
          {
            baseClass = testClass.getBaseClass();
          }
          superInterfaces = testClass.getInterfaces();
        }
        else if (!interfaces.isEmpty())
        {
          ConceptualInterface testInterface = interfaces.poll();
          if (visited.contains(testInterface))
          {
            continue;
          }
          if (testInterface.equals(protectedTo.getValue()))
          {
            return true;
          }
          visited.add(testInterface);
          hasBaseClass = false;
          superInterfaces = testInterface.getSuperInterfaces();
        }
        else // !enums.isEmpty()
        {
          ConceptualEnum testEnum = enums.poll();
          if (visited.contains(testEnum))
          {
            continue;
          }
          if (testEnum.equals(protectedTo.getValue()))
          {
            return true;
          }
          visited.add(testEnum);
          baseClass = testEnum.getBaseClass();
          superInterfaces = testEnum.getInterfaces();
        }

        if ((hasBaseClass && baseClass == null) || superInterfaces == null)
        {
          // something has not yet been filled in
          foundAll = false;
        }
        if (baseClass != null)
        {
          TypeInstance baseClassTypeInstance = baseClass.getTypeInstance();
          if (!(baseClassTypeInstance instanceof ClassTypeInstance))
          {
            throw new IllegalStateException("A base class cannot have a TypeInstance which is not a ClassTypeInstance");
          }
          classes.add(((ClassTypeInstance) baseClassTypeInstance).getClassType());
        }
        if (superInterfaces != null)
        {
          for (PointerType interfaceType : superInterfaces)
          {
            TypeInstance superInterfaceTypeInstance = interfaceType.getTypeInstance();
            if (!(superInterfaceTypeInstance instanceof InterfaceTypeInstance))
            {
              throw new IllegalStateException("An implemented interface cannot have a TypeInstance which is not an InterfaceTypeInstance");
            }
            interfaces.add(((InterfaceTypeInstance) superInterfaceTypeInstance).getInterfaceType());
          }
        }
      }
      if (!foundAll)
      {
        throw new ScopeException("Could not determine whether a scope was protectedly accessible, certain parts of the type hierarchy have not yet been filled in.");
      }

      current = current.getParent();
    }
    return false;
  }

  /**
   * Checks whether the specified scope can be accessed from the specified other scope, given that the accessSpecifier is PRIVATE
   * @param accessed - the scope that is being accessed
   * @param from - the scope that contains the code trying to access the scope
   * @return true if the scope is accessible from this context, false otherwise
   */
  private static boolean isPrivatelyAccessible(Scope accessed, Scope from)
  {
    // find the outermost non-package scope from accessed, this will be the scope that it is private to
    Scope privateTo = accessed;
    while (privateTo.getParent() != null && privateTo.getParent().getType() != ScopeType.PACKAGE)
    {
      privateTo = privateTo.getParent();
    }
    // go up through the parents of from, and check whether any of them are privateTo
    Scope current = from;
    while (current != null)
    {
      if (privateTo.equals(current))
      {
        return true;
      }
      current = current.getParent();
    }
    return false;
  }

  /**
   * Attempts to load the file which contains the specified qualified name into the scope hierarchy.
   * @param nameStrings - the parts of the qualified name
   */
  private void findExternalQName(String[] nameStrings)
  {
    // TODO: lookup the specified QName in files which have not yet been loaded


    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < nameStrings.length; i++)
    {
      buffer.append(nameStrings[i]);
      if (i != nameStrings.length - 1)
      {
        buffer.append(".");
      }
    }
    throw new UnsupportedOperationException("Unable to find QName externally: " + buffer.toString());
  }
}

package compiler.language.translator.conceptual;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

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

/*
 * Created on 27 Jan 2011
 */

/**
 * @author Anthony Bryant
 */
public class NameResolver
{

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
      Scope importScope = rootScope.lookup(nameStrings);
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

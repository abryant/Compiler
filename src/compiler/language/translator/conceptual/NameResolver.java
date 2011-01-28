package compiler.language.translator.conceptual;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.ast.topLevel.ImportDeclarationAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.Scope;

/*
 * Created on 27 Jan 2011
 */

/**
 * @author Anthony Bryant
 */
public class NameResolver
{

  private Queue<CompilationUnitAST> toResolve;
  private Scope rootScope;
  private Map<Object, Scope> astScopes;

  /**
   * Creates a new NameResolver to resolve names on the specified compilation units.
   * @param rootScope - the root scope that contains the fully qualified names and maps them to the conceptual hierarchy
   * @param compilationUnits - the compilation units to resolve the qualified names in
   * @param astScopes - a map from the AST nodes in the compilation units to scope objects
   */
  public NameResolver(List<CompilationUnitAST> compilationUnits, Scope rootScope, Map<Object, Scope> astScopes)
  {
    this.rootScope = rootScope;
    this.toResolve = new LinkedList<CompilationUnitAST>(compilationUnits);
    this.astScopes = astScopes;
  }

  /**
   * Adds the specified compilation unit to the resolving queue.
   * @param compilationUnit - the compilation unit to add
   */
  public void addCompilationUnit(CompilationUnitAST compilationUnit)
  {
    toResolve.add(compilationUnit);
  }

  public void resolveNames()
  {
    // TODO: this method should not care if more compilation units are added via addCompilationUnit() at any point during resolution
    // it will not happen in another thread, but if names that cannot be resolved are found, more compilation units may be parsed and added here

    // TODO: first resolve all of the imports

    // TODO: next resolve the inheritance hierarchies, as their scopes need to be combined
    // also, check that the inheritance hierarchy is not at all cyclic
    // this will require a "while (!resolvedMore)" loop, as some supertypes may need to be resolved before others can be
    // i.e. if A extends B and A has an inner class X, and Foo extends B.X

    // TODO: do the rest, including generating the missing parts of the conceptual hierarchy
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
      if (importScope == null)
      {
        findExternalQName(nameStrings);
        importScope = rootScope.lookup(nameStrings);
        if (importScope == null)
        {
          // TODO: accumulate error messages instead of just throwing one
          throw new ConceptualException("Unresolved import: " + importDeclaration.getName(),
                                        importDeclaration.getName().getParseInfo());
        }
      }
      // TODO: how should static imports be handled here?
      // TODO: only allow certain scope types to be imported
      if (importDeclaration.isAll())
      {
        fileScope.copyChildren(importScope);
      }
      else
      {
        String name = nameStrings[nameStrings.length - 1];
        try
        {
          fileScope.addChild(name, importScope);
        }
        catch (ScopeException e)
        {
          // TODO: add the other import's ParseInfo to the exception
          throw new ConceptualException("Conflicting import declarations: " + name, importDeclaration.getParseInfo());
        }
      }
    }
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
        buffer.append(", ");
      }
    }
    throw new UnsupportedOperationException("Unable to find QName externally: " + buffer.toString());
  }
}

package compiler.language.translator.conceptual;

import java.util.Map;
import java.util.Set;

import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.ast.topLevel.ImportDeclarationAST;
import compiler.language.conceptual.ConceptualException;

/*
 * Created on 14 Nov 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualTranslator
{

  private Scope rootScope;
  private Set<CompilationUnitAST> compilationUnits;
  private NameScanner nameScanner;

  /**
   * Creates a new ConceptualTranslator for the specified compilation units.
   * @param compilationUnits - the compilation units to translate
   */
  public ConceptualTranslator(Set<CompilationUnitAST> compilationUnits)
  {
    this.compilationUnits = compilationUnits;
    rootScope = ScopeFactory.createRootScope();
    nameScanner = new NameScanner(rootScope);
  }

  /**
   * Translates the ASTs provided to this object's constructor into a conceptual object heirarchy.
   */
  public void translate()
  {
    try
    {
      for (CompilationUnitAST compilationUnit : compilationUnits)
      {
        nameScanner.scanCompilationUnit(compilationUnit);
      }
    }
    catch (ScopeException e)
    {
      e.printStackTrace();
    }
    catch (ConceptualException e)
    {
      e.printStackTrace();
    }

  }

  private void resolveImports() throws ConceptualException
  {
    Map<CompilationUnitAST, Scope> fileScopes = nameScanner.getFileScopes();
    for (CompilationUnitAST compilationUnit : compilationUnits)
    {
      Scope fileScope = fileScopes.get(compilationUnit);
      ImportDeclarationAST[] imports = compilationUnit.getImports();
      for (ImportDeclarationAST importDeclaration : imports)
      {
        Scope importScope = fileScope.lookup(importDeclaration.getName().getNameStrings());
        if (importScope == null)
        {
          // TODO: accumulate error messages instead of just throwing one
          throw new ConceptualException("Unresolved import: " + importDeclaration.getName(),
                                        importDeclaration.getName().getParseInfo());
        }
        if (importDeclaration.isAll())
        {
          fileScope.copyChildren(importScope);
        }
        else
        {
          NameAST[] names = importDeclaration.getName().getNames();
          String name = names[names.length - 1].getName();
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
  }

}

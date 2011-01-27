package compiler.language.translator.conceptual;

import java.util.List;
import java.util.Map;

import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.ast.topLevel.ImportDeclarationAST;
import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.ConceptualProgram;
import compiler.language.conceptual.Scope;

/*
 * Created on 14 Nov 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConceptualTranslator
{

  private Scope rootScope;
  private ConceptualProgram program;
  private List<CompilationUnitAST> compilationUnits;
  private ASTConverter astConverter;

  /**
   * Creates a new ConceptualTranslator for the specified compilation units.
   * @param compilationUnits - the compilation units to translate
   */
  public ConceptualTranslator(List<CompilationUnitAST> compilationUnits)
  {
    this.compilationUnits = compilationUnits;
    rootScope = ScopeFactory.createRootScope();
    program = new ConceptualProgram();
    astConverter = new ASTConverter(program, rootScope);
  }

  /**
   * Translates the ASTs provided to this object's constructor into a conceptual object hierarchy.
   */
  public void translate()
  {
    try
    {
      for (CompilationUnitAST compilationUnit : compilationUnits)
      {
        astConverter.convert(compilationUnit);
      }
      resolveImports();
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
    Map<Object, Scope> scopes = astConverter.getScopes();

    // TODO: avoid the ConcurrentModificationException here, which would be caused by adding to compilationUnits while looping over it
    for (CompilationUnitAST compilationUnit : compilationUnits)
    {
      Scope fileScope = scopes.get(compilationUnit);
      ImportDeclarationAST[] imports = compilationUnit.getImports();
      for (ImportDeclarationAST importDeclaration : imports)
      {
        String[] nameStrings = importDeclaration.getName().getNameStrings();
        Scope importScope = rootScope.lookup(nameStrings);
        if (importScope == null)
        {
          importScope = lookupExternalQualifiedName(nameStrings);
          if (importScope == null)
          {
            // TODO: accumulate error messages instead of just throwing one
            throw new ConceptualException("Unresolved import: " + importDeclaration.getName(),
                                          importDeclaration.getName().getParseInfo());
          }
        }
        // TODO: how should static imports be handled here?
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
  }

  /**
   * Looks up the specified qualified name in compilation units which have not been loaded into the AST Converter.
   * @param nameStrings - the qualified name as an array of strings
   * @return the newly-loaded scope which corresponds to the specified qualified name
   */
  private Scope lookupExternalQualifiedName(String[] nameStrings)
  {
    // TODO: lookup the specified name strings in files which have not yet been loaded
    // if a file likely to contain it is found, load it into astConverter and try to resolve the scope again
    return null;
  }

}

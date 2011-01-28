package compiler.language.translator.conceptual;

import java.util.List;

import compiler.language.ast.topLevel.CompilationUnitAST;
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
  private NameResolver nameResolver;

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
    nameResolver = new NameResolver(compilationUnits, rootScope, astConverter.getScopes());
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

      nameResolver.resolveNames();
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

}

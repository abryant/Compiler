package compiler.language.parser.rules;

import static compiler.language.parser.Type.IMPORT_DECLARATIONS;
import static compiler.language.parser.Type.PACKAGE_DECLARATION;
import static compiler.language.parser.Type.TYPE_DEFINITIONS;

import compiler.language.ast.CompilationUnit;
import compiler.language.ast.ImportDeclaration;
import compiler.language.ast.PackageDeclaration;
import compiler.language.ast.TypeDefinition;
import compiler.language.parser.Type;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class CompilationUnitRule extends Rule
{
  private static final Object[] PRODUCTION = new Object[] {PACKAGE_DECLARATION, IMPORT_DECLARATIONS, TYPE_DEFINITIONS};

  public CompilationUnitRule()
  {
    super(Type.COMPILATION_UNIT, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new CompilationUnit((PackageDeclaration) args[0], (ImportDeclaration[]) args[1], (TypeDefinition[]) args[2]);
    }
    throw badTypeList();
  }

}

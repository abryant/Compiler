package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.COMPILATION_UNIT;
import static compiler.language.parser.ParseType.IMPORT_DECLARATION;
import static compiler.language.parser.ParseType.PACKAGE_DECLARATION;
import static compiler.language.parser.ParseType.TYPE_DEFINITION;

import compiler.language.ast.topLevel.CompilationUnit;
import compiler.language.ast.topLevel.ImportDeclaration;
import compiler.language.ast.topLevel.PackageDeclaration;
import compiler.language.ast.topLevel.TypeDefinition;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class CompilationUnitRule extends Rule
{
  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PACKAGE_PRODUCTION = new Object[] {COMPILATION_UNIT, PACKAGE_DECLARATION};
  private static final Object[] IMPORT_PRODUCTION = new Object[] {COMPILATION_UNIT, IMPORT_DECLARATION};
  private static final Object[] TYPE_DEFINITION_PRODUCTION = new Object[] {COMPILATION_UNIT, TYPE_DEFINITION};

  public CompilationUnitRule()
  {
    super(COMPILATION_UNIT, EMPTY_PRODUCTION, PACKAGE_PRODUCTION, IMPORT_PRODUCTION, TYPE_DEFINITION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new CompilationUnit(null, new ImportDeclaration[0], new TypeDefinition[0]);
    }
    if (types == PACKAGE_PRODUCTION)
    {
      CompilationUnit oldUnit = (CompilationUnit) args[0];
      if (oldUnit.getPackageDeclaration() != null)
      {
        throw new IllegalStateException("Multiple package specifications");
      }
      ImportDeclaration[] imports = oldUnit.getImports();
      if (imports.length > 0)
      {
        throw new IllegalStateException("Package must be declared before imports");
      }
      TypeDefinition[] typeDefinitions = oldUnit.getTypes();
      if (typeDefinitions.length > 0)
      {
        throw new IllegalStateException("Package must be specified before types.");
      }
      return new CompilationUnit((PackageDeclaration) args[1], imports, typeDefinitions);
    }
    if (types == IMPORT_PRODUCTION)
    {
      CompilationUnit oldUnit = (CompilationUnit) args[0];
      TypeDefinition[] typeDefinitions = oldUnit.getTypes();
      if (typeDefinitions.length > 0)
      {
        throw new IllegalStateException("Imports must be specified before types");
      }
      ImportDeclaration[] oldImports = oldUnit.getImports();
      ImportDeclaration[] newImports = new ImportDeclaration[oldImports.length + 1];
      System.arraycopy(oldImports, 0, newImports, 0, oldImports.length);
      newImports[oldImports.length] = (ImportDeclaration) args[1];
      return new CompilationUnit(oldUnit.getPackageDeclaration(), newImports, typeDefinitions);
    }
    if (types == TYPE_DEFINITION_PRODUCTION)
    {
      CompilationUnit oldUnit = (CompilationUnit) args[0];
      TypeDefinition[] oldTypes = oldUnit.getTypes();
      TypeDefinition[] newTypes = new TypeDefinition[oldTypes.length + 1];
      System.arraycopy(oldTypes, 0, newTypes, 0, oldTypes.length);
      newTypes[oldTypes.length] = (TypeDefinition) args[1];
      return new CompilationUnit(oldUnit.getPackageDeclaration(), oldUnit.getImports(), newTypes);
    }
    throw badTypeList();
  }

}

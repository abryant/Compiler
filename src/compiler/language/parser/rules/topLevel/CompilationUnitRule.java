package compiler.language.parser.rules.topLevel;

import static compiler.language.parser.ParseType.COMPILATION_UNIT;
import static compiler.language.parser.ParseType.IMPORT_DECLARATION;
import static compiler.language.parser.ParseType.PACKAGE_DECLARATION;
import static compiler.language.parser.ParseType.TYPE_DEFINITION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.topLevel.CompilationUnit;
import compiler.language.ast.topLevel.ImportDeclaration;
import compiler.language.ast.topLevel.PackageDeclaration;
import compiler.language.ast.topLevel.TypeDefinition;
import compiler.language.parser.LanguageParseException;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public final class CompilationUnitRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> PACKAGE_PRODUCTION = new Production<ParseType>(COMPILATION_UNIT, PACKAGE_DECLARATION);
  private static final Production<ParseType> IMPORT_PRODUCTION = new Production<ParseType>(COMPILATION_UNIT, IMPORT_DECLARATION);
  private static final Production<ParseType> TYPE_DEFINITION_PRODUCTION = new Production<ParseType>(COMPILATION_UNIT, TYPE_DEFINITION);

  @SuppressWarnings("unchecked")
  public CompilationUnitRule()
  {
    super(COMPILATION_UNIT, EMPTY_PRODUCTION, PACKAGE_PRODUCTION, IMPORT_PRODUCTION, TYPE_DEFINITION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new CompilationUnit(null, new ImportDeclaration[0], new TypeDefinition[0], null);
    }
    if (PACKAGE_PRODUCTION.equals(production))
    {
      CompilationUnit oldUnit = (CompilationUnit) args[0];
      PackageDeclaration packageDeclaration = (PackageDeclaration) args[1];
      if (oldUnit.getPackageDeclaration() != null)
      {
        throw new LanguageParseException("Multiple package specifications", packageDeclaration.getParseInfo());
      }
      ImportDeclaration[] imports = oldUnit.getImports();
      if (imports.length > 0)
      {
        throw new LanguageParseException("Package must be declared before imports", packageDeclaration.getParseInfo());
      }
      TypeDefinition[] typeDefinitions = oldUnit.getTypes();
      if (typeDefinitions.length > 0)
      {
        throw new LanguageParseException("Package must be specified before types.", packageDeclaration.getParseInfo());
      }
      return new CompilationUnit(packageDeclaration, imports, typeDefinitions, ParseInfo.combine(oldUnit.getParseInfo(), packageDeclaration.getParseInfo()));
    }
    if (IMPORT_PRODUCTION.equals(production))
    {
      CompilationUnit oldUnit = (CompilationUnit) args[0];
      ImportDeclaration newImport = (ImportDeclaration) args[1];
      TypeDefinition[] typeDefinitions = oldUnit.getTypes();
      if (typeDefinitions.length > 0)
      {
        throw new LanguageParseException("Imports must be specified before types", newImport.getParseInfo());
      }
      ImportDeclaration[] oldImports = oldUnit.getImports();
      ImportDeclaration[] newImports = new ImportDeclaration[oldImports.length + 1];
      System.arraycopy(oldImports, 0, newImports, 0, oldImports.length);
      newImports[oldImports.length] = newImport;
      return new CompilationUnit(oldUnit.getPackageDeclaration(), newImports, typeDefinitions, ParseInfo.combine(oldUnit.getParseInfo(), newImport.getParseInfo()));
    }
    if (TYPE_DEFINITION_PRODUCTION.equals(production))
    {
      CompilationUnit oldUnit = (CompilationUnit) args[0];
      TypeDefinition[] oldTypes = oldUnit.getTypes();
      TypeDefinition[] newTypes = new TypeDefinition[oldTypes.length + 1];
      System.arraycopy(oldTypes, 0, newTypes, 0, oldTypes.length);
      TypeDefinition newType = (TypeDefinition) args[1];
      newTypes[oldTypes.length] = newType;
      return new CompilationUnit(oldUnit.getPackageDeclaration(), oldUnit.getImports(), newTypes, ParseInfo.combine(oldUnit.getParseInfo(), newType.getParseInfo()));
    }
    throw badTypeList();
  }

}

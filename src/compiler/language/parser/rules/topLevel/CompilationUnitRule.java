package compiler.language.parser.rules.topLevel;

import static compiler.language.parser.ParseType.COMPILATION_UNIT;
import static compiler.language.parser.ParseType.IMPORT_DECLARATION;
import static compiler.language.parser.ParseType.PACKAGE_DECLARATION;
import static compiler.language.parser.ParseType.TYPE_DEFINITION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.topLevel.CompilationUnitAST;
import compiler.language.ast.topLevel.ImportDeclarationAST;
import compiler.language.ast.topLevel.PackageDeclarationAST;
import compiler.language.ast.topLevel.TypeDefinitionAST;
import compiler.language.parser.LanguageParseException;
import compiler.language.parser.ParseType;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new CompilationUnitAST(null, new ImportDeclarationAST[0], new TypeDefinitionAST[0], null);
    }
    if (PACKAGE_PRODUCTION.equals(production))
    {
      CompilationUnitAST oldUnit = (CompilationUnitAST) args[0];
      PackageDeclarationAST packageDeclaration = (PackageDeclarationAST) args[1];
      if (oldUnit.getPackageDeclaration() != null)
      {
        throw new LanguageParseException("Multiple package specifications", packageDeclaration.getParseInfo());
      }
      ImportDeclarationAST[] imports = oldUnit.getImports();
      if (imports.length > 0)
      {
        throw new LanguageParseException("Package must be declared before imports", packageDeclaration.getParseInfo());
      }
      TypeDefinitionAST[] typeDefinitions = oldUnit.getTypes();
      if (typeDefinitions.length > 0)
      {
        throw new LanguageParseException("Package must be specified before types.", packageDeclaration.getParseInfo());
      }
      return new CompilationUnitAST(packageDeclaration, imports, typeDefinitions, ParseInfo.combine(oldUnit.getParseInfo(), packageDeclaration.getParseInfo()));
    }
    if (IMPORT_PRODUCTION.equals(production))
    {
      CompilationUnitAST oldUnit = (CompilationUnitAST) args[0];
      ImportDeclarationAST newImport = (ImportDeclarationAST) args[1];
      TypeDefinitionAST[] typeDefinitions = oldUnit.getTypes();
      if (typeDefinitions.length > 0)
      {
        throw new LanguageParseException("Imports must be specified before types", newImport.getParseInfo());
      }
      ImportDeclarationAST[] oldImports = oldUnit.getImports();
      ImportDeclarationAST[] newImports = new ImportDeclarationAST[oldImports.length + 1];
      System.arraycopy(oldImports, 0, newImports, 0, oldImports.length);
      newImports[oldImports.length] = newImport;
      return new CompilationUnitAST(oldUnit.getPackageDeclaration(), newImports, typeDefinitions, ParseInfo.combine(oldUnit.getParseInfo(), newImport.getParseInfo()));
    }
    if (TYPE_DEFINITION_PRODUCTION.equals(production))
    {
      CompilationUnitAST oldUnit = (CompilationUnitAST) args[0];
      TypeDefinitionAST[] oldTypes = oldUnit.getTypes();
      TypeDefinitionAST[] newTypes = new TypeDefinitionAST[oldTypes.length + 1];
      System.arraycopy(oldTypes, 0, newTypes, 0, oldTypes.length);
      TypeDefinitionAST newType = (TypeDefinitionAST) args[1];
      newTypes[oldTypes.length] = newType;
      return new CompilationUnitAST(oldUnit.getPackageDeclaration(), oldUnit.getImports(), newTypes, ParseInfo.combine(oldUnit.getParseInfo(), newType.getParseInfo()));
    }
    throw badTypeList();
  }

}

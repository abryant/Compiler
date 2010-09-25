package compiler.language.ast.topLevel;

import compiler.language.ast.ParseInfo;


/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class CompilationUnitAST
{

  private ParseInfo parseInfo;

  private PackageDeclarationAST packageDeclaration;
  private ImportDeclarationAST[] imports;
  private TypeDefinitionAST[] types;

  /**
   * Creates a new CompilationUnitAST with the specified sub-nodes
   * @param packageDeclaration - the package declaration of the compilation unit
   * @param imports - the list of imports of the compilation unit
   * @param types - the list of types declared by the compilation unit
   * @param parseInfo - the parsing information
   */
  public CompilationUnitAST(PackageDeclarationAST packageDeclaration, ImportDeclarationAST[] imports, TypeDefinitionAST[] types, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.packageDeclaration = packageDeclaration;
    this.imports = imports;
    this.types = types;
  }

  /**
   * @return the package declaration
   */
  public PackageDeclarationAST getPackageDeclaration()
  {
    return packageDeclaration;
  }

  /**
   * @return the imports
   */
  public ImportDeclarationAST[] getImports()
  {
    return imports;
  }

  /**
   * @return the types
   */
  public TypeDefinitionAST[] getTypes()
  {
    return types;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    if (packageDeclaration != null)
    {
      buffer.append(packageDeclaration.toString() + "\n\n");
    }
    for (ImportDeclarationAST importDecl : imports)
    {
      buffer.append(importDecl + "\n");
    }
    if (imports.length > 0)
    {
      buffer.append("\n");
    }

    for (TypeDefinitionAST type : types)
    {
      buffer.append(type + "\n\n");
    }
    return buffer.toString();
  }
}

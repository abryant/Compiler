package compiler.language.ast;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class CompilationUnit
{

  private PackageDeclaration packageDeclaration;
  private ImportDeclaration[] imports;
  private TypeDefinition[] types;

  /**
   * Creates a new CompilationUnit with the specified sub-nodes
   * @param packageDeclaration - the package declaration of the compilation unit
   * @param imports - the list of imports of the compilation unit
   * @param types - the list of types declared by the compilation unit
   */
  public CompilationUnit(PackageDeclaration packageDeclaration, ImportDeclaration[] imports, TypeDefinition[] types)
  {
    this.packageDeclaration = packageDeclaration;
    this.imports = imports;
    this.types = types;
  }

  /**
   * @return the package declaration
   */
  public PackageDeclaration getPackageDeclaration()
  {
    return packageDeclaration;
  }

  /**
   * @return the imports
   */
  public ImportDeclaration[] getImports()
  {
    return imports;
  }

  /**
   * @return the types
   */
  public TypeDefinition[] getTypes()
  {
    return types;
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
    for (ImportDeclaration importDecl : imports)
    {
      buffer.append(importDecl + "\n");
    }

    for (TypeDefinition type : types)
    {
      buffer.append("\n" + type + "\n");
    }
    return buffer.toString();
  }
}

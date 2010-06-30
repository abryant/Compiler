package compiler.language.ast;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class PackageDeclaration
{

  private QName packageName;

  /**
   * Creates a new Package Declaration with the specified package name
   * @param packageName - the qualified name of the package
   */
  public PackageDeclaration(QName packageName)
  {
    this.packageName = packageName;
  }

  /**
   * @return the packageName
   */
  public QName getPackageName()
  {
    return packageName;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "package " + packageName + ";";
  }
}

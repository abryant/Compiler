package compiler.language.ast;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ImportDeclaration
{

  private QName name;
  private boolean all;
  private boolean staticImport;

  /**
   * Creates a new ImportDeclaration with the specified name, and whether or not to import all names under this name.
   * @param name - the name to import
   * @param all - true to import all names under this one, false otherwise
   * @param staticImport - true if this should represent a static import, false otherwise
   */
  public ImportDeclaration(QName name, boolean all, boolean staticImport)
  {
    this.name = name;
    this.all = all;
    this.staticImport = staticImport;
  }

  /**
   * @return the name
   */
  public QName getName()
  {
    return name;
  }

  /**
   * @return true if this import declaration imports all names under its specified qualified name, false otherwise
   */
  public boolean isAll()
  {
    return all;
  }

  /**
   * @return true if this ImportDeclaration represents a static import, false otherwise
   */
  public boolean isStaticImport()
  {
    return staticImport;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("import ");
    if (staticImport)
    {
      buffer.append("static ");
    }
    buffer.append(name);
    if (all)
    {
      buffer.append(".*");
    }
    buffer.append(";");
    return buffer.toString();
  }
}

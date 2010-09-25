package compiler.language.ast.topLevel;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ImportDeclarationAST
{

  private ParseInfo parseInfo;

  private QNameAST name;
  private boolean all;
  private boolean staticImport;

  /**
   * Creates a new ImportDeclarationAST with the specified name, and whether or not to import all names under this name.
   * @param name - the name to import
   * @param all - true to import all names under this one, false otherwise
   * @param staticImport - true if this should represent a static import, false otherwise
   * @param parseInfo - the parsing information
   */
  public ImportDeclarationAST(QNameAST name, boolean all, boolean staticImport, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.name = name;
    this.all = all;
    this.staticImport = staticImport;
  }

  /**
   * @return the name
   */
  public QNameAST getName()
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
   * @return true if this ImportDeclarationAST represents a static import, false otherwise
   */
  public boolean isStaticImport()
  {
    return staticImport;
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

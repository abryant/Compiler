package compiler.language.ast.topLevel;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class PackageDeclarationAST
{

  private ParseInfo parseInfo;

  private QNameAST packageName;

  /**
   * Creates a new Package Declaration with the specified package name
   * @param packageName - the qualified name of the package
   * @param parseInfo - the parsing information
   */
  public PackageDeclarationAST(QNameAST packageName, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
    this.packageName = packageName;
  }

  /**
   * @return the packageName
   */
  public QNameAST getPackageName()
  {
    return packageName;
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
    return "package " + packageName + ";";
  }
}

package compiler.language.ast.topLevel;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class PackageDeclaration
{

  private ParseInfo parseInfo;

  private QName packageName;

  /**
   * Creates a new Package Declaration with the specified package name
   * @param packageName - the qualified name of the package
   * @param parseInfo - the parsing information
   */
  public PackageDeclaration(QName packageName, ParseInfo parseInfo)
  {
    this.parseInfo = parseInfo;
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

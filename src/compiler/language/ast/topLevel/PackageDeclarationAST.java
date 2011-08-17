package compiler.language.ast.topLevel;

import compiler.language.LexicalPhrase;
import compiler.language.QName;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class PackageDeclarationAST
{

  private LexicalPhrase lexicalPhrase;

  private QName packageName;

  /**
   * Creates a new Package Declaration with the specified package name
   * @param packageName - the qualified name of the package
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public PackageDeclarationAST(QName packageName, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
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
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
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

package compiler.language.ast.topLevel;

import compiler.language.LexicalPhrase;
import compiler.language.QName;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ImportDeclarationAST
{

  private LexicalPhrase lexicalPhrase;

  private QName name;
  private boolean all;

  /**
   * Creates a new ImportDeclarationAST with the specified name, and whether or not to import all names under this name.
   * @param name - the name to import
   * @param all - true to import all names under this one, false otherwise
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ImportDeclarationAST(QName name, boolean all, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.name = name;
    this.all = all;
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
    StringBuffer buffer = new StringBuffer();
    buffer.append("import ");
    buffer.append(name);
    if (all)
    {
      buffer.append(".*");
    }
    buffer.append(";");
    return buffer.toString();
  }
}

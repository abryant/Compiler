package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.NameAST;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class QNameAST
{

  private LexicalPhrase lexicalPhrase;

  private NameAST[] names;

  /**
   * Creates a new QNameAST containing only one name
   * @param startName - the name to start the QNameAST with
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public QNameAST(NameAST startName, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    names = new NameAST[] {startName};
  }

  /**
   * Creates a new QNameAST based on an old QNameAST, but with an additional name
   * @param original - the QNameAST to base this QNameAST on
   * @param additional - the extra name to add
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public QNameAST(QNameAST original, NameAST additional, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    NameAST[] origNames = original.names;
    names = new NameAST[origNames.length + 1];
    System.arraycopy(origNames, 0, names, 0, origNames.length);
    names[origNames.length] = additional;
  }

  /**
   * Creates a new QNameAST with the specified list of names.
   * @param names - the names to include in this QNameAST
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public QNameAST(NameAST[] names, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.names = names;
  }

  /**
   * @return the names
   */
  public NameAST[] getNames()
  {
    return names;
  }

  /**
   * @return the names contained in this QNameAST as a String[]
   */
  public String[] getNameStrings()
  {
    String[] nameStrings = new String[names.length];
    for (int i = 0; i < names.length; i++)
    {
      nameStrings[i] = names[i].getName();
    }
    return nameStrings;
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
    for (int i = 0; i < names.length; i++)
    {
      buffer.append(names[i]);
      if (i != names.length - 1)
      {
        buffer.append(".");
      }
    }
    return buffer.toString();
  }
}

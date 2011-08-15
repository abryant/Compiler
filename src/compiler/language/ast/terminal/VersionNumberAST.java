package compiler.language.ast.terminal;

import compiler.language.LexicalPhrase;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class VersionNumberAST
{

  private LexicalPhrase lexicalPhrase;

  private IntegerLiteralAST[] versionParts;

  /**
   * Creates a new version number with the specified version parts
   * @param versionParts - the integer literals representing the parts of this version number
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public VersionNumberAST(IntegerLiteralAST[] versionParts, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.versionParts = versionParts;
  }

  /**
   * @return the individual parts of the version number
   */
  public IntegerLiteralAST[] getVersionParts()
  {
    return versionParts;
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
    for (int i = 0; i < versionParts.length; i++)
    {
      buffer.append(versionParts[i]);
      if (i != versionParts.length - 1)
      {
        buffer.append(".");
      }
    }
    return buffer.toString();
  }

}

package compiler.language.ast.terminal;

import compiler.language.LexicalPhrase;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class NameAST
{

  private LexicalPhrase lexicalPhrase;

  private String name;

  /**
   * Creates a NameAST with the specified text and position in the file
   * @param name - the text of the name
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public NameAST(String name, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.name = name;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
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
    return name;
  }
}

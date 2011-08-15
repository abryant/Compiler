package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;

/*
 * Created on 16 Jul 2010
 */

/**
 * The base class for all arguments
 * @author Anthony Bryant
 */
public abstract class ArgumentAST
{

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new ArgumentAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ArgumentAST(LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

}

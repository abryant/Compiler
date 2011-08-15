package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;

/*
 * Created on 10 Jul 2010
 */

/**
 * The base class for all parameters
 * @author Anthony Bryant
 */
public abstract class ParameterAST
{

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new ParameterAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ParameterAST(LexicalPhrase lexicalPhrase)
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

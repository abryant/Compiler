package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;

/*
 * Created on 8 Jul 2010
 */

/**
 * The base class for all statements.
 * @author Anthony Bryant
 */
public abstract class StatementAST
{

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new StatementAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public StatementAST(LexicalPhrase lexicalPhrase)
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

  /**
   * Sets this StatementAST's LexicalPhrase. This should only be used during parsing.
   * @param lexicalPhrase - the LexicalPhrase to set
   */
  public void setLexicalPhrase(LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
  }

}

package compiler.language.ast.type;

import compiler.language.LexicalPhrase;

/*
 * Created on 30 Jun 2010
 */

/**
 * The base class for all Types.
 * @author Anthony Bryant
 */
public abstract class TypeAST
{

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new TypeAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public TypeAST(LexicalPhrase lexicalPhrase)
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
   * @param lexicalPhrase - the LexicalPhrase to set
   */
  public void setLexicalPhrase(LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
  }

}

package compiler.language.ast.member;

import compiler.language.LexicalPhrase;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public abstract class MemberAST
{

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new MemberAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public MemberAST(LexicalPhrase lexicalPhrase)
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

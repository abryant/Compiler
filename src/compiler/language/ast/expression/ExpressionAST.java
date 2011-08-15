package compiler.language.ast.expression;

import compiler.language.LexicalPhrase;

/*
 * Created on 5 Jul 2010
 */

/**
 * The abstract base class for all expressions.
 * @author Anthony Bryant
 */
public abstract class ExpressionAST
{

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new ExpressionAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ExpressionAST(LexicalPhrase lexicalPhrase)
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

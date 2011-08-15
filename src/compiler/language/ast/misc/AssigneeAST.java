package compiler.language.ast.misc;

import compiler.language.LexicalPhrase;


/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class AssigneeAST
{

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new AssigneeAST that does not assign to anything. This is represented by an underscore in the language syntax.
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public AssigneeAST(LexicalPhrase lexicalPhrase)
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
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "_";
  }
}

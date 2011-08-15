package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;


/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EmptyStatementAST extends StatementAST
{

  /**
   * Creates a new EmptyStatementAST with the specified LexicalPhrase
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public EmptyStatementAST(LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return ";";
  }
}

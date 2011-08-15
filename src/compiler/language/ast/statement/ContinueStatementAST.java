package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.IntegerLiteralAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ContinueStatementAST extends StatementAST
{

  private IntegerLiteralAST continueLevels;

  /**
   * Creates a new ContinueStatementAST which continues through the specified number of levels of nested loops.
   * @param continueLevels - the integer literal representing the number of levels to continue through, or null if no number of levels was specified
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ContinueStatementAST(IntegerLiteralAST continueLevels, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.continueLevels = continueLevels;
  }

  /**
   * @return the number of levels to continue through, or null if it was not specified
   */
  public IntegerLiteralAST getContinueLevels()
  {
    return continueLevels;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    if (continueLevels == null)
    {
      return "continue;";
    }
    return "continue(" + continueLevels + ");";
  }

}

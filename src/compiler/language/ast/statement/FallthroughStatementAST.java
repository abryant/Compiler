package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.terminal.IntegerLiteralAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FallthroughStatementAST extends StatementAST
{

  private IntegerLiteralAST fallthroughLevels;

  /**
   * Creates a new FallthroughStatementAST to fall through the specified number of levels of switch statements.
   * @param fallthroughLevels - the number of levels of switch statements to fallthrough, or null if it was not specified
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public FallthroughStatementAST(IntegerLiteralAST fallthroughLevels, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.fallthroughLevels = fallthroughLevels;
  }

  /**
   * @return the fallthroughLevels
   */
  public IntegerLiteralAST getFallthroughLevels()
  {
    return fallthroughLevels;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    if (fallthroughLevels == null)
    {
      return "fallthrough;";
    }
    return "fallthrough(" + fallthroughLevels + ");";
  }
}

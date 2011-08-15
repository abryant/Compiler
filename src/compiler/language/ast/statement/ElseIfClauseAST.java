package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ElseIfClauseAST
{

  private ExpressionAST condition;
  private BlockAST block;

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new ElseIfClauseAST with the specified condition and block
   * @param condition - the condition that must be satisfied for this else if clause to execute
   * @param block - the block that this else if clause executes
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public ElseIfClauseAST(ExpressionAST condition, BlockAST block, LexicalPhrase lexicalPhrase)
  {
    this.lexicalPhrase = lexicalPhrase;
    this.condition = condition;
    this.block = block;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }

  /**
   * @return the condition
   */
  public ExpressionAST getExpression()
  {
    return condition;
  }

  /**
   * @return the block
   */
  public BlockAST getBlock()
  {
    return block;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "else if " + condition + "\n" + block;
  }

}

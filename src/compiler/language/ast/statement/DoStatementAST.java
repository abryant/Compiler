package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DoStatementAST extends StatementAST
{

  private BlockAST block;
  private ExpressionAST condition;

  /**
   * @param block - the block to execute in this do loop
   * @param condition - the condition of this do loop
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public DoStatementAST(BlockAST block, ExpressionAST condition, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.block = block;
    this.condition = condition;
  }

  /**
   * @return the block
   */
  public BlockAST getBlock()
  {
    return block;
  }

  /**
   * @return the condition
   */
  public ExpressionAST getCondition()
  {
    return condition;
  }

  /**
   * {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "do\n" + block + "\nwhile " + condition + ";";
  }
}

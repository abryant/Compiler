package compiler.language.ast.statement;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ExpressionAST;

/*
 * Created on 13 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class WhileStatementAST extends StatementAST
{

  private ExpressionAST condition;
  private BlockAST block;

  /**
   * @param condition - the condition of this while loop
   * @param block - the block that this while loop executes
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public WhileStatementAST(ExpressionAST condition, BlockAST block, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.condition = condition;
    this.block = block;
  }

  /**
   * @return the condition
   */
  public ExpressionAST getCondition()
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
    return "while " + condition + "\n" + block;
  }

}

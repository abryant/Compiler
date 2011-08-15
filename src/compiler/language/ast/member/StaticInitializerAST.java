package compiler.language.ast.member;

import compiler.language.LexicalPhrase;
import compiler.language.ast.statement.BlockAST;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StaticInitializerAST extends MemberAST
{

  private BlockAST block;

  /**
   * Creates a new static initializer which contains the specified block
   * @param block - the block for this static initializer
   * @param lexicalPhrase - the lexical phrase associated with this AST node
   */
  public StaticInitializerAST(BlockAST block, LexicalPhrase lexicalPhrase)
  {
    super(lexicalPhrase);
    this.block = block;
  }

  /**
   * @return the block
   */
  public BlockAST getBlock()
  {
    return block;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "static\n" + block;
  }
}

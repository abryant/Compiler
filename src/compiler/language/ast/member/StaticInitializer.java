package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.Block;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StaticInitializer extends Member
{
  private Block block;

  /**
   * Creates a new static initializer which contains the specified block
   * @param block - the block for this static initializer
   * @param parseInfo - the parsing information
   */
  public StaticInitializer(Block block, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.block = block;
  }

  /**
   * @return the block
   */
  public Block getBlock()
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

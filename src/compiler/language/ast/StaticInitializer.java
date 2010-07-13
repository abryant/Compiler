package compiler.language.ast;

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
   */
  public StaticInitializer(Block block)
  {
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

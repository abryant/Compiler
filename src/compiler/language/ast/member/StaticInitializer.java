package compiler.language.ast.member;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.statement.Block;
import compiler.language.ast.terminal.Name;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StaticInitializer extends Member
{

  private Name name;
  private Block block;

  /**
   * Creates a new static initializer with the specified class name which contains the specified block
   * @param name - the name associated with the static initializer, this should be checked later to make sure it is the same as the class name.
   * @param block - the block for this static initializer
   * @param parseInfo - the parsing information
   */
  public StaticInitializer(Name name, Block block, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.name = name;
    this.block = block;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
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
    return "static " + name + "\n" + block;
  }
}

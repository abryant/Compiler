package compiler.language.ast.expression;

import compiler.language.ast.misc.ArgumentList;
import compiler.language.ast.statement.Block;
import compiler.language.ast.type.Type;

/*
 * Created on 3 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClosureCreationExpression extends Expression
{

  private Type returnType;
  private ArgumentList arguments;
  private Block block;

  /**
   * Creates a new ClosureCreationExpression with the specified return type, arguments and block
   * @param returnType - the return type of the closure
   * @param arguments - the arguments to the closure
   * @param block - the block containing the closure's statements
   */
  public ClosureCreationExpression(Type returnType, ArgumentList arguments, Block block)
  {
    this.returnType = returnType;
    this.arguments = arguments;
    this.block = block;
  }

  /**
   * @return the returnType
   */
  public Type getReturnType()
  {
    return returnType;
  }

  /**
   * @return the arguments
   */
  public ArgumentList getArguments()
  {
    return arguments;
  }

  /**
   * @return the block
   */
  public Block getBlock()
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
    return returnType + " closure" + arguments + "\n" + block;
  }

}

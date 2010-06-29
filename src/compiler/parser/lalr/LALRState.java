package compiler.parser.lalr;

import compiler.parser.Action;
import compiler.parser.IState;
import compiler.parser.Token;

/*
 * Created on 21 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class LALRState implements IState
{

  // TODO: how should actions and gotos be stored?

  /**
   * @see compiler.parser.IState#getAction(compiler.parser.Token)
   */
  @Override
  public Action getAction(Token terminal)
  {
    return null;
  }

  /**
   * @see compiler.parser.IState#getExpectedTerminalTypes()
   */
  @Override
  public Object[] getExpectedTerminalTypes()
  {
    return null;
  }

  /**
   * @see compiler.parser.IState#getGoto(compiler.parser.Token)
   */
  @Override
  public IState getGoto(Token nonTerminal)
  {
    return null;
  }

}

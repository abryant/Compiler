package compiler.parser;

/*
 * Created on 21 Jun 2010
 */

/**
 * Specifies the behaviour of a state in a parse table.
 *
 * @author Anthony Bryant
 */
public interface IState
{

  /**
   * Finds the action to perform given the specified terminal
   * @param terminal - the terminal token to get the action for in this state
   * @return the action to perform
   */
  public Action getAction(Token terminal);

  /**
   * @return the list of terminals that this state has actions for
   */
  public Object[] getExpectedTerminalTypes();

  /**
   * Finds the state to goto given the specified non terminal
   * @param nonTerminal - the non terminal token to get the goto rule for in this state
   * @return the state to go to
   */
  public IState getGoto(Token nonTerminal);

}

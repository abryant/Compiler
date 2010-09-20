package compiler.parser;

import java.io.Serializable;

/*
 * Created on 21 Jun 2010
 */

/**
 * Specifies the behaviour of a state in a parse table.
 *
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public interface State<T extends Enum<T>> extends Serializable
{

  /**
   * Finds the action to perform given the specified terminal
   * @param terminal - the terminal token to get the action for in this state
   * @return the action to perform
   */
  public Action<T> getAction(Token<T> terminal);

  /**
   * @return the list of terminals that this state has actions for
   */
  public T[] getExpectedTerminalTypes();

  /**
   * Finds the state to goto given the specified non terminal
   * @param nonTerminal - the non terminal token to get the goto rule for in this state
   * @return the state to go to
   */
  public State<T> getGoto(Token<T> nonTerminal);

}

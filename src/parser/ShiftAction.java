package parser;

import java.util.Deque;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents a shift action in the parser.
 *
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public class ShiftAction<T extends Enum<T>> extends Action<T>
{

  private static final long serialVersionUID = 1L;

  private State<T> state;

  /**
   * Creates a new Shift action to shift to the specified state
   * @param state - the state to shift to
   */
  public ShiftAction(State<T> state)
  {
    this.state = state;
  }

  /**
   * @see parser.Action#perform(parser.Token, java.util.Deque, java.util.Deque)
   */
  @Override
  public boolean perform(Token<T> token, Deque<State<T>> stateStack, Deque<Token<T>> tokenStack) throws ParseException
  {
    // add the next state and the next token to their stacks
    stateStack.addFirst(state);
    tokenStack.addFirst(token);
    return true;
  }
}

package parser;

import java.util.Deque;
import java.util.LinkedList;

/*
 * Created on 21 Jun 2010
 */

/**
 * Implements a parser, which parses input from a tokenizer according to a parse table (which is implemented as a set of states and their associated actions)
 *
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public class Parser<T extends Enum<T>>
{

  private State<T> startState;
  private Tokenizer<T> tokenizer;

  /**
   * Creates a new parser to parse the input from the specified Tokenizer, starting in the specified start state.
   * @param startState - the state to start the parser in
   * @param tokenizer - the tokenizer to read input from
   */
  public Parser(State<T> startState, Tokenizer<T> tokenizer)
  {
    this.startState = startState;
    this.tokenizer = tokenizer;
  }

  /**
   * Parses the input from the tokenizer according to the start state
   * @return the token representing the whole of the parsed structure
   * @throws ParseException - if an error occurs while parsing
   * @throws BadTokenException - if an unexpected token was found while parsing
   */
  public Token<T> parse() throws ParseException, BadTokenException
  {
    Deque<State<T>> stateStack = new LinkedList<State<T>>();
    Deque<Token<T>> tokenStack = new LinkedList<Token<T>>();
    stateStack.addFirst(startState);

    Token<T> lookahead = tokenizer.next();

    while (true)
    {
      State<T> state = stateStack.peekFirst();

      Action<T> action = state.getAction(lookahead);
      if (action == null)
      {
        throw new BadTokenException(lookahead, state.getExpectedTerminalTypes());
      }

      if (action.isAccept())
      {
        // perform the reduction that the accept action does
        action.perform(lookahead, stateStack, tokenStack);
        return tokenStack.removeFirst();
      }

      // perform the action (usually shift/reduce)
      boolean tokenUsed = action.perform(lookahead, stateStack, tokenStack);
      if (tokenUsed)
      {
        lookahead = tokenizer.next();
      }
    }

  }

}

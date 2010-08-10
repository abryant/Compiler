package compiler.parser;

import java.util.Deque;
import java.util.LinkedList;

/*
 * Created on 21 Jun 2010
 */

/**
 * Implements a parser, which parses input from a tokenizer according to a parse table (which is implemented as a set of states and their associated actions)
 *
 * @author Anthony Bryant
 */
public class Parser
{

  private State startState;
  private Tokenizer tokenizer;

  /**
   * Creates a new parser to parse the input from the specified Tokenizer, starting in the specified start state.
   * @param startState - the state to start the parser in
   * @param tokenizer - the tokenizer to read input from
   */
  public Parser(State startState, Tokenizer tokenizer)
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
  public Token parse() throws ParseException, BadTokenException
  {
    Deque<State> stateStack = new LinkedList<State>();
    Deque<Token> tokenStack = new LinkedList<Token>();
    stateStack.addFirst(startState);

    Token lookahead = tokenizer.next();

    while (true)
    {
      State state = stateStack.peekFirst();

      Action action = state.getAction(lookahead);
      if (action == null)
      {
        throw new BadTokenException(lookahead, state.getExpectedTerminalTypes());
      }

      if (action.isAccept())
      {
        System.out.println("Accepting on " + lookahead); // TODO: remove debug output
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

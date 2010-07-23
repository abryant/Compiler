package compiler.parser;

import java.util.Deque;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents a reduce action in the parser.
 *
 * @author Anthony Bryant
 */
public class ReduceAction extends Action
{
  private Rule rule;
  private int productionIndex;

  /**
   * Creates a new ReduceAction to reduce via the specified rule
   * @param rule - the rule to reduce with
   * @param productionIndex - the index of the production within the rule to reduce with
   */
  public ReduceAction(Rule rule, int productionIndex)
  {
    this.rule = rule;
    this.productionIndex = productionIndex;
  }

  /**
   * @return the rule that this ReduceAction will reduce through
   */
  public Rule getRule()
  {
    return rule;
  }

  /**
   * @return the index of the production in the rule associated with this reduce action
   */
  public int getProductionIndex()
  {
    return productionIndex;
  }

  /**
   * @see compiler.parser.Action#perform(compiler.parser.Token, java.util.Deque, java.util.Deque)
   */
  @Override
  public boolean perform(Token token, Deque<State> stateStack, Deque<Token> tokenStack)
  {
    Object[] production = rule.getProductions()[productionIndex];
    System.out.println("Reducing via production " + Rule.getProductionString(rule.getType(), production) + " (lookahead " + (token == null ? null : token.getType()) + ")"); // TODO: remove debug output
    if (stateStack.size() <= production.length || tokenStack.size() < production.length)
    {
      throw new IllegalStateException("Bad reduction of rule, not enough elements");
    }

    // get the list of token values
    Object[] values = new Object[production.length];
    for (int i = values.length - 1; i >= 0; i--)
    {
      Token t = tokenStack.removeFirst();
      if (!t.getType().equals(production[i]))
      {
        throw new IllegalStateException("Bad reduction of rule, invalid token type");
      }
      values[i] = t.getValue();

      // remove the top state from the state stack
      stateStack.removeFirst();
    }

    Object result = rule.match(production, values);
    Token nonTerminal = new Token(rule.getType(), result);

    State topState = stateStack.peekFirst();
    State gotoState = topState.getGoto(nonTerminal);

    stateStack.addFirst(gotoState);
    tokenStack.addFirst(nonTerminal);

    return false;
  }
}

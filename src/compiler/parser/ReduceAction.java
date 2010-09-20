package compiler.parser;

import java.util.Deque;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents a reduce action in the parser.
 *
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public class ReduceAction<T extends Enum<T>> extends Action<T>
{

  private static final long serialVersionUID = 1L;

  private Rule<T> rule;
  private int productionIndex;

  /**
   * Creates a new ReduceAction to reduce via the specified rule
   * @param rule - the rule to reduce with
   * @param productionIndex - the index of the production within the rule to reduce with
   */
  public ReduceAction(Rule<T> rule, int productionIndex)
  {
    this.rule = rule;
    this.productionIndex = productionIndex;
  }

  /**
   * @return the rule that this ReduceAction will reduce through
   */
  public Rule<T> getRule()
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
  public boolean perform(Token<T> token, Deque<State<T>> stateStack, Deque<Token<T>> tokenStack) throws ParseException
  {
    Production<T> production = rule.getProductions()[productionIndex];

    T[] productionTypes = production.getTypes();
    if (stateStack.size() <= productionTypes.length || tokenStack.size() < productionTypes.length)
    {
      throw new ParseException("Bad reduction of rule, not enough elements");
    }

    // get the list of token values
    Object[] values = new Object[productionTypes.length];
    for (int i = values.length - 1; i >= 0; i--)
    {
      Token<T> t = tokenStack.removeFirst();
      if (!t.getType().equals(productionTypes[i]))
      {
        throw new ParseException("Bad reduction of rule, invalid token type");
      }
      values[i] = t.getValue();

      // remove the top state from the state stack
      stateStack.removeFirst();
    }

    Object result = rule.match(production, values);
    Token<T> nonTerminal = new Token<T>(rule.getType(), result);

    State<T> topState = stateStack.peekFirst();
    State<T> gotoState = topState.getGoto(nonTerminal);

    stateStack.addFirst(gotoState);
    tokenStack.addFirst(nonTerminal);

    return false;
  }
}

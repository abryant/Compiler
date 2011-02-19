package parser.lalr;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import parser.AcceptAction;
import parser.Action;
import parser.Production;
import parser.ReduceAction;
import parser.Rule;
import parser.ShiftAction;
import parser.State;
import parser.Token;


/*
 * Created on 21 Jun 2010
 */

/**
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 *
 */
public class LALRState<T extends Enum<T>> implements State<T>
{

  private static final long serialVersionUID = 1L;

  private Map<T, LALRState<T>> shiftRules = null;
  private Map<T, ReduceAction<T>> reduceActions = null;
  // this should only ever contain null -> new AcceptAction(), (null means there are no more tokens)
  // but other conditions for accept are allowed by the parser, so this is more general
  private Map<T, AcceptAction<T>> acceptActions = null;

  private Map<T, LALRState<T>> gotoRules = null;

  /**
   * Creates a new LALRState with no shift or reduce actions or goto rules.
   */
  public LALRState()
  {
    // do nothing, as the maps are created lazily
  }

  /**
   * Adds a shift action from this state to the specified state via the specified token type
   * @param tokenType - the token type to shift on
   * @param shiftTo - the state to shift to
   */
  public void addShift(T tokenType, LALRState<T> shiftTo)
  {
    if (shiftRules != null && shiftRules.containsKey(tokenType))
    {
      throw new IllegalStateException("Shift-shift conflict! Via terminal: " + tokenType);
    }
    ReduceAction<T> reduceAction = reduceActions != null ? reduceActions.get(tokenType) : null;
    if (reduceAction != null)
    {
      Rule<T> rule = reduceAction.getRule();
      Production<T> production = rule.getProductions()[reduceAction.getProductionIndex()];
      throw shiftReduceConflict(tokenType, rule.getType(), production);
    }
    AcceptAction<T> acceptAction = acceptActions != null ? acceptActions.get(tokenType) : null;
    if (acceptAction != null)
    {
      Rule<T> rule = acceptAction.getRule();
      throw shiftAcceptConflict(tokenType, rule.getType(), rule.getProductions()[acceptAction.getProductionIndex()]);
    }
    if (shiftRules == null)
    {
      shiftRules = new HashMap<T, LALRState<T>>();
    }
    shiftRules.put(tokenType, shiftTo);
  }

  /**
   * Adds a reduce action from this state via the specified rule and production with the specified token type
   * @param tokenType - the token type to shift on
   * @param reduceRule - the rule to reduce with
   * @param productionIndex - the production of the rule to use
   */
  public void addReduce(T tokenType, Rule<T> reduceRule, int productionIndex)
  {
    if (shiftRules != null && shiftRules.containsKey(tokenType))
    {
      Production<T> production = reduceRule.getProductions()[productionIndex];
      throw shiftReduceConflict(tokenType, reduceRule.getType(), production);
    }
    ReduceAction<T> existingReduce = reduceActions != null ? reduceActions.get(tokenType) : null;
    if (existingReduce != null)
    {
      Production<T> newProduction = reduceRule.getProductions()[productionIndex];
      throw reduceReduceConflict(tokenType, existingReduce, reduceRule.getType(), newProduction);
    }
    AcceptAction<T> existingAccept = acceptActions != null ? acceptActions.get(tokenType) : null;
    if (existingAccept != null)
    {
      Production<T> newProduction = reduceRule.getProductions()[productionIndex];
      Rule<T> acceptRule = existingAccept.getRule();
      throw acceptReduceConflict(tokenType, acceptRule.getType(), acceptRule.getProductions()[existingAccept.getProductionIndex()], reduceRule.getType(), newProduction);
    }
    if (reduceActions == null)
    {
      reduceActions = new HashMap<T, ReduceAction<T>>();
    }
    reduceActions.put(tokenType, new ReduceAction<T>(reduceRule, productionIndex));
  }

  /**
   * Adds an accept action from this state via the specified token type.
   * @param tokenType - the token type to accept on
   * @param rule - the rule to reduce with before accepting
   * @param productionIndex - the index of the production of the rule to use
   */
  public void addAccept(T tokenType, Rule<T> rule, int productionIndex)
  {
    if (shiftRules != null && shiftRules.containsKey(tokenType))
    {
      throw shiftAcceptConflict(tokenType, rule.getType(), rule.getProductions()[productionIndex]);
    }
    ReduceAction<T> existingReduce = reduceActions != null ? reduceActions.get(tokenType) : null;
    if (existingReduce != null)
    {
      Rule<T> existingRule = existingReduce.getRule();
      throw acceptReduceConflict(tokenType, rule.getType(), rule.getProductions()[productionIndex], existingRule.getType(), existingRule.getProductions()[existingReduce.getProductionIndex()]);
    }
    AcceptAction<T> existingAccept = acceptActions != null ? acceptActions.get(tokenType) : null;
    if (existingAccept != null)
    {
      Rule<T> existingRule = existingAccept.getRule();
      throw acceptAcceptConflict(tokenType, existingAccept, existingRule.getType(), existingRule.getProductions()[existingAccept.getProductionIndex()]);
    }
    if (acceptActions == null)
    {
      acceptActions = new HashMap<T, AcceptAction<T>>();
    }
    acceptActions.put(tokenType, new AcceptAction<T>(rule, productionIndex));
  }

  /**
   * Adds a goto from this state with the specified token to the specified other state.
   * @param tokenType - the token type for the goto condition
   * @param state - the state to go to on the specified token
   */
  public void addGoto(T tokenType, LALRState<T> state)
  {
    if (gotoRules != null && gotoRules.containsKey(tokenType))
    {
      throw new IllegalStateException("Goto-goto conflict! TypeAST:" + tokenType);
    }
    if (gotoRules == null)
    {
      gotoRules = new HashMap<T, LALRState<T>>();
    }
    gotoRules.put(tokenType, state);
  }

  /**
   * @see parser.State#getAction(parser.Token)
   */
  @Override
  public Action<T> getAction(Token<T> terminal)
  {
    Object type = terminal == null ? null : terminal.getType();
    // try to return a shift rule first
    LALRState<T> state = shiftRules != null ? shiftRules.get(type) : null;
    if (state != null)
    {
      return new ShiftAction<T>(state);
    }
    // there was no shift rule, so try a reduce rule
    ReduceAction<T> reduceAction = reduceActions != null ? reduceActions.get(type) : null;
    if (reduceAction != null)
    {
      return reduceAction;
    }
    // there were no shift or reduce rules, so try an accept rule
    AcceptAction<T> acceptAction = acceptActions != null ? acceptActions.get(type) : null;
    if (acceptAction != null)
    {
      return acceptAction;
    }
    // there is nothing in the table for this terminal from this state
    return null;
  }

  /**
   * @see parser.State#getExpectedTerminalTypes()
   */
  @SuppressWarnings("unchecked")
  @Override
  public T[] getExpectedTerminalTypes()
  {
    Set<T> types = new HashSet<T>();
    if (shiftRules != null)
    {
      types.addAll(shiftRules.keySet());
    }
    if (reduceActions != null)
    {
      types.addAll(reduceActions.keySet());
    }
    if (acceptActions != null)
    {
      types.addAll(acceptActions.keySet());
    }

    if (!types.iterator().hasNext())
    {
      throw new IllegalStateException("An LALR State must have at least one action.");
    }
    T firstType = types.iterator().next();
    return types.toArray((T[]) Array.newInstance(firstType.getClass(), types.size()));
  }

  /**
   * @see parser.State#getGoto(parser.Token)
   */
  @Override
  public State<T> getGoto(Token<T> nonTerminal)
  {
    return gotoRules != null ? gotoRules.get(nonTerminal.getType()) : null;
  }

  /**
   * Package scope, because it should only ever be used by something which knows what it is doing.
   * @return the shift rules of this LALRState, or null if it has not been initialized yet
   */
  Map<T, LALRState<T>> getShiftRules()
  {
    return shiftRules;
  }

  /**
   * Package scope, because it should only ever be used by something which knows what it is doing.
   * @return the reduce actions of this LALRState, or null if it has not been initialized yet
   */
  Map<T, ReduceAction<T>> getReduceActions()
  {
    return reduceActions;
  }

  /**
   * Package scope, because it should only ever be used by something which knows what it is doing.
   * @return the accept actions of this LALRState, or null if it has not been initialized yet
   */
  Map<T, AcceptAction<T>> getAcceptActions()
  {
    return acceptActions;
  }

  /**
   * Package scope, because it should only ever be used by something which knows what it is doing.
   * @return the goto rules of this LALRState, or null if it has not been initialized yet
   */
  Map<T, LALRState<T>> getGotoRules()
  {
    return gotoRules;
  }

  /**
   * Creates an exception representing a shift-reduce conflict.
   * This exception contains details of the input token type,
   * the type that the reduce action would produce, and
   * the production it would take to get there.
   * @param <T> - the enum type that holds all possible values for the token type
   * @param tokenType - the input token that leads to this conflict
   * @param reduceRuleType - the token type that reducing would produce
   * @param reduceProduction - the production that the reduce rule uses
   * @return an exception representing the shift-reduce conflict
   */
  private static <T extends Enum<T>> IllegalStateException shiftReduceConflict(T tokenType, T reduceRuleType, Production<T> reduceProduction)
  {
    String production = Rule.getProductionString(reduceRuleType, reduceProduction);
    return new IllegalStateException("Shift-reduce conflict! On input: " + tokenType + ", can either shift or reduce via " + production);
  }

  /**
   * Creates an exception representing a shift-accept conflict.
   * This exception contains details of the input token type,
   * the type that the accept action would produce, and
   * the production it would take to get there.
   * @param <T> - the enum type that holds all possible values for the token type
   * @param tokenType - the input token that leads to this conflict
   * @param acceptRuleType - the token type that accepting would produce
   * @param acceptProduction - the production that the accept rule uses
   * @return an exception representing the shift-accept conflict
   */
  private static <T extends Enum<T>> IllegalStateException shiftAcceptConflict(T tokenType, T acceptRuleType, Production<T> acceptProduction)
  {
    String production = Rule.getProductionString(acceptRuleType, acceptProduction);
    return new IllegalStateException("Shift-accept conflict! On input: " + tokenType + ", can either shift or accept via " + production);
  }

  /**
   * Creates an exception representing a reduce-reduce conflict.
   * This exception contains details of the input token type,
   * the type that each reduce action would produce, and
   * the production it would take to get there in each case.
   * @param <T> - the enum type that holds all possible values for the token type
   * @param tokenType - the input token that leads to this conflict
   * @param existingReduce - the existing reduce action
   * @param newRuleType - the token type that the new reduce rule would produce
   * @param newProduction - the production that the new reduce rule uses
   * @return an exception representing the reduce-reduce conflict
   */
  private static <T extends Enum<T>> IllegalStateException reduceReduceConflict(T tokenType, ReduceAction<T> existingReduce, T newRuleType, Production<T> newProduction)
  {
    Rule<T> existingRule = existingReduce.getRule();
    String existingProductionStr = Rule.getProductionString(existingRule.getType(), existingRule.getProductions()[existingReduce.getProductionIndex()]);
    String newProductionStr = Rule.getProductionString(newRuleType, newProduction);
    return new IllegalStateException("Reduce-reduce conflict! On input: " + tokenType + ", can reduce via " + existingProductionStr + " or " + newProductionStr);
  }

  /**
   * Creates an exception representing an accept-reduce conflict.
   * This exception contains details of the input token type,
   * the type that each action would produce, and
   * the production that it would take to get there in each case.
   * @param <T> - the enum type that holds all possible values for the token type
   * @param tokenType - the input token that leads to this conflict
   * @param acceptRuleType - the token type that the accept rule would produce
   * @param acceptProduction - the production that the accept rule uses
   * @param reduceRuleType - the token type that reducing would produce
   * @param reduceProduction - the production that the reduce rule uses
   * @return an exception representing the accept-reduce conflict
   */
  private static <T extends Enum<T>> IllegalStateException acceptReduceConflict(T tokenType, T acceptRuleType, Production<T> acceptProduction, T reduceRuleType, Production<T> reduceProduction)
  {
    String acceptProductionStr = Rule.getProductionString(acceptRuleType, acceptProduction);
    String productionStr = Rule.getProductionString(reduceRuleType, reduceProduction);
    return new IllegalStateException("Accept-reduce conflict! On input: " + tokenType + ", can accept via " + acceptProductionStr + " or reduce via " + productionStr);
  }

  /**
   * Creates an exception representing an accept-accept conflict.
   * This exception contains details of the input token type,
   * the type that each action would produce, and
   * the production that it would take to get there in each case.
   * @param <T> - the enum type that holds all possible values for the token type
   * @param tokenType - the input token that leads to this conflict
   * @param existingAccept - the existing accept action
   * @param acceptRuleType - the token type that the accept rule would produce
   * @param acceptProduction - the production that the accept rule uses
   * @return an exception representing the accept-accept conflict
   */
  private static <T extends Enum<T>> IllegalStateException acceptAcceptConflict(T tokenType, AcceptAction<T> existingAccept, T acceptRuleType, Production<T> acceptProduction)
  {
    Rule<T> existingRule = existingAccept.getRule();
    String existingProductionStr = Rule.getProductionString(existingRule.getType(), existingRule.getProductions()[existingAccept.getProductionIndex()]);
    String acceptProductionStr = Rule.getProductionString(acceptRuleType, acceptProduction);
    return new IllegalStateException("Accept-accept conflict! On input: " + tokenType + ", can accept via " + existingProductionStr + " or via " + acceptProductionStr);
  }
}

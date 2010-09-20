package compiler.parser;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/*
 * Created on 21 Jun 2010
 */

/**
 * Stores a set of rules, and provides methods for finding information about them.
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public class RuleSet<T extends Enum<T>>
{

  protected Map<T, Rule<T>> rules;
  protected Rule<T> startRule = null;

  private Map<T, Set<TypeUseEntry<T>>> typeUses = new HashMap<T, Set<TypeUseEntry<T>>>();

  // a lazily evaluated set containing the types that are nullable (i.e. can be epsilon)
  // populated in findNullableSet()
  private Set<T> nullableTypes = null;

  private Map<T, Set<T>> firstSets = new HashMap<T, Set<T>>();
  private Map<T, Set<T>> followSets = new HashMap<T, Set<T>>();

  /**
   * Creates a new RuleSet with the specified rules
   * @param rules - the rules to contain in this RuleSet
   */
  public RuleSet(Set<Rule<T>> rules)
  {
    this.rules = new HashMap<T, Rule<T>>();
    for (Rule<T> rule : rules)
    {
      addRule(rule);
    }
  }

  /**
   * Creates a new RuleSet with an empty set of rules.
   */
  public RuleSet()
  {
    rules = new HashMap<T, Rule<T>>();
  }

  /**
   * Adds the specified rule to the set.
   * Note: adding two different Rules with the same productions is not supported and will throw an IllegalArgumentException (but should be unnecessary).
   * Also, adding two different Rules with the same token type will cause the two Rules to be coalesced into a single Rule.
   * This means that neither the existing rule nor the new rule will be contained directly in the RuleSet, instead they will be added via a MetaRule
   * @param rule - the rule to add
   */
  public void addRule(Rule<T> rule)
  {
    // clear the caches
    nullableTypes = null;
    typeUses = null;
    firstSets.clear();
    followSets.clear();

    Rule<T> existing = rules.get(rule.getType());
    if (existing == null)
    {
      rules.put(rule.getType(), rule);
    }
    else
    {
      rules.put(rule.getType(), new MetaRule<T>(existing, rule));
    }
  }

  /**
   * Adds the specified rule as the starting rule for the grammar.
   * @param rule - the new starting rule for this grammar.
   */
  public void addStartRule(Rule<T> rule)
  {
    addRule(rule);
    startRule = rule;
  }

  /**
   * @return the start rule for this grammar
   */
  public Rule<T> getStartRule()
  {
    return startRule;
  }

  /**
   * Checks whether the specified token type is a terminal. i.e. it does not have any rules that produce it.
   * @param tokenType - the type to check
   * @return true if the specified type is a terminal, false otherwise
   */
  public boolean isTerminal(T tokenType)
  {
    return rules.get(tokenType) == null;
  }

  /**
   * Finds the set of types that can possibly have no types on their right hand side.
   * This includes all rules which are explicitly empty, and all rules that only have nullable rules on their right hand side.
   */
  private void findNullableSet()
  {
    nullableTypes = new HashSet<T>();

    // keep looping until no elements are added to the nullable set in an iteration
    boolean changed = true;
    while (changed)
    {
      changed = false;
      for (Entry<T, Rule<T>> entry : rules.entrySet())
      {
        T type = entry.getKey();
        Production<T>[] productions = entry.getValue().getProductions();
        for (Production<T> production : productions)
        {
          boolean nullable = true;
          for (T subType : production.getTypes())
          {
            if (!nullableTypes.contains(subType))
            {
              nullable = false;
              break;
            }
          }
          if (nullable)
          {
            if (nullableTypes.add(type))
            {
              changed = true;
            }
          }
        }
      }
    }
  }

  /**
   * Finds whether the specified type is nullable.
   * @param type - the type to check
   * @return true if the specified type is nullable, false otherwise
   */
  protected boolean isNullable(T type)
  {
    if (nullableTypes == null)
    {
      findNullableSet();
    }
    return nullableTypes.contains(type);
  }

  /**
   * Finds the set of uses for each type on the right hand side of the rule.
   */
  private void findTypeUses()
  {
    typeUses = new HashMap<T, Set<TypeUseEntry<T>>>();

    for (Rule<T> rule : rules.values())
    {
      // add type uses for all of the right hand sides of the Rule
      Production<T>[] productions = rule.getProductions();
      for (int i = 0; i < productions.length; i++)
      {
        T[] productionTypes = productions[i].getTypes();
        for (int j = 0; j < productionTypes.length; j++)
        {
          T type = productionTypes[j];
          Set<TypeUseEntry<T>> uses = typeUses.get(type);
          if (uses == null)
          {
            uses = new HashSet<TypeUseEntry<T>>();
            typeUses.put(type, uses);
          }
          uses.add(new TypeUseEntry<T>(rule, i, j));
        }
      }
    }
  }

  /**
   * Finds the set of uses of the specified type.
   * @param type - the type to find the uses of
   * @return the set of uses of the specified type
   */
  protected Set<TypeUseEntry<T>> getTypeUses(T type)
  {
    if (typeUses == null)
    {
      findTypeUses();
    }
    return typeUses.get(type);
  }

  /**
   * Finds the first set of the specified token type. The first set is defined according to the following rules:
   * The first set for a terminal is the terminal itself.
   * The first set for a nonterminal is the set of terminals that could start a derivation of it.
   * The first set never contains an epsilon. Nullability is instead checked for using the isNullable() method.
   * @param tokenType - the type of the token to find the first set of
   * @return a set of token types that could start a derivation of the specified token type
   */
  public Set<T> getFirstSet(T tokenType)
  {
    // check whether the result has already been cached
    Set<T> cachedResult = firstSets.get(tokenType);
    if (cachedResult != null)
    {
      return cachedResult;
    }

    Deque<T> stack = new LinkedList<T>();
    stack.add(tokenType);

    Set<T> visited = new HashSet<T>();

    Set<T> result = new HashSet<T>();

    while (!stack.isEmpty())
    {
      T currentType = stack.pop();
      // only visit each type once
      if (visited.contains(currentType))
      {
        continue;
      }
      visited.add(currentType);

      // find the productions for this type
      Rule<T> rule = rules.get(currentType);
      if (rule == null)
      {
        // this is a terminal, so just add the terminal itself to the result
        result.add(currentType);
        continue;
      }

      Production<T>[] productions = rule.getProductions();

      // for each production, add the applicable subtypes (from the RHS of the rule) to the stack
      for (Production<T> production : productions)
      {
        // iterate through the production in order, and break when a non-nullable type is reached
        // this adds all reachable types to the stack, to be computed later on
        for (T type : production.getTypes())
        {
          stack.push(type);
          if (!isNullable(type))
          {
            break;
          }
        }
      }
    }

    // cache the result
    firstSets.put(tokenType, result);

    return result;
  }

}

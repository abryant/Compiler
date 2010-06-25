package compiler.parser.lalr;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import compiler.parser.Rule;

/*
 * Created on 21 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class LALRParserGenerator
{

  private LALRRuleSet rules;

  /**
   * Creates a new LALR parser generator for the specified set of rules.
   * @param rules - the rule set to generate an LALR parser for
   */
  public LALRParserGenerator(LALRRuleSet rules)
  {
    this.rules = rules;
  }

  /**
   * Generates the parse table for the set of rules passed into this parser generator.
   * The parse table can be obtained from the getStartState() function.
   */
  public void generate()
  {
    Rule startRule = rules.getStartRule();

    // create the initial item set
    LALRItemSet initialSet = new LALRItemSet();
    int productionCount = startRule.getProductions().length;
    for (int i = 0; i < productionCount; i++)
    {
      initialSet.addKernelItem(new LALRItem(startRule, i, 0));
    }
    initialSet.calculateClosureItems(rules);

    Set<LALRItemSet> itemSets = new HashSet<LALRItemSet>();
    itemSets.add(initialSet);

    Deque<LALRItemSet> stack = new LinkedList<LALRItemSet>();
    stack.add(initialSet);

    while (!stack.isEmpty())
    {
      LALRItemSet set = stack.pop();

      Map<Object, LALRItemSet> transitionsSet = findTransitions(set, itemSets);
      // TODO: use the keys of this map when generating the states and actions
      stack.addAll(transitionsSet.values());
    }

    // TODO: generate the state and action objects from the item sets
  }

  /**
   * Finds all of the transitions from the specified LALRItemSet to other LALRItemSets
   * @param itemSet - the item set to find the transitions from
   * @param existingSets - the existing item sets. All new item sets will be added to this.
   * @return the transitions from the specified item set
   */
  private Map<Object, LALRItemSet> findTransitions(LALRItemSet itemSet, Set<LALRItemSet> existingSets)
  {
    // find the transitions from itemSet to some new LALRItemSet objects
    Map<Object, LALRItemSet> transitions = new HashMap<Object, LALRItemSet>();
    Set<LALRItem> items = itemSet.getItems();
    for (LALRItem item : items)
    {
      Object[] production = item.getProduction();
      int offset = item.getOffset();
      // there are no transitions from the end of a production
      if (offset == production.length)
      {
        continue;
      }

      LALRItem newItem = new LALRItem(item.getRule(), item.getProductionIndex(), offset + 1);
      newItem.addLookaheads(item.getLookaheads());

      LALRItemSet currentTransition = transitions.get(production[offset]);
      if (currentTransition == null)
      {
        currentTransition = new LALRItemSet();
      }
      currentTransition.addKernelItem(newItem);
      transitions.put(production[offset], currentTransition);
    }

    // create a map from the set entry to itself, so that the actual object can be retrieved from the set later
    // basically, this works around Set not having a method like:
    // public E get(E key)
    // which really goes against the concept of a set, but is very useful when you have overridden equals in certain ways
    Map<LALRItemSet, LALRItemSet> existingSetsMap = new HashMap<LALRItemSet, LALRItemSet>();
    for (LALRItemSet set : existingSets)
    {
      existingSetsMap.put(set, set);
    }

    // combine the new LALRItemSet objects with the existing sets, and make sure they are all in existingSets
    Iterator<Entry<Object, LALRItemSet>> it = transitions.entrySet().iterator();
    while (it.hasNext())
    {
      Entry<Object, LALRItemSet> entry = it.next();
      LALRItemSet transitionSet = entry.getValue();
      LALRItemSet existing = existingSetsMap.get(transitionSet);
      if (existing != null)
      {
        existing.combineLookaheads(transitionSet);
        existing.calculateClosureItems(rules);
        // existing does not need to be re-added to existingSet, as it is already in there
      }
      else
      {
        // make sure the new transition set is in the set of existing sets
        existingSets.add(transitionSet);
        existingSetsMap.put(transitionSet, transitionSet);
      }
    }

    return transitions;
  }

}

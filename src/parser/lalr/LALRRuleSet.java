package parser.lalr;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import parser.Production;
import parser.Rule;
import parser.RuleSet;
import parser.TypeUseEntry;


/*
 * Created on 22 Jun 2010
 */

/**
 * A RuleSet with extra methods for LALR parsers.
 *
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public class LALRRuleSet<T extends Enum<T>> extends RuleSet<T>
{

  /**
   * Creates a new empty LALRRuleSet.
   */
  public LALRRuleSet()
  {
    super();
  }

  /**
   * Creates a new LALRRuleSet with the specified rules.
   * @param rules - the rules for this LALRRuleSet to initially contain
   */
  public LALRRuleSet(Set<Rule<T>> rules)
  {
    super(rules);
  }

  /**
   * Finds the set of LALR Items that are in the epsilon-closure of the specified set of start items.
   * @param startItems - the set of kernel items to calculate the closure of
   * @return the set of closure items that must be added to the kernel set to produce the closure. These will be updated with any additional lookaheads they require
   */
  public Map<TypeUseEntry<T>, LALRItem<T>> calculateClosureItems(Collection<LALRItem<T>> startItems)
  {
    Deque<LALRItem<T>> queue = new LinkedList<LALRItem<T>>();
    queue.addAll(startItems);

    Map<TypeUseEntry<T>, LALRItem<T>> result = new HashMap<TypeUseEntry<T>, LALRItem<T>>();
    // add the kernel items to the result.
    // they will be removed again at the end of this method, but they need to
    // be added so that their lookaheads can be updated when an existing item
    // is found in the result set
    for (LALRItem<T> startItem : startItems)
    {
      result.put(startItem.getNextTypeUse(), startItem);
    }

    Map<TypeUseEntry<T>, LALRItem<T>> visited = new HashMap<TypeUseEntry<T>, LALRItem<T>>();

    while (!queue.isEmpty())
    {
      LALRItem<T> item = queue.pollFirst();
      LALRItem<T> visitedItem = visited.get(item.getNextTypeUse());
      if (visitedItem != null && visitedItem.containsLookaheads(item))
      {
        continue;
      }
      LALRItem<T> itemCopy = new LALRItem<T>(item);
      visited.put(itemCopy.getNextTypeUse(), itemCopy);

      int offset = item.getOffset();

      // we have reached an item that should be added to the result
      // (unless it is one of startItems, but in that case it will be removed later)
      LALRItem<T> resultItem = new LALRItem<T>(item.getRule(), item.getProductionIndex(), offset);
      LALRItem<T> existing = result.get(resultItem.getNextTypeUse());
      if (existing != null)
      {
        existing.addLookaheads(item.getLookaheads());
        // if the lookaheads of an item are changed, it should be processed again by the queue
        // NOTE: maintenance of the visited map depends on this
        queue.addLast(existing);
      }
      else
      {
        resultItem.addLookaheads(item.getLookaheads());
        result.put(resultItem.getNextTypeUse(), resultItem);
      }

      Production<T> production = item.getProduction();
      T[] productionTypes = production.getTypes();
      if (offset == productionTypes.length)
      {
        // this item represents the end of a production, so it cannot generate any closure items
        continue;
      }

      Rule<T> rule = rules.get(productionTypes[offset]);
      if (rule != null)
      {
        // work out the lookahead set for the new stack items that are about to be generated
        // this is the first set of the item after the current token,
        // but advances on to the subsequent tokens in the list until a non-nullable one is found
        Set<T> lookaheads = new HashSet<T>();
        boolean nullable = true;
        for (int lookahead = offset + 1; lookahead < productionTypes.length; lookahead++)
        {
          lookaheads.addAll(getFirstSet(productionTypes[lookahead]));
          if (!isNullable(productionTypes[lookahead]))
          {
            nullable = false;
            break;
          }
        }
        if (nullable)
        {
          lookaheads.addAll(item.getLookaheads());
        }

        // this is a non-terminal, so add the productions of its rule to the stack for processing
        Production<T>[] subProductions = rule.getProductions();
        for (int j = 0; j < subProductions.length; j++)
        {
          LALRItem<T> stackItem = new LALRItem<T>(rule, j, 0);
          stackItem.addLookaheads(lookaheads);
          queue.addLast(stackItem);
        }
      }
    }

    // remove all items that are already in startItems (they do not count as closure items)
    Iterator<Entry<TypeUseEntry<T>, LALRItem<T>>> it = result.entrySet().iterator();
    while (it.hasNext())
    {
      Entry<TypeUseEntry<T>, LALRItem<T>> entry = it.next();
      if (startItems.contains(entry.getValue()))
      {
        it.remove();
      }
    }

    return result;
  }

}

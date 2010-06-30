package compiler.parser.lalr;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import compiler.parser.Rule;
import compiler.parser.RuleSet;
import compiler.parser.TypeUseEntry;

/*
 * Created on 22 Jun 2010
 */

/**
 * A RuleSet with extra methods for LALR parsers.
 *
 * @author Anthony Bryant
 */
public class LALRRuleSet extends RuleSet
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
  public LALRRuleSet(Set<Rule> rules)
  {
    super(rules);
  }

  /**
   * Finds the set of LALR Items that are in the epsilon-closure of the specified set of start items.
   * @param startItems - the set of kernel items to calculate the closure of
   * @return the set of closure items that must be added to the kernel set to produce the closure
   */
  public Map<TypeUseEntry, LALRItem> calculateClosureItems(Collection<LALRItem> startItems)
  {
    Deque<LALRItem> queue = new LinkedList<LALRItem>();
    queue.addAll(startItems);

    Map<TypeUseEntry, LALRItem> result = new HashMap<TypeUseEntry, LALRItem>();

    Map<TypeUseEntry, LALRItem> visited = new HashMap<TypeUseEntry, LALRItem>();

    while (!queue.isEmpty())
    {
      LALRItem item = queue.pollFirst();
      LALRItem visitedItem = visited.get(item.getNextTypeUse());
      if (visitedItem != null && visitedItem.containsLookaheads(item))
      {
        continue;
      }
      LALRItem itemCopy = new LALRItem(item);
      visited.put(itemCopy.getNextTypeUse(), itemCopy);

      int offset = item.getOffset();

      // we have reached an item that should be added to the result
      // (unless it is one of startItems, but in that case it will be removed later)
      LALRItem resultItem = new LALRItem(item.getRule(), item.getProductionIndex(), offset);
      LALRItem existing = result.get(resultItem.getNextTypeUse());
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

      Object[] production = item.getProduction();
      if (offset == production.length)
      {
        // this item represents the end of a production, so it cannot generate any closure items
        continue;
      }

      Rule rule = rules.get(production[offset]);
      if (rule != null)
      {
        // work out the lookahead set for the new stack items that are about to be generated
        // this is the first set of the item after the current token,
        // but advances on to the subsequent tokens in the list until a non-nullable one is found
        Set<Object> lookaheads = new HashSet<Object>();
        boolean nullable = true;
        for (int lookahead = offset + 1; lookahead < production.length; lookahead++)
        {
          lookaheads.addAll(getFirstSet(production[lookahead]));
          if (!isNullable(production[lookahead]))
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
        Object[][] subProductions = rule.getProductions();
        for (int j = 0; j < subProductions.length; j++)
        {
          LALRItem stackItem = new LALRItem(rule, j, 0);
          stackItem.addLookaheads(lookaheads);
          queue.addLast(stackItem);
        }
      }
    }

    // remove all items that are already in startItems (they do not count as closure items)
    Iterator<Entry<TypeUseEntry, LALRItem>> it = result.entrySet().iterator();
    while (it.hasNext())
    {
      Entry<TypeUseEntry, LALRItem> entry = it.next();
      if (startItems.contains(entry.getValue()))
      {
        it.remove();
      }
    }

    return result;
  }

}

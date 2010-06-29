package compiler.parser.lalr;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

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
    Deque<LALRItem> stack = new LinkedList<LALRItem>();
    stack.addAll(startItems);

    Map<TypeUseEntry, LALRItem> result = new HashMap<TypeUseEntry, LALRItem>();

    Set<LALRItem> visited = new HashSet<LALRItem>();

    while (!stack.isEmpty())
    {
      LALRItem item = stack.pop();
      if (visited.contains(item))
      {
        continue;
      }
      visited.add(item);

      Object[] production = item.getProduction();
      int offset = item.getOffset();
      if (offset == production.length)
      {
        // this item represents the end of a production, so it cannot generate any closure items
        continue;
      }

      for (int i = offset + 1; i < production.length; i++)
      {
        Rule rule = rules.get(production[i]);

        // we have reached an item that should be added to the result
        LALRItem resultItem = new LALRItem(item.getRule(), item.getProductionIndex(), i);
        LALRItem existing = result.get(resultItem);
        if (existing != null)
        {
          existing.addLookaheads(item.getLookaheads());
        }
        else
        {
          resultItem.addLookaheads(item.getLookaheads());
          result.put(resultItem.getNextTypeUse(), resultItem);
        }

        if (rule != null)
        {
          // this is a non-terminal, so add the productions of its rule to the stack for processing
          Object[][] subProductions = rule.getProductions();
          for (int j = 0; j < subProductions.length; j++)
          {
            LALRItem stackItem = new LALRItem(rule, j, 0);
            // work out the lookahead set for the new stack item
            boolean nullable = true;
            for (int l = i + 1; l < production.length; l++)
            {
              stackItem.addLookaheads(getFirstSet(production[l]));
              if (!isNullable(production[l]))
              {
                nullable = false;
                break;
              }
            }
            if (nullable)
            {
              stackItem.addLookaheads(item.getLookaheads());
            }
            stack.add(stackItem);
          }
        }

        // do not go onto the next type in the production unless the current type is nullable
        if (!isNullable(production[i]))
        {
          break;
        }
      }
    }

    return result;
  }

}

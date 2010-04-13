package compiler.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Created on 6 Apr 2010
 */

/**
 * A RuleSet object contains a set of rules that the parser can use to match. It supports various lookups on the set of rules.
 * Once a method other than add() has been called on an instance of this class, add() should not be called again, as this class caches results for some function calls.
 * 
 * @author Anthony Bryant
 * 
 */
public class RuleSet
{
  
  private Map<Object, List<Object[]>> rules;
  
  private Map<Object, List<TypeUseEntry>> typeUses;
  
  private Map<Object, Set<Object>> fullFollowSets;
  
  /**
   * Creates a new RuleSet.
   */
  public RuleSet()
  {
    rules = new HashMap<Object, List<Object[]>>();
    typeUses = new HashMap<Object, List<TypeUseEntry>>();
    fullFollowSets = new HashMap<Object, Set<Object>>();
  }
  
  /**
   * Adds the specified rule to this set
   * @param rule - the rule to add
   */
  public void add(Rule rule)
  {
    Object[][] requirementTypeLists = rule.getRequirementTypeLists();
    if (requirementTypeLists == null || requirementTypeLists.length == 0)
    {
      // there is nothing to add if this Rule does not represent any real rules
      return;
    }
    
    // get/create the rule list for this Rule's type
    List<Object[]> ruleLists = rules.get(rule.getType());
    if (ruleLists == null)
    {
      ruleLists = new ArrayList<Object[]>(requirementTypeLists.length);
      rules.put(rule.getType(), ruleLists);
    }
    
    for (int i = 0; i < requirementTypeLists.length; i++)
    {
      Object[] typeList = requirementTypeLists[i];
      // add the type list to the rule type list
      ruleLists.add(typeList);
      
      for (int j = 0; j < typeList.length; j++)
      {
        TypeUseEntry entry = new TypeUseEntry(rule, i, j);
        
        // add the type use entry to the type uses map
        List<TypeUseEntry> typeUseList = typeUses.get(typeList[j]);
        if (typeUseList == null)
        {
          typeUseList = new LinkedList<TypeUseEntry>();
          typeUseList.add(entry);
          typeUses.put(typeList[j], typeUseList);
        }
        else
        {
          typeUseList.add(entry);
        }
        
      }
    }
    
  }
  
  /**
   * Finds a list of each location in the grammar where the specified type is used (on the right hand side of a rule).
   * @param type - the type to find the locations of
   * @return a list of locations of uses of the specified type, or null if it is never used
   */
  public List<TypeUseEntry> getTypeUses(Object type)
  {
    return typeUses.get(type);
  }
  
  /**
   * Finds the immediate follow set of the specified list of type uses.
   * The immediate follow set of a type is the set of terminal types that can immediately follow the specified type as part of the same rule.
   * Since the rule can contain non-terminals, any non-terminals are expanded and the first type(s) in the expanded rules are added to the immediate follow set.
   * If the type use occurs at the end of a rule, the immediate follow set of the type from that rule is NOT added to the generated follow set.
   * This method is guaranteed not to recurse indefinitely, as it keeps track of which rules it has applied and does not go into loops.
   * @param uses - the list of uses of the type to generate the immediate follow set for
   * @return the set of terminal types that could follow the specified type uses
   */
  public Set<Object> getImmediateFollowSet(List<TypeUseEntry> uses)
  {
    Set<Object> terminals = new HashSet<Object>();
    if (uses == null || uses.isEmpty())
    {
      // this type is never used, so return an empty set
      return terminals;
    }
    
    LinkedList<Object> queue = new LinkedList<Object>();
    
    // find the immediate follow set including non-terminals
    for (TypeUseEntry entry : uses)
    {
      Object[] rule = entry.getRule().getRequirementTypeLists()[entry.getTypeListNum()];
      int index = entry.getOffset();
      if (index + 1 == rule.length)
      {
        continue;
      }
      queue.offer(rule[index + 1]);
    }
    
    Set<Object> visited = new HashSet<Object>();
    
    // reduce the non-terminals to terminals, and add the terminals to the set
    while (!queue.isEmpty())
    {
      Object followType = queue.poll();
      visited.add(followType);
      List<Object[]> followTypeRules = rules.get(followType);
      if (followTypeRules == null || followTypeRules.isEmpty())
      {
        terminals.add(followType);
        continue;
      }
      for (Object[] rule : followTypeRules)
      {
        if (rule.length == 0)
        {
          // an empty rule is equivalent to no rule at all, so ignore it
          continue;
        }
        // only add the rule if we have not already visited it, otherwise we could recurse infinitely for some rules
        if (!visited.contains(rule[0]))
        {
          queue.offer(rule[0]);
        }
      }
    }
    
    return terminals;
  }
  
  /**
   * Finds the full follow set of the specified type.
   * The full follow set is the set of terminal types that can immediately follow the specified type.
   * Since the rule can contain non-terminals, any non-terminals are expanded and the first type(s) in the expanded rules are added to the follow set.
   * If the specified type occurs at the end of a rule, the full follow set of the terminal from that rule is added to the generated follow set.
   * This method is guaranteed not to recurse indefinitely, as it keeps track of which rules it has applied and does not go into loops.
   * Also, the result of this method contains the null element iff the specified type can be the last token. For this to work, the top level token must be passed into this method.
   * NOTE: after this is called, add() should not be called again, as this caches results that depend on the rule set being in the current state.
   * @param type - the type to generate the full follow set for
   * @param topLevelType - the top level type that the parser is trying to reach, this type is assumed to have an extra follow entry: null
   * @return the set of terminal types that could follow the specified type
   */
  public Set<Object> getFullFollowSet(Object type, Object topLevelType)
  {
    Set<Object> terminals = fullFollowSets.get(type);
    if (terminals != null)
    {
      return terminals;
    }
    
    // the set of types that we need to calculate the follow set of
    LinkedList<Object> queue = new LinkedList<Object>();
    Set<Object> initialSet = new HashSet<Object>();
    queue.add(type);
    initialSet.add(type);
    while (!queue.isEmpty())
    {
      Object currentType = queue.poll();
      List<TypeUseEntry> uses = typeUses.get(currentType);
      if (uses == null)
      {
        continue;
      }
      for (TypeUseEntry entry : uses)
      {
        Object[] rule = entry.getRule().getRequirementTypeLists()[entry.getTypeListNum()];
        int index = entry.getOffset();
        if (index + 1 != rule.length)
        {
          continue;
        }
        Object ruleType = entry.getRule().getType();
        // if the rule type was not already in the set when we added it, then offer the rule type onto the queue
        if (initialSet.add(ruleType))
        {
          queue.offer(ruleType);
        }
      }
    }
    
    // fill terminals with the immediate follow sets of all of the elements of the initial set
    terminals = new HashSet<Object>();
    for (Object currentType : initialSet)
    {
      terminals.addAll(getImmediateFollowSet(getTypeUses(currentType)));
      if (currentType == topLevelType)
      {
        // add the null follow entry to represent the fact that it is possible for the specified type to be the last token
        terminals.add(null);
      }
    }
    return terminals;
  }
  
}

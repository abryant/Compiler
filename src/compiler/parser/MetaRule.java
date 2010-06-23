package compiler.parser;

/*
 * Created on 22 Jun 2010
 */

/**
 * A rule the coalesces multiple rules for the same type into a single rule.
 *
 * @author Anthony Bryant
 */
public class MetaRule extends Rule
{

  private Rule first;
  private Rule second;

  /**
   * Creates a new MetaRule to coalesce the specified two rules into one.
   * The specified rules must return the same type.
   * @param first - the first rule
   * @param second - the second rule
   */
  public MetaRule(Rule first, Rule second)
  {
    super(first.getType(), coalesce(first, second));
  }

  /**
   * Coalesces the specified two rules and produces the list of productions.
   * The list of productions is always the first rule's productions concatenated with the second rule's productions.
   * i.e. the first rule's productions, then the second
   * @param first - the first rule to coalesce
   * @param second - the second rule to coalesce
   * @return the coalesced productions list
   * @throws IllegalArgumentException - if the rules cannot be combined
   */
  private static Object[][] coalesce(Rule first, Rule second)
  {
    if (first.getType() != second.getType())
    {
      throw new IllegalArgumentException("MetaRule may only be created for two rules of the same type.");
    }
    Object[][] firstTypeLists = first.getRequirementTypeLists();
    Object[][] secondTypeLists = second.getRequirementTypeLists();

    // make sure the two rules do not have any type lists in common
    for (Object[] typeList : firstTypeLists)
    {
      for (Object[] check : secondTypeLists)
      {
        if (typeList.length != check.length)
        {
          continue;
        }
        boolean equal = true;
        for (int i = 0; i < typeList.length; i++)
        {
          if (typeList[i] != check[i])
          {
            equal = false;
            break;
          }
        }
        if (equal)
        {
          throw new IllegalArgumentException("A MetaRule may not be created for rules that have a type list in common");
        }
      }
    }

    Object[][] productions = new Object[firstTypeLists.length + secondTypeLists.length][];
    System.arraycopy(firstTypeLists, 0, productions, 0, firstTypeLists.length);
    System.arraycopy(secondTypeLists, 0, productions, firstTypeLists.length, secondTypeLists.length);
    return productions;
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    // find the index of the production we are using
    Object[][] productions = getRequirementTypeLists();
    int index = -1;
    for (int i = 0; i < productions.length; i++)
    {
      if (productions[i].length != types.length)
      {
        continue;
      }
      boolean equal = true;
      for (int j = 0; j < types.length; j++)
      {
        if (types[j] != productions[i][j])
        {
          equal = false;
          break;
        }
      }
      if (equal)
      {
        index = i;
        break;
      }
    }
    // the production was not in any of this rule's requirement type lists
    if (index == -1)
    {
      throw new IllegalStateException("MetaRule.match() called with invalid type list");
    }

    // call either the first or the second rule's match() method depending on the index of the type list in this rule's productions
    if (index < first.getRequirementTypeLists().length)
    {
      return first.match(types, args);
    }
    return second.match(types, args);
  }

}

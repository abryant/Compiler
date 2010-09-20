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
  private static final long serialVersionUID = 1L;

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
  private static Production[] coalesce(Rule first, Rule second)
  {
    if (first.getType() != second.getType())
    {
      throw new IllegalArgumentException("MetaRule may only be created for two rules of the same type.");
    }
    Production[] firstProductions = first.getProductions();
    Production[] secondProductions = second.getProductions();

    // make sure the two rules do not have any productions in common
    for (Production production : firstProductions)
    {
      for (Production check : secondProductions)
      {
        if (production.equals(check))
        {
          throw new IllegalArgumentException("A MetaRule may not be created for rules that have a production in common");
        }
      }
    }

    Production[] productions = new Production[firstProductions.length + secondProductions.length];
    System.arraycopy(firstProductions, 0, productions, 0, firstProductions.length);
    System.arraycopy(secondProductions, 0, productions, firstProductions.length, secondProductions.length);
    return productions;
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    // find the index of the production we are using
    Production[] productions = getProductions();
    int index = -1;
    for (int i = 0; i < productions.length; i++)
    {
      if (production.equals(productions[i]))
      {
        index = i;
        break;
      }
    }
    // the production was not in any of this rule's productions
    if (index == -1)
    {
      throw new ParseException("MetaRule.match() called with invalid production");
    }

    // call either the first or the second rule's match() method depending on the index of the production in this rule's productions
    if (index < first.getProductions().length)
    {
      return first.match(production, args);
    }
    return second.match(production, args);
  }

}

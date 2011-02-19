package parser;

import java.lang.reflect.Array;


/*
 * Created on 22 Jun 2010
 */

/**
 * A rule the coalesces multiple rules for the same type into a single rule.
 *
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public class MetaRule<T extends Enum<T>> extends Rule<T>
{
  private static final long serialVersionUID = 1L;

  private Rule<T> first;
  private Rule<T> second;

  /**
   * Creates a new MetaRule to coalesce the specified two rules into one.
   * The specified rules must return the same type.
   * @param first - the first rule
   * @param second - the second rule
   */
  public MetaRule(Rule<T> first, Rule<T> second)
  {
    super(first.getType(), coalesce(first, second));
  }

  /**
   * Coalesces the specified two rules and produces the list of productions.
   * The list of productions is always the first rule's productions concatenated with the second rule's productions.
   * i.e. the first rule's productions, then the second
   * @param <T> - the enum type that holds all possible values for the token type
   * @param first - the first rule to coalesce
   * @param second - the second rule to coalesce
   * @return the coalesced productions list
   * @throws IllegalArgumentException - if the rules cannot be combined
   */
  private static <T extends Enum<T>> Production<T>[] coalesce(Rule<T> first, Rule<T> second)
  {
    if (first.getType() != second.getType())
    {
      throw new IllegalArgumentException("MetaRule may only be created for two rules of the same type.");
    }
    Production<T>[] firstProductions = first.getProductions();
    Production<T>[] secondProductions = second.getProductions();

    // make sure the two rules do not have any productions in common
    for (Production<T> production : firstProductions)
    {
      for (Production<T> check : secondProductions)
      {
        if (production.equals(check))
        {
          throw new IllegalArgumentException("A MetaRule may not be created for rules that have a production in common");
        }
      }
    }

    if (firstProductions.length == 0)
    {
      throw new IllegalArgumentException("A MetaRule may not be created for rules that have no productions");
    }
    @SuppressWarnings("unchecked")
    Production<T>[] productions = (Production<T>[]) Array.newInstance(firstProductions[0].getClass(), firstProductions.length + secondProductions.length);
    System.arraycopy(firstProductions, 0, productions, 0, firstProductions.length);
    System.arraycopy(secondProductions, 0, productions, firstProductions.length, secondProductions.length);
    return productions;
  }

  /**
   * @see parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Production<T> production, Object[] args) throws ParseException
  {
    // find the index of the production we are using
    Production<T>[] productions = getProductions();
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

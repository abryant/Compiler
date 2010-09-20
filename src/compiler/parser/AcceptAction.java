package compiler.parser;


/*
 * Created on 21 Jun 2010
 */

/**
 * Represents an accept action in the parser
 *
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public class AcceptAction<T extends Enum<T>> extends ReduceAction<T>
{

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new AcceptAction to reduce on the specified rule and then accept.
   * @param rule - the rule to reduce on
   * @param productionIndex - the index of the production in the rule to reduce on
   */
  public AcceptAction(Rule<T> rule, int productionIndex)
  {
    super(rule, productionIndex);
  }

  /**
   * @see compiler.parser.Action#isAccept()
   */
  @Override
  public boolean isAccept()
  {
    return true;
  }
}

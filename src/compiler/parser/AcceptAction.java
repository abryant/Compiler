package compiler.parser;


/*
 * Created on 21 Jun 2010
 */

/**
 * Represents an accept action in the parser
 *
 * @author Anthony Bryant
 */
public class AcceptAction extends ReduceAction
{
  /**
   * Creates a new AcceptAction to reduce on the specified rule and then accept.
   * @param rule - the rule to reduce on
   * @param productionIndex - the index of the production in the rule to reduce on
   */
  public AcceptAction(Rule rule, int productionIndex)
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

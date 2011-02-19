package test.brackets;

import parser.ParseException;
import parser.Production;
import parser.Rule;

/*
 * Created on 15 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BracketRule extends Rule<BracketsType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<BracketsType> PRODUCTION = new Production<BracketsType>(BracketsType.LPAREN, BracketsType.BRACKETS, BracketsType.RPAREN);

  @SuppressWarnings("unchecked")
  public BracketRule()
  {
    super(BracketsType.BRACKET, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<BracketsType> production, Object[] args) throws ParseException
  {
    if (production.equals(PRODUCTION))
    {
      return new Bracket((Bracket[]) args[1]);
    }
    throw badTypeList();
  }

}

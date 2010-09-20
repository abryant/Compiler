package test.brackets;

import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 15 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BracketRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(BracketsType.LPAREN, BracketsType.BRACKETS, BracketsType.RPAREN);

  public BracketRule()
  {
    super(BracketsType.BRACKET, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (production.equals(PRODUCTION))
    {
      return new Bracket((Bracket[]) args[1]);
    }
    throw badTypeList();
  }

}

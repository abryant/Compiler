package test.brackets;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 15 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BracketRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {BracketsType.LPAREN, BracketsType.BRACKETS, BracketsType.RPAREN};

  public BracketRule()
  {
    super(BracketsType.BRACKET, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      return new Bracket((Bracket[]) args[1]);
    }
    throw badTypeList();
  }

}

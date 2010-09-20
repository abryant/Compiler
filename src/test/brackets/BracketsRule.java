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
public class BracketsRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production();
  private static final Production LIST_PRODUCTION = new Production(BracketsType.BRACKET, BracketsType.BRACKETS);

  public BracketsRule()
  {
    super(BracketsType.BRACKETS, EMPTY_PRODUCTION, LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (production.equals(EMPTY_PRODUCTION))
    {
      return new Bracket[0];
    }
    if (production.equals(LIST_PRODUCTION))
    {
      Bracket[] oldList = (Bracket[]) args[1];
      Bracket[] newList = new Bracket[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 1, oldList.length);
      newList[0] = (Bracket) args[0];
      return newList;
    }
    throw badTypeList();
  }

}

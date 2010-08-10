package test.brackets;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 15 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BracketsRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] LIST_PRODUCTION = new Object[] {BracketsType.BRACKET, BracketsType.BRACKETS};

  public BracketsRule()
  {
    super(BracketsType.BRACKETS, EMPTY_PRODUCTION, LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new Bracket[0];
    }
    if (types == LIST_PRODUCTION)
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

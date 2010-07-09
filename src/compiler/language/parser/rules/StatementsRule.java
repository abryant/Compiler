package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.STATEMENT;
import static compiler.language.parser.ParseType.STATEMENTS;

import compiler.language.ast.Statement;
import compiler.parser.Rule;

/*
 * Created on 9 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StatementsRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {STATEMENTS, STATEMENT};

  public StatementsRule()
  {
    super(STATEMENTS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new Statement[0];
    }
    if (types == PRODUCTION)
    {
      Statement[] oldList = (Statement[]) args[0];
      Statement[] newList = new Statement[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (Statement) args[1];
      return newList;
    }
    throw badTypeList();
  }

}

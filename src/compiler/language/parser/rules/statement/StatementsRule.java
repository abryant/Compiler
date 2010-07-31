package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.STATEMENT;
import static compiler.language.parser.ParseType.STATEMENTS;

import compiler.language.ast.statement.Statement;
import compiler.parser.Rule;

/*
 * Created on 9 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StatementsRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {STATEMENT};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {STATEMENTS, STATEMENT};

  public StatementsRule()
  {
    super(STATEMENTS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new Statement[] {(Statement) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
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

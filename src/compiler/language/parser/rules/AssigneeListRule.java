package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ASSIGNEE;
import static compiler.language.parser.ParseType.ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.COMMA;

import compiler.language.ast.Assignee;
import compiler.parser.Rule;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class AssigneeListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {ASSIGNEE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ASSIGNEE_LIST, COMMA, ASSIGNEE};

  public AssigneeListRule()
  {
    super(ASSIGNEE_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      return new Assignee[] {(Assignee) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      Assignee[] oldList = (Assignee[]) args[0];
      Assignee[] newList = new Assignee[oldList.length + 1];
      System.arraycopy(oldList, 0, newList, 0, oldList.length);
      newList[oldList.length] = (Assignee) args[2];
      return newList;
    }
    throw badTypeList();
  }

}

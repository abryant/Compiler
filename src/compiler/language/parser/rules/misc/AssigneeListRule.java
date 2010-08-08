package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ASSIGNEE;
import static compiler.language.parser.ParseType.ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.COMMA;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Assignee;
import compiler.parser.Rule;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class AssigneeListRule extends Rule
{

  // this is a right-recursive list, so that the symbol after the list can be
  // used to decide between TypeList and AssigneeList
  private static final Object[] START_PRODUCTION = new Object[] {ASSIGNEE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {ASSIGNEE, COMMA, ASSIGNEE_LIST};

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
      Assignee assignee = (Assignee) args[0];
      return new ParseList<Assignee>(assignee, assignee.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Assignee> list = (ParseList<Assignee>) args[2];
      Assignee assignee = (Assignee) args[0];
      list.addFirst(assignee, ParseInfo.combine(assignee.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

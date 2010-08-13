package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE;
import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.DeclarationAssignee;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DeclarationAssigneeListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {DECLARATION_ASSIGNEE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {DECLARATION_ASSIGNEE_LIST, COMMA, DECLARATION_ASSIGNEE};

  public DeclarationAssigneeListRule()
  {
    super(DECLARATION_ASSIGNEE_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      DeclarationAssignee assignee = (DeclarationAssignee) args[0];
      return new ParseList<DeclarationAssignee>(assignee, assignee.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssignee> list = (ParseList<DeclarationAssignee>) args[0];
      DeclarationAssignee assignee = (DeclarationAssignee) args[2];
      list.addLast(assignee, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], assignee.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE;
import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.DeclarationAssignee;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class DeclarationAssigneeListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(DECLARATION_ASSIGNEE);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(DECLARATION_ASSIGNEE_LIST, COMMA, DECLARATION_ASSIGNEE);

  @SuppressWarnings("unchecked")
  public DeclarationAssigneeListRule()
  {
    super(DECLARATION_ASSIGNEE_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      DeclarationAssignee assignee = (DeclarationAssignee) args[0];
      return new ParseList<DeclarationAssignee>(assignee, assignee.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
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

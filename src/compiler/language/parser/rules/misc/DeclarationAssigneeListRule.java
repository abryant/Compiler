package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE;
import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.DeclarationAssigneeAST;
import compiler.language.parser.ParseType;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      DeclarationAssigneeAST assignee = (DeclarationAssigneeAST) args[0];
      return new ParseList<DeclarationAssigneeAST>(assignee, assignee.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<DeclarationAssigneeAST> list = (ParseList<DeclarationAssigneeAST>) args[0];
      DeclarationAssigneeAST assignee = (DeclarationAssigneeAST) args[2];
      list.addLast(assignee, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], assignee.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

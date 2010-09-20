package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ASSIGNEE;
import static compiler.language.parser.ParseType.ASSIGNEE_LIST;
import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.misc.Assignee;
import compiler.language.ast.misc.FieldAssignee;
import compiler.language.ast.misc.QName;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class AssigneeListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  // this is a right-recursive list, so that the symbol after the list can be
  // used to decide between TypeList and AssigneeList
  private static final Production<ParseType> ASSIGNEE_END_PRODUCTION  = new Production<ParseType>(ASSIGNEE);
  private static final Production<ParseType> QNAME_END_PRODUCTION     = new Production<ParseType>(QNAME);
  private static final Production<ParseType> ASSIGNEE_LIST_PRODUCTION = new Production<ParseType>(ASSIGNEE, COMMA, ASSIGNEE_LIST);
  private static final Production<ParseType> QNAME_LIST_PRODUCTION    = new Production<ParseType>(QNAME,    COMMA, ASSIGNEE_LIST);

  @SuppressWarnings("unchecked")
  public AssigneeListRule()
  {
    super(ASSIGNEE_LIST, ASSIGNEE_END_PRODUCTION, QNAME_END_PRODUCTION, ASSIGNEE_LIST_PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (ASSIGNEE_END_PRODUCTION.equals(production))
    {
      Assignee assignee = (Assignee) args[0];
      return new ParseList<Assignee>(assignee, assignee.getParseInfo());
    }
    if (QNAME_END_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      FieldAccessExpression fieldAccess = new FieldAccessExpression(qname, qname.getParseInfo());
      Assignee assignee = new FieldAssignee(fieldAccess, fieldAccess.getParseInfo());
      return new ParseList<Assignee>(assignee, assignee.getParseInfo());
    }
    if (ASSIGNEE_LIST_PRODUCTION.equals(production))
    {
      Assignee assignee = (Assignee) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Assignee> list = (ParseList<Assignee>) args[2];
      list.addFirst(assignee, ParseInfo.combine(assignee.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      FieldAccessExpression fieldAccess = new FieldAccessExpression(qname, qname.getParseInfo());
      Assignee assignee = new FieldAssignee(fieldAccess, fieldAccess.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseList<Assignee> list = (ParseList<Assignee>) args[2];
      list.addFirst(assignee, ParseInfo.combine(assignee.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

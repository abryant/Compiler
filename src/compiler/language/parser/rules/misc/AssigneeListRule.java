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
import compiler.parser.ParseException;
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
  private static final Object[] ASSIGNEE_END_PRODUCTION  = new Object[] {ASSIGNEE};
  private static final Object[] QNAME_END_PRODUCTION     = new Object[] {QNAME};
  private static final Object[] ASSIGNEE_LIST_PRODUCTION = new Object[] {ASSIGNEE, COMMA, ASSIGNEE_LIST};
  private static final Object[] QNAME_LIST_PRODUCTION    = new Object[] {QNAME,    COMMA, ASSIGNEE_LIST};

  public AssigneeListRule()
  {
    super(ASSIGNEE_LIST, ASSIGNEE_END_PRODUCTION, QNAME_END_PRODUCTION, ASSIGNEE_LIST_PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == ASSIGNEE_END_PRODUCTION)
    {
      Assignee assignee = (Assignee) args[0];
      return new ParseList<Assignee>(assignee, assignee.getParseInfo());
    }
    if (types == QNAME_END_PRODUCTION)
    {
      QName qname = (QName) args[0];
      FieldAccessExpression fieldAccess = new FieldAccessExpression(qname, qname.getParseInfo());
      Assignee assignee = new FieldAssignee(fieldAccess, fieldAccess.getParseInfo());
      return new ParseList<Assignee>(assignee, assignee.getParseInfo());
    }
    if (types == ASSIGNEE_LIST_PRODUCTION)
    {
      Assignee assignee = (Assignee) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Assignee> list = (ParseList<Assignee>) args[2];
      list.addFirst(assignee, ParseInfo.combine(assignee.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (types == QNAME_LIST_PRODUCTION)
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

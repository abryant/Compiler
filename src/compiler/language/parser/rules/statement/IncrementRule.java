package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNEE;
import static compiler.language.parser.ParseType.DOUBLE_PLUS;
import static compiler.language.parser.ParseType.INCREMENT;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.misc.Assignee;
import compiler.language.ast.misc.FieldAssignee;
import compiler.language.ast.misc.QName;
import compiler.language.ast.statement.IncrementStatement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class IncrementRule extends Rule
{

  private static final Object[] PRE_PRODUCTION        = new Object[] {DOUBLE_PLUS, ASSIGNEE};
  private static final Object[] PRE_QNAME_PRODUCTION  = new Object[] {DOUBLE_PLUS, QNAME};
  private static final Object[] POST_PRODUCTION       = new Object[] {ASSIGNEE, DOUBLE_PLUS};
  private static final Object[] POST_QNAME_PRODUCTION = new Object[] {QNAME,    DOUBLE_PLUS};

  public IncrementRule()
  {
    super(INCREMENT, PRE_PRODUCTION, PRE_QNAME_PRODUCTION, POST_PRODUCTION, POST_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRE_PRODUCTION)
    {
      Assignee assignee = (Assignee) args[1];
      return new IncrementStatement(assignee, ParseInfo.combine((ParseInfo) args[0], assignee.getParseInfo()));
    }
    if (types == PRE_QNAME_PRODUCTION)
    {
      QName qname = (QName) args[1];
      FieldAccessExpression fieldAccess = new FieldAccessExpression(qname, qname.getParseInfo());
      Assignee assignee = new FieldAssignee(fieldAccess, fieldAccess.getParseInfo());
      return new IncrementStatement(assignee, ParseInfo.combine((ParseInfo) args[0], assignee.getParseInfo()));
    }
    if (types == POST_PRODUCTION)
    {
      Assignee assignee = (Assignee) args[0];
      return new IncrementStatement(assignee, ParseInfo.combine(assignee.getParseInfo(), (ParseInfo) args[1]));
    }
    if (types == POST_QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      FieldAccessExpression fieldAccess = new FieldAccessExpression(qname, qname.getParseInfo());
      Assignee assignee = new FieldAssignee(fieldAccess, fieldAccess.getParseInfo());
      return new IncrementStatement(assignee, ParseInfo.combine(assignee.getParseInfo(), (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}

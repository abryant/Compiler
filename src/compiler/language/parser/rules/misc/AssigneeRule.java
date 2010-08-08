package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ASSIGNEE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.UNDERSCORE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Assignee;
import compiler.language.ast.misc.QName;
import compiler.parser.Rule;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class AssigneeRule extends Rule
{

  private static final Object[] BLANK_PRODUCTION = new Object[] {UNDERSCORE};
  private static final Object[] PRODUCTION = new Object[] {QNAME}; // TODO: change this to FieldAccessExpression, and add another for ArrayAccessExpression

  public AssigneeRule()
  {
    super(ASSIGNEE, BLANK_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == BLANK_PRODUCTION)
    {
      return new Assignee((ParseInfo) args[0]);
    }
    if (types == PRODUCTION)
    {
      QName qname = (QName) args[0];
      return new Assignee(qname, qname.getParseInfo());
    }
    throw badTypeList();
  }

}

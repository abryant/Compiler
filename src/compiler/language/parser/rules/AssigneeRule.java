package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ASSIGNEE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.UNDERSCORE;

import compiler.language.ast.Assignee;
import compiler.language.ast.QName;
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
  private static final Object[] PRODUCTION = new Object[] {QNAME};

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
      return new Assignee();
    }
    if (types == PRODUCTION)
    {
      return new Assignee((QName) args[0]);
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ARRAY_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.ASSIGNEE;
import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.UNDERSCORE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ArrayAccessExpression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.misc.ArrayElementAssignee;
import compiler.language.ast.misc.Assignee;
import compiler.language.ast.misc.FieldAssignee;
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
  private static final Object[] FIELD_PRODUCTION = new Object[] {FIELD_ACCESS_EXPRESSION};
  private static final Object[] ARRAY_ELEMENT_PRODUCTION = new Object[] {ARRAY_ACCESS_EXPRESSION};

  public AssigneeRule()
  {
    super(ASSIGNEE, BLANK_PRODUCTION, FIELD_PRODUCTION, ARRAY_ELEMENT_PRODUCTION);
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
    if (types == FIELD_PRODUCTION)
    {
      FieldAccessExpression field = (FieldAccessExpression) args[0];
      return new FieldAssignee(field, field.getParseInfo());
    }
    if (types == ARRAY_ELEMENT_PRODUCTION)
    {
      ArrayAccessExpression arrayElement = (ArrayAccessExpression) args[0];
      return new ArrayElementAssignee(arrayElement, arrayElement.getParseInfo());
    }
    throw badTypeList();
  }

}

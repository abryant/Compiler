package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ARRAY_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.ASSIGNEE;
import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION_NOT_QNAME;
import static compiler.language.parser.ParseType.UNDERSCORE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ArrayAccessExpressionAST;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.misc.ArrayElementAssigneeAST;
import compiler.language.ast.misc.AssigneeAST;
import compiler.language.ast.misc.FieldAssigneeAST;
import compiler.language.parser.ParseType;

/*
 * Created on 5 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class AssigneeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> BLANK_PRODUCTION = new Production<ParseType>(UNDERSCORE);
  private static final Production<ParseType> FIELD_PRODUCTION = new Production<ParseType>(FIELD_ACCESS_EXPRESSION_NOT_QNAME);
  private static final Production<ParseType> ARRAY_ELEMENT_PRODUCTION = new Production<ParseType>(ARRAY_ACCESS_EXPRESSION);

  @SuppressWarnings("unchecked")
  public AssigneeRule()
  {
    super(ASSIGNEE, BLANK_PRODUCTION, FIELD_PRODUCTION, ARRAY_ELEMENT_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (BLANK_PRODUCTION.equals(production))
    {
      return new AssigneeAST((ParseInfo) args[0]);
    }
    if (FIELD_PRODUCTION.equals(production))
    {
      FieldAccessExpressionAST field = (FieldAccessExpressionAST) args[0];
      return new FieldAssigneeAST(field, field.getParseInfo());
    }
    if (ARRAY_ELEMENT_PRODUCTION.equals(production))
    {
      ArrayAccessExpressionAST arrayElement = (ArrayAccessExpressionAST) args[0];
      return new ArrayElementAssigneeAST(arrayElement, arrayElement.getParseInfo());
    }
    throw badTypeList();
  }

}

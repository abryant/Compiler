package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_OR_EXPRESSION;
import static compiler.language.parser.ParseType.COLON;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.INLINE_IF_EXPRESSION;
import static compiler.language.parser.ParseType.QUESTION_MARK;

import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.InlineIfExpression;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InlineIfExpressionRule extends Rule
{

  private static final Object[] NO_CHANGE_PRODUCTION = new Object[] {BOOLEAN_OR_EXPRESSION};
  private static final Object[] PRODUCTION = new Object[] {BOOLEAN_OR_EXPRESSION, QUESTION_MARK, EXPRESSION, COLON, INLINE_IF_EXPRESSION};

  public InlineIfExpressionRule()
  {
    super(INLINE_IF_EXPRESSION, NO_CHANGE_PRODUCTION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == NO_CHANGE_PRODUCTION)
    {
      // return the existing Expression
      return args[0];
    }
    if (types == PRODUCTION)
    {
      return new InlineIfExpression((Expression) args[0], (Expression) args[2], (Expression) args[4]);
    }
    throw badTypeList();
  }

}

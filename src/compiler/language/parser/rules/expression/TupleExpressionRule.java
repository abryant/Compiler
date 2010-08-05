package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.INLINE_IF_EXPRESSION;
import static compiler.language.parser.ParseType.TUPLE_EXPRESSION;

import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.TupleExpression;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {INLINE_IF_EXPRESSION};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {TUPLE_EXPRESSION, COMMA, INLINE_IF_EXPRESSION};

  public TupleExpressionRule()
  {
    super(TUPLE_EXPRESSION, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == START_PRODUCTION)
    {
      // return the already generated expression
      return args[0];
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      if (args[0] instanceof TupleExpression)
      {
        // continue the existing tuple if we've already started one
        return new TupleExpression((TupleExpression) args[0], (Expression) args[2]);
      }
      return new TupleExpression((Expression) args[0], (Expression) args[2]);
    }
    throw badTypeList();
  }

}

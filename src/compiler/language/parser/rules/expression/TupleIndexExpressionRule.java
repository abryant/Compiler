package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.EXCLAIMATION_MARK;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.TUPLE_INDEX_EXPRESSION;
import static compiler.language.parser.ParseType.UNARY_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.TupleIndexExpression;
import compiler.language.ast.terminal.IntegerLiteral;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleIndexExpressionRule extends Rule
{

  private static final Object[] NO_CHANGE_PRODUCTION = new Object[] {UNARY_EXPRESSION};
  private static final Object[] PRODUCTION = new Object[] {TUPLE_INDEX_EXPRESSION, EXCLAIMATION_MARK, INTEGER_LITERAL};

  public TupleIndexExpressionRule()
  {
    super(TUPLE_INDEX_EXPRESSION, NO_CHANGE_PRODUCTION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == NO_CHANGE_PRODUCTION)
    {
      // return the existing expression
      return args[0];
    }
    if (types == PRODUCTION)
    {
      Expression expression = (Expression) args[0];
      IntegerLiteral index = (IntegerLiteral) args[2];
      return new TupleIndexExpression(expression, index, ParseInfo.combine(expression.getParseInfo(), (ParseInfo) args[1], index.getParseInfo()));
    }
    throw badTypeList();
  }

}

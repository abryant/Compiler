package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.EXPRESSION_LIST;
import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ExpressionListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {EXPRESSION_NO_TUPLE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {EXPRESSION_LIST, COMMA, EXPRESSION_NO_TUPLE};

  public ExpressionListRule()
  {
    super(EXPRESSION_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      Expression expression = (Expression) args[0];
      return new ParseList<Expression>(expression, expression.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Expression> list = (ParseList<Expression>) args[0];
      Expression expression = (Expression) args[2];
      list.addLast(expression, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], expression.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

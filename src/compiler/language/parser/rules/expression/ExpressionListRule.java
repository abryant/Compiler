package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.EXPRESSION_LIST;
import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ExpressionListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION = new Production(EXPRESSION_NO_TUPLE);
  private static final Production CONTINUATION_PRODUCTION = new Production(EXPRESSION_LIST, COMMA, EXPRESSION_NO_TUPLE);

  public ExpressionListRule()
  {
    super(EXPRESSION_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[0];
      return new ParseList<Expression>(expression, expression.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
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

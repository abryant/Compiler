package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.EXPRESSION_LIST;
import static compiler.language.parser.ParseType.EXPRESSION_NO_TUPLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ExpressionListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(EXPRESSION_NO_TUPLE);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(EXPRESSION_LIST, COMMA, EXPRESSION_NO_TUPLE);

  @SuppressWarnings("unchecked")
  public ExpressionListRule()
  {
    super(EXPRESSION_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      ExpressionAST expression = (ExpressionAST) args[0];
      return new ParseList<ExpressionAST>(expression, expression.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ExpressionAST> list = (ParseList<ExpressionAST>) args[0];
      ExpressionAST expression = (ExpressionAST) args[2];
      list.addLast(expression, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], expression.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

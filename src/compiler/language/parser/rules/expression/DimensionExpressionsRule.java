package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DIMENSION_EXPRESSION;
import static compiler.language.parser.ParseType.DIMENSION_EXPRESSIONS;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class DimensionExpressionsRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(DIMENSION_EXPRESSION);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(DIMENSION_EXPRESSIONS, DIMENSION_EXPRESSION);

  @SuppressWarnings("unchecked")
  public DimensionExpressionsRule()
  {
    super(DIMENSION_EXPRESSIONS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<Expression> container = (ParseContainer<Expression>) args[0];
      return new ParseList<Expression>(container.getItem(), container.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Expression> list = (ParseList<Expression>) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<Expression> container = (ParseContainer<Expression>) args[1];
      list.addLast(container.getItem(), ParseInfo.combine(list.getParseInfo(), container.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

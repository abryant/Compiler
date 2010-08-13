package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DIMENSION_EXPRESSION;
import static compiler.language.parser.ParseType.DIMENSION_EXPRESSIONS;

import compiler.language.ast.ParseContainer;
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
public class DimensionExpressionsRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {DIMENSION_EXPRESSION};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {DIMENSION_EXPRESSIONS, DIMENSION_EXPRESSION};

  public DimensionExpressionsRule()
  {
    super(DIMENSION_EXPRESSIONS, START_PRODUCTION, CONTINUATION_PRODUCTION);
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
      @SuppressWarnings("unchecked")
      ParseContainer<Expression> container = (ParseContainer<Expression>) args[0];
      return new ParseList<Expression>(container.getItem(), container.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
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

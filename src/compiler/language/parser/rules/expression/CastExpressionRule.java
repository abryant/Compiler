package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.CAST_EXPRESSION;
import static compiler.language.parser.ParseType.CAST_KEYWORD;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.TYPE_RANGLE;
import static compiler.language.parser.ParseType.UNARY_EXPRESSION;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.CastExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CastExpressionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {CAST_KEYWORD, LANGLE, TYPE_RANGLE, UNARY_EXPRESSION};

  public CastExpressionRule()
  {
    super(CAST_EXPRESSION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<Type> type = (ParseContainer<Type>) args[2];
      Expression expression = (Expression) args[3];
      return new CastExpression(type.getItem(), expression,
                                ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], type.getParseInfo(), expression.getParseInfo()));
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.CAST_EXPRESSION;
import static compiler.language.parser.ParseType.CAST_KEYWORD;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.TYPE_RANGLE;
import static compiler.language.parser.ParseType.UNARY_EXPRESSION;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.CastExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CastExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(CAST_KEYWORD, LANGLE, TYPE_RANGLE, UNARY_EXPRESSION);
  private static final Production QNAME_PRODUCTION = new Production(CAST_KEYWORD, LANGLE, TYPE_RANGLE, QNAME_EXPRESSION);

  public CastExpressionRule()
  {
    super(CAST_EXPRESSION, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production) || QNAME_PRODUCTION.equals(production))
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

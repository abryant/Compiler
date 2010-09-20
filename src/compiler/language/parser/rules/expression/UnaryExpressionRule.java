package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.CAST_EXPRESSION;
import static compiler.language.parser.ParseType.EXCLAIMATION_MARK;
import static compiler.language.parser.ParseType.MINUS;
import static compiler.language.parser.ParseType.PLUS;
import static compiler.language.parser.ParseType.PRIMARY;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.TILDE;
import static compiler.language.parser.ParseType.UNARY_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BitwiseNotExpression;
import compiler.language.ast.expression.BooleanNotExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.UnaryMinusExpression;
import compiler.language.ast.expression.UnaryPlusExpression;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class UnaryExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRIMARY_PRODUCTION = new Production<ParseType>(PRIMARY);
  private static final Production<ParseType> CAST_PRODUCTION = new Production<ParseType>(CAST_EXPRESSION);

  private static final Production<ParseType> UNARY_PLUS_PRODUCTION = new Production<ParseType>(PLUS, UNARY_EXPRESSION);
  private static final Production<ParseType> UNARY_PLUS_QNAME_PRODUCTION = new Production<ParseType>(PLUS, QNAME_EXPRESSION);
  private static final Production<ParseType> UNARY_MINUS_PRODUCTION = new Production<ParseType>(MINUS, UNARY_EXPRESSION);
  private static final Production<ParseType> UNARY_MINUS_QNAME_PRODUCTION = new Production<ParseType>(MINUS, QNAME_EXPRESSION);
  private static final Production<ParseType> BOOLEAN_NOT_PRODUCTION = new Production<ParseType>(EXCLAIMATION_MARK, UNARY_EXPRESSION);
  private static final Production<ParseType> BOOLEAN_NOT_QNAME_PRODUCTION = new Production<ParseType>(EXCLAIMATION_MARK, QNAME_EXPRESSION);
  private static final Production<ParseType> BITWISE_NOT_PRODUCTION = new Production<ParseType>(TILDE, UNARY_EXPRESSION);
  private static final Production<ParseType> BITWISE_NOT_QNAME_PRODUCTION = new Production<ParseType>(TILDE, QNAME_EXPRESSION);


  @SuppressWarnings("unchecked")
  public UnaryExpressionRule()
  {
    super(UNARY_EXPRESSION, PRIMARY_PRODUCTION,     CAST_PRODUCTION,
                            UNARY_PLUS_PRODUCTION,  UNARY_PLUS_QNAME_PRODUCTION,
                            UNARY_MINUS_PRODUCTION, UNARY_MINUS_QNAME_PRODUCTION,
                            BOOLEAN_NOT_PRODUCTION, BOOLEAN_NOT_QNAME_PRODUCTION,
                            BITWISE_NOT_PRODUCTION, BITWISE_NOT_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRIMARY_PRODUCTION.equals(production) || CAST_PRODUCTION.equals(production))
    {
      // the expression has already been built, so return it
      return args[0];
    }
    if (UNARY_PLUS_PRODUCTION.equals(production) || UNARY_PLUS_QNAME_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[1];
      return new UnaryPlusExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo()));
    }
    if (UNARY_MINUS_PRODUCTION.equals(production) || UNARY_MINUS_QNAME_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[1];
      return new UnaryMinusExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo()));
    }
    if (BOOLEAN_NOT_PRODUCTION.equals(production) || BOOLEAN_NOT_QNAME_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[1];
      return new BooleanNotExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo()));
    }
    if (BITWISE_NOT_PRODUCTION.equals(production) || BITWISE_NOT_QNAME_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[1];
      return new BitwiseNotExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo()));
    }
    throw badTypeList();
  }

}

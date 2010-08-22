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
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class UnaryExpressionRule extends Rule
{

  private static final Object[] PRIMARY_PRODUCTION = new Object[] {PRIMARY};
  private static final Object[] CAST_PRODUCTION = new Object[] {CAST_EXPRESSION};

  private static final Object[] UNARY_PLUS_PRODUCTION = new Object[] {PLUS, UNARY_EXPRESSION};
  private static final Object[] UNARY_PLUS_QNAME_PRODUCTION = new Object[] {PLUS, QNAME_EXPRESSION};
  private static final Object[] UNARY_MINUS_PRODUCTION = new Object[] {MINUS, UNARY_EXPRESSION};
  private static final Object[] UNARY_MINUS_QNAME_PRODUCTION = new Object[] {MINUS, QNAME_EXPRESSION};
  private static final Object[] BOOLEAN_NOT_PRODUCTION = new Object[] {EXCLAIMATION_MARK, UNARY_EXPRESSION};
  private static final Object[] BOOLEAN_NOT_QNAME_PRODUCTION = new Object[] {EXCLAIMATION_MARK, QNAME_EXPRESSION};
  private static final Object[] BITWISE_NOT_PRODUCTION = new Object[] {TILDE, UNARY_EXPRESSION};
  private static final Object[] BITWISE_NOT_QNAME_PRODUCTION = new Object[] {TILDE, QNAME_EXPRESSION};


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
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRIMARY_PRODUCTION || types == CAST_PRODUCTION)
    {
      // the expression has already been built, so return it
      return args[0];
    }
    if (types == UNARY_PLUS_PRODUCTION || types == UNARY_PLUS_QNAME_PRODUCTION)
    {
      Expression expression = (Expression) args[1];
      return new UnaryPlusExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo()));
    }
    if (types == UNARY_MINUS_PRODUCTION || types == UNARY_MINUS_QNAME_PRODUCTION)
    {
      Expression expression = (Expression) args[1];
      return new UnaryMinusExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo()));
    }
    if (types == BOOLEAN_NOT_PRODUCTION || types == BOOLEAN_NOT_QNAME_PRODUCTION)
    {
      Expression expression = (Expression) args[1];
      return new BooleanNotExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo()));
    }
    if (types == BITWISE_NOT_PRODUCTION || types == BITWISE_NOT_QNAME_PRODUCTION)
    {
      Expression expression = (Expression) args[1];
      return new BitwiseNotExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo()));
    }
    throw badTypeList();
  }

}

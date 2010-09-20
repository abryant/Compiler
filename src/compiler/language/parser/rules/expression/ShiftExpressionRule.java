package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ADDITIVE_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_LANGLE;
import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.SHIFT_EXPRESSION;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.expression.ShiftExpression;
import compiler.language.ast.expression.ShiftExpressionType;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ShiftExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION                                     = new Production<ParseType>(ADDITIVE_EXPRESSION);
  private static final Production<ParseType> LEFT_SHIFT_PRODUCTION                                = new Production<ParseType>(SHIFT_EXPRESSION,  DOUBLE_LANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> LEFT_SHIFT_QNAME_PRODUCTION                          = new Production<ParseType>(SHIFT_EXPRESSION,  DOUBLE_LANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_LEFT_SHIFT_PRODUCTION                          = new Production<ParseType>(QNAME_EXPRESSION,  DOUBLE_LANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> QNAME_LEFT_SHIFT_QNAME_PRODUCTION                    = new Production<ParseType>(QNAME_EXPRESSION,  DOUBLE_LANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> ARITHMETIC_RIGHT_SHIFT_PRODUCTION                    = new Production<ParseType>(SHIFT_EXPRESSION,  DOUBLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION              = new Production<ParseType>(SHIFT_EXPRESSION,  DOUBLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION              = new Production<ParseType>(QNAME,             DOUBLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION        = new Production<ParseType>(QNAME,             DOUBLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION       = new Production<ParseType>(NESTED_QNAME_LIST, DOUBLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST, DOUBLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> LOGICAL_RIGHT_SHIFT_PRODUCTION                       = new Production<ParseType>(SHIFT_EXPRESSION,  TRIPLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION                 = new Production<ParseType>(SHIFT_EXPRESSION,  TRIPLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION                 = new Production<ParseType>(QNAME,             TRIPLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION           = new Production<ParseType>(QNAME,             TRIPLE_RANGLE, QNAME_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION          = new Production<ParseType>(NESTED_QNAME_LIST, TRIPLE_RANGLE, ADDITIVE_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION    = new Production<ParseType>(NESTED_QNAME_LIST, TRIPLE_RANGLE, QNAME_EXPRESSION);

  @SuppressWarnings("unchecked")
  public ShiftExpressionRule()
  {
    super(SHIFT_EXPRESSION, START_PRODUCTION,
                            LEFT_SHIFT_PRODUCTION,                          LEFT_SHIFT_QNAME_PRODUCTION,
                            QNAME_LEFT_SHIFT_PRODUCTION,                    QNAME_LEFT_SHIFT_QNAME_PRODUCTION,
                            ARITHMETIC_RIGHT_SHIFT_PRODUCTION,              ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION,
                            QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION,        QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION,
                            NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION, NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION,
                            LOGICAL_RIGHT_SHIFT_PRODUCTION,                 LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION,
                            QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION,           QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION,
                            NESTED_QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION,    NESTED_QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION);
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
      // return the existing Expression
      return args[0];
    }

    Expression firstExpression;
    ShiftExpressionType separator;
    if (LEFT_SHIFT_PRODUCTION.equals(production)       || LEFT_SHIFT_QNAME_PRODUCTION.equals(production) ||
        QNAME_LEFT_SHIFT_PRODUCTION.equals(production) || QNAME_LEFT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      firstExpression = (Expression) args[0];
      separator = ShiftExpressionType.LEFT_SHIFT;
    }
    else if (ARITHMETIC_RIGHT_SHIFT_PRODUCTION.equals(production)              || ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      firstExpression = (Expression) args[0];
      separator = ShiftExpressionType.ARITHMETIC_RIGHT_SHIFT;
    }
    else if (QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION.equals(production)        || QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      firstExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      separator = ShiftExpressionType.ARITHMETIC_RIGHT_SHIFT;
    }
    else if (NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION.equals(production) || NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      firstExpression = element.toExpression();
      separator = ShiftExpressionType.ARITHMETIC_RIGHT_SHIFT;
    }
    else if (LOGICAL_RIGHT_SHIFT_PRODUCTION.equals(production)                 || LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      firstExpression = (Expression) args[0];
      separator = ShiftExpressionType.LOGICAL_RIGHT_SHIFT;
    }
    else if (QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION.equals(production)           || QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      firstExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      separator = ShiftExpressionType.LOGICAL_RIGHT_SHIFT;
    }
    else if (NESTED_QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION.equals(production)    || NESTED_QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      firstExpression = element.toExpression();
      separator = ShiftExpressionType.LOGICAL_RIGHT_SHIFT;
    }
    else
    {
      throw badTypeList();
    }


    Expression secondExpression = (Expression) args[2];
    if (firstExpression instanceof ShiftExpression)
    {
      // continue the existing ShiftExpression if we've already started one
      ShiftExpression startExpression = (ShiftExpression) args[0];
      return new ShiftExpression(startExpression, separator, secondExpression,
                                 ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    return new ShiftExpression(firstExpression, separator, secondExpression,
                               ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
  }

}

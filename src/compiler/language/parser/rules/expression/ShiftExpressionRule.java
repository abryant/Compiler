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
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ShiftExpressionRule extends Rule
{

  private static final Object[] START_PRODUCTION                                     = new Object[] {ADDITIVE_EXPRESSION};
  private static final Object[] LEFT_SHIFT_PRODUCTION                                = new Object[] {SHIFT_EXPRESSION,  DOUBLE_LANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] LEFT_SHIFT_QNAME_PRODUCTION                          = new Object[] {SHIFT_EXPRESSION,  DOUBLE_LANGLE, QNAME_EXPRESSION};
  private static final Object[] QNAME_LEFT_SHIFT_PRODUCTION                          = new Object[] {QNAME_EXPRESSION,  DOUBLE_LANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] QNAME_LEFT_SHIFT_QNAME_PRODUCTION                    = new Object[] {QNAME_EXPRESSION,  DOUBLE_LANGLE, QNAME_EXPRESSION};
  private static final Object[] ARITHMETIC_RIGHT_SHIFT_PRODUCTION                    = new Object[] {SHIFT_EXPRESSION,  DOUBLE_RANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION              = new Object[] {SHIFT_EXPRESSION,  DOUBLE_RANGLE, QNAME_EXPRESSION};
  private static final Object[] QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION              = new Object[] {QNAME,             DOUBLE_RANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION        = new Object[] {QNAME,             DOUBLE_RANGLE, QNAME_EXPRESSION};
  private static final Object[] NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION       = new Object[] {NESTED_QNAME_LIST, DOUBLE_RANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION = new Object[] {NESTED_QNAME_LIST, DOUBLE_RANGLE, QNAME_EXPRESSION};
  private static final Object[] LOGICAL_RIGHT_SHIFT_PRODUCTION                       = new Object[] {SHIFT_EXPRESSION,  TRIPLE_RANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION                 = new Object[] {SHIFT_EXPRESSION,  TRIPLE_RANGLE, QNAME_EXPRESSION};
  private static final Object[] QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION                 = new Object[] {QNAME,             TRIPLE_RANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION           = new Object[] {QNAME,             TRIPLE_RANGLE, QNAME_EXPRESSION};
  private static final Object[] NESTED_QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION          = new Object[] {NESTED_QNAME_LIST, TRIPLE_RANGLE, ADDITIVE_EXPRESSION};
  private static final Object[] NESTED_QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION    = new Object[] {NESTED_QNAME_LIST, TRIPLE_RANGLE, QNAME_EXPRESSION};

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
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      // return the existing Expression
      return args[0];
    }

    Expression firstExpression;
    ShiftExpressionType separator;
    if (types == LEFT_SHIFT_PRODUCTION       || types == LEFT_SHIFT_QNAME_PRODUCTION ||
        types == QNAME_LEFT_SHIFT_PRODUCTION || types == QNAME_LEFT_SHIFT_QNAME_PRODUCTION)
    {
      firstExpression = (Expression) args[0];
      separator = ShiftExpressionType.LEFT_SHIFT;
    }
    else if (types == ARITHMETIC_RIGHT_SHIFT_PRODUCTION              || types == ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION)
    {
      firstExpression = (Expression) args[0];
      separator = ShiftExpressionType.ARITHMETIC_RIGHT_SHIFT;
    }
    else if (types == QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION        || types == QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      firstExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      separator = ShiftExpressionType.ARITHMETIC_RIGHT_SHIFT;
    }
    else if (types == NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_PRODUCTION || types == NESTED_QNAME_ARITHMETIC_RIGHT_SHIFT_QNAME_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      firstExpression = element.toExpression();
      separator = ShiftExpressionType.ARITHMETIC_RIGHT_SHIFT;
    }
    else if (types == LOGICAL_RIGHT_SHIFT_PRODUCTION                 || types == LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION)
    {
      firstExpression = (Expression) args[0];
      separator = ShiftExpressionType.LOGICAL_RIGHT_SHIFT;
    }
    else if (types == QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION           || types == QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      firstExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      separator = ShiftExpressionType.LOGICAL_RIGHT_SHIFT;
    }
    else if (types == NESTED_QNAME_LOGICAL_RIGHT_SHIFT_PRODUCTION    || types == NESTED_QNAME_LOGICAL_RIGHT_SHIFT_QNAME_PRODUCTION)
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

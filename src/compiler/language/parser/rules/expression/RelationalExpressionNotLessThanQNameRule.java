package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BITWISE_OR_EXPRESSION;
import static compiler.language.parser.ParseType.INSTANCEOF_KEYWORD;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.LANGLE_EQUALS;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.RANGLE_EQUALS;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME;
import static compiler.language.parser.ParseType.TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.expression.InstanceOfExpression;
import compiler.language.ast.expression.RelationalExpression;
import compiler.language.ast.expression.RelationalExpressionType;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class RelationalExpressionNotLessThanQNameRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production NO_CHANGE_PRODUCTION                       = new Production(BITWISE_OR_EXPRESSION);

  private static final Production LESS_THAN_PRODUCTION                       = new Production(BITWISE_OR_EXPRESSION, LANGLE,        BITWISE_OR_EXPRESSION);
  private static final Production LESS_THAN_QNAME_PRODUCTION                 = new Production(BITWISE_OR_EXPRESSION, LANGLE,        QNAME_EXPRESSION);
  private static final Production QNAME_LESS_THAN_PRODUCTION                 = new Production(QNAME,                 LANGLE,        BITWISE_OR_EXPRESSION);
  private static final Production NESTED_QNAME_LESS_THAN_PRODUCTION          = new Production(NESTED_QNAME_LIST,     LANGLE,        BITWISE_OR_EXPRESSION);


  private static final Production GREATER_THAN_PRODUCTION                    = new Production(BITWISE_OR_EXPRESSION, RANGLE,        BITWISE_OR_EXPRESSION);
  private static final Production GREATER_THAN_QNAME_PRODUCTION              = new Production(BITWISE_OR_EXPRESSION, RANGLE,        QNAME_EXPRESSION);
  private static final Production QNAME_GREATER_THAN_PRODUCTION              = new Production(QNAME,                 RANGLE,        BITWISE_OR_EXPRESSION);
  private static final Production QNAME_GREATER_THAN_QNAME_PRODUCTION        = new Production(QNAME,                 RANGLE,        QNAME_EXPRESSION);
  private static final Production NESTED_QNAME_GREATER_THAN_PRODUCTION       = new Production(NESTED_QNAME_LIST,     RANGLE,        BITWISE_OR_EXPRESSION);
  private static final Production NESTED_QNAME_GREATER_THAN_QNAME_PRODUCTION = new Production(NESTED_QNAME_LIST,     RANGLE,        QNAME_EXPRESSION);
  private static final Production LESS_THAN_EQUAL_PRODUCTION                 = new Production(BITWISE_OR_EXPRESSION, LANGLE_EQUALS, BITWISE_OR_EXPRESSION);
  private static final Production LESS_THAN_EQUAL_QNAME_PRODUCTION           = new Production(BITWISE_OR_EXPRESSION, LANGLE_EQUALS, QNAME_EXPRESSION);
  private static final Production QNAME_LESS_THAN_EQUAL_PRODUCTION           = new Production(QNAME_EXPRESSION,      LANGLE_EQUALS, BITWISE_OR_EXPRESSION);
  private static final Production QNAME_LESS_THAN_EQUAL_QNAME_PRODUCTION     = new Production(QNAME_EXPRESSION,      LANGLE_EQUALS, QNAME_EXPRESSION);
  private static final Production GREATER_THAN_EQUAL_PRODUCTION              = new Production(BITWISE_OR_EXPRESSION, RANGLE_EQUALS, BITWISE_OR_EXPRESSION);
  private static final Production GREATER_THAN_EQUAL_QNAME_PRODUCTION        = new Production(BITWISE_OR_EXPRESSION, RANGLE_EQUALS, QNAME_EXPRESSION);
  private static final Production QNAME_GREATER_THAN_EQUAL_PRODUCTION        = new Production(QNAME_EXPRESSION,      RANGLE_EQUALS, BITWISE_OR_EXPRESSION);
  private static final Production QNAME_GREATER_THAN_EQUAL_QNAME_PRODUCTION  = new Production(QNAME_EXPRESSION,      RANGLE_EQUALS, QNAME_EXPRESSION);

  private static final Production INSTANCE_OF_PRODUCTION                     = new Production(BITWISE_OR_EXPRESSION, INSTANCEOF_KEYWORD, TYPE);
  private static final Production QNAME_INSTANCE_OF_PRODUCTION               = new Production(QNAME_EXPRESSION,      INSTANCEOF_KEYWORD, TYPE);

  public RelationalExpressionNotLessThanQNameRule()
  {
    super(RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME, NO_CHANGE_PRODUCTION,
                                                     LESS_THAN_PRODUCTION,                 LESS_THAN_QNAME_PRODUCTION,
                                                     QNAME_LESS_THAN_PRODUCTION,           NESTED_QNAME_LESS_THAN_PRODUCTION,
                                                     GREATER_THAN_PRODUCTION,              GREATER_THAN_QNAME_PRODUCTION,
                                                     QNAME_GREATER_THAN_PRODUCTION,        QNAME_GREATER_THAN_QNAME_PRODUCTION,
                                                     NESTED_QNAME_GREATER_THAN_PRODUCTION, NESTED_QNAME_GREATER_THAN_QNAME_PRODUCTION,
                                                     LESS_THAN_EQUAL_PRODUCTION,           LESS_THAN_EQUAL_QNAME_PRODUCTION,
                                                     QNAME_LESS_THAN_EQUAL_PRODUCTION,     QNAME_LESS_THAN_EQUAL_QNAME_PRODUCTION,
                                                     GREATER_THAN_EQUAL_PRODUCTION,        GREATER_THAN_EQUAL_QNAME_PRODUCTION,
                                                     QNAME_GREATER_THAN_EQUAL_PRODUCTION,  QNAME_GREATER_THAN_EQUAL_QNAME_PRODUCTION,
                                                     INSTANCE_OF_PRODUCTION,               QNAME_INSTANCE_OF_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (NO_CHANGE_PRODUCTION.equals(production))
    {
      // return the existing Expression
      return args[0];
    }
    if (INSTANCE_OF_PRODUCTION.equals(production) || QNAME_INSTANCE_OF_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[0];
      Type type = (Type) args[2];
      return new InstanceOfExpression(expression, type, ParseInfo.combine(expression.getParseInfo(), (ParseInfo) args[1], type.getParseInfo()));
    }
    // handle the special case QName rules for Less Than
    if (QNAME_LESS_THAN_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      Expression firstExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      Expression secondExpression = (Expression) args[2];
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (NESTED_QNAME_LESS_THAN_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      Expression firstExpression = element.toExpression();
      Expression secondExpression = (Expression) args[2];
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    // handle the special case QName rules for Greater Than
    if (QNAME_GREATER_THAN_PRODUCTION.equals(production) || QNAME_GREATER_THAN_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      Expression firstExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      Expression secondExpression = (Expression) args[2];
      return new RelationalExpression(firstExpression, RelationalExpressionType.GREATER_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (NESTED_QNAME_GREATER_THAN_PRODUCTION.equals(production) || NESTED_QNAME_GREATER_THAN_QNAME_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      Expression firstExpression = element.toExpression();
      Expression secondExpression = (Expression) args[2];
      return new RelationalExpression(firstExpression, RelationalExpressionType.GREATER_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }


    // the only remaining productions are RelationalExpression productions
    RelationalExpressionType separator = null;
    if (LESS_THAN_PRODUCTION.equals(production) || LESS_THAN_QNAME_PRODUCTION.equals(production))
    {
      separator = RelationalExpressionType.LESS_THAN;
    }
    else if (GREATER_THAN_PRODUCTION.equals(production)       || GREATER_THAN_QNAME_PRODUCTION.equals(production))
    {
      separator = RelationalExpressionType.GREATER_THAN;
    }
    else if (LESS_THAN_EQUAL_PRODUCTION.equals(production)       || LESS_THAN_EQUAL_QNAME_PRODUCTION.equals(production) ||
             QNAME_LESS_THAN_EQUAL_PRODUCTION.equals(production) || QNAME_LESS_THAN_EQUAL_QNAME_PRODUCTION.equals(production))
    {
      separator = RelationalExpressionType.LESS_THAN_EQUAL;
    }
    else if (GREATER_THAN_EQUAL_PRODUCTION.equals(production)       || GREATER_THAN_EQUAL_QNAME_PRODUCTION.equals(production) ||
             QNAME_GREATER_THAN_EQUAL_PRODUCTION.equals(production) || QNAME_GREATER_THAN_EQUAL_QNAME_PRODUCTION.equals(production))
    {
      separator = RelationalExpressionType.GREATER_THAN_EQUAL;
    }
    else
    {
      throw badTypeList();
    }

    Expression firstExpression = (Expression) args[0];
    Expression lastExpression = (Expression) args[2];
    return new RelationalExpression(firstExpression, separator, lastExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], lastExpression.getParseInfo()));
  }

}

package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.INLINE_IF_EXPRESSION;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.QNAME_LIST;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_LESS_THAN_QNAME;
import static compiler.language.parser.ParseType.TUPLE_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.expression.RelationalExpression;
import compiler.language.ast.expression.RelationalExpressionType;
import compiler.language.ast.expression.TupleExpression;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleExpressionRule extends Rule
{

  private static final Object[] QNAME_PRODUCTION                               = new Object[] {QNAME,                                        COMMA, TUPLE_EXPRESSION};
  private static final Object[] NESTED_QNAME_LIST_PRODUCTION                   = new Object[] {NESTED_QNAME_LIST,                            COMMA, TUPLE_EXPRESSION};
  private static final Object[] QNAME_LESS_THAN_QNAME_PRODUCTION               = new Object[] {QNAME,             LANGLE, QNAME,             COMMA, TUPLE_EXPRESSION};
  private static final Object[] QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION        = new Object[] {QNAME,             LANGLE, NESTED_QNAME_LIST, COMMA, TUPLE_EXPRESSION};
  private static final Object[] NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION        = new Object[] {NESTED_QNAME_LIST, LANGLE, QNAME,             COMMA, TUPLE_EXPRESSION};
  private static final Object[] NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION = new Object[] {NESTED_QNAME_LIST, LANGLE, NESTED_QNAME_LIST, COMMA, TUPLE_EXPRESSION};
  private static final Object[] INLINE_IF_TUPLE_PRODUCTION                     = new Object[] {INLINE_IF_EXPRESSION,                         COMMA, TUPLE_EXPRESSION};
  private static final Object[] INLINE_IF_QNAME_LIST_PRODUCTION                = new Object[] {INLINE_IF_EXPRESSION,                         COMMA, QNAME_LIST};
  private static final Object[] INLINE_IF_PRODUCTION                           = new Object[] {INLINE_IF_EXPRESSION};
  private static final Object[] LESS_THAN_PRODUCTION                           = new Object[] {RELATIONAL_EXPRESSION_LESS_THAN_QNAME};

  public TupleExpressionRule()
  {
    super(TUPLE_EXPRESSION, QNAME_PRODUCTION,
                            NESTED_QNAME_LIST_PRODUCTION,
                            QNAME_LESS_THAN_QNAME_PRODUCTION,
                            QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION,
                            NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION,
                            NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION,
                            INLINE_IF_TUPLE_PRODUCTION,
                            INLINE_IF_QNAME_LIST_PRODUCTION,
                            INLINE_IF_PRODUCTION,
                            LESS_THAN_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == INLINE_IF_PRODUCTION || types == LESS_THAN_PRODUCTION)
    {
      // return the existing expression
      return args[0];
    }
    if (types == INLINE_IF_QNAME_LIST_PRODUCTION)
    {
      Expression firstExpression = (Expression) args[0];
      @SuppressWarnings("unchecked")
      ParseList<QNameElement> qnameList = (ParseList<QNameElement>) args[2];

      QNameElement[] elements = qnameList.toArray(new QNameElement[0]);
      Expression[] expressions = new Expression[elements.length + 1];
      expressions[0] = firstExpression;
      for (int i = 0; i < elements.length; i++)
      {
        expressions[i + 1] = elements[i].toExpression();
      }
      return new TupleExpression(expressions, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], qnameList.getParseInfo()));
    }

    // the rest of the productions are in the form: X COMMA TUPLE_EXPRESSION,
    // so find the expressions and ParseInfo based on the production, and share
    // code for combining it with the rest of the production
    Expression firstExpression;
    Expression secondExpression;
    ParseInfo finalParseInfo;
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      firstExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      secondExpression = (Expression) args[2];
      finalParseInfo = ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo());
    }
    else if (types == NESTED_QNAME_LIST_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      firstExpression = element.toExpression();
      secondExpression = (Expression) args[2];
      finalParseInfo = ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo());
    }
    else if (types == INLINE_IF_TUPLE_PRODUCTION)
    {
      firstExpression = (Expression) args[0];
      secondExpression = (Expression) args[2];
      finalParseInfo = ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo());
    }
    else if (types == QNAME_LESS_THAN_QNAME_PRODUCTION)
    {
      QName firstQName = (QName) args[0];
      QName secondQName = (QName) args[2];
      Expression leftExpression = new FieldAccessExpression(firstQName, firstQName.getParseInfo());
      Expression rightExpression = new FieldAccessExpression(secondQName, secondQName.getParseInfo());
      firstExpression = new RelationalExpression(leftExpression, RelationalExpressionType.LESS_THAN, rightExpression,
                              ParseInfo.combine(leftExpression.getParseInfo(), (ParseInfo) args[1], rightExpression.getParseInfo()));
      secondExpression = (Expression) args[4];
      finalParseInfo = ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[3], secondExpression.getParseInfo());
    }
    else if (types == QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      QNameElement element = (QNameElement) args[2];
      Expression leftExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      Expression rightExpression = element.toExpression();
      firstExpression = new RelationalExpression(leftExpression, RelationalExpressionType.LESS_THAN, rightExpression,
                              ParseInfo.combine(leftExpression.getParseInfo(), (ParseInfo) args[1], rightExpression.getParseInfo()));
      secondExpression = (Expression) args[4];
      finalParseInfo = ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[3], secondExpression.getParseInfo());
    }
    else if (types == NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      QName qname = (QName) args[2];
      Expression leftExpression = element.toExpression();
      Expression rightExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      firstExpression = new RelationalExpression(leftExpression, RelationalExpressionType.LESS_THAN, rightExpression,
                              ParseInfo.combine(leftExpression.getParseInfo(), (ParseInfo) args[1], rightExpression.getParseInfo()));
      secondExpression = (Expression) args[4];
      finalParseInfo = ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[3], secondExpression.getParseInfo());
    }
    else if (types == NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION)
    {
      QNameElement firstElement = (QNameElement) args[0];
      QNameElement secondElement = (QNameElement) args[2];
      Expression leftExpression = firstElement.toExpression();
      Expression rightExpression = secondElement.toExpression();
      firstExpression = new RelationalExpression(leftExpression, RelationalExpressionType.LESS_THAN, rightExpression,
                              ParseInfo.combine(leftExpression.getParseInfo(), (ParseInfo) args[1], rightExpression.getParseInfo()));
      secondExpression = (Expression) args[4];
      finalParseInfo = ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[3], secondExpression.getParseInfo());
    }
    else
    {
      throw badTypeList();
    }

    if (secondExpression instanceof TupleExpression)
    {
      TupleExpression tupleExpression = (TupleExpression) secondExpression;
      Expression[] oldExpressions = tupleExpression.getExpressions();
      Expression[] newExpressions = new Expression[oldExpressions.length + 1];
      System.arraycopy(oldExpressions, 0, newExpressions, 1, oldExpressions.length);
      newExpressions[0] = firstExpression;
      return new TupleExpression(newExpressions, finalParseInfo);
    }
    Expression[] expressions = new Expression[] {firstExpression, secondExpression};
    return new TupleExpression(expressions, finalParseInfo);
  }

}

package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_LIST;
import static compiler.language.parser.ParseType.TUPLE_EXPRESSION;

import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.TupleExpression;
import compiler.language.ast.misc.QNameElement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ExpressionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {TUPLE_EXPRESSION};
  private static final Object[] QNAME_LIST_PRODUCTION = new Object[] {QNAME_LIST};

  public ExpressionRule()
  {
    super(EXPRESSION, PRODUCTION, QNAME_LIST_PRODUCTION);
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
      // return the already generated expression
      return args[0];
    }
    if (types == QNAME_LIST_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<QNameElement> qnameElements = (ParseList<QNameElement>) args[0];
      QNameElement[] elements = qnameElements.toArray(new QNameElement[0]);
      Expression[] expressions = new Expression[elements.length];
      for (int i = 0; i < elements.length; i++)
      {
        expressions[i] = elements[i].toExpression();
      }
      return new TupleExpression(expressions, qnameElements.getParseInfo());
    }
    throw badTypeList();
  }

}

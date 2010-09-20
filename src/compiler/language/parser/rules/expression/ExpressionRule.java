package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_LIST;
import static compiler.language.parser.ParseType.TUPLE_EXPRESSION;

import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.TupleExpression;
import compiler.language.ast.misc.QNameElement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(TUPLE_EXPRESSION);
  private static final Production QNAME_LIST_PRODUCTION = new Production(QNAME_LIST);

  public ExpressionRule()
  {
    super(EXPRESSION, PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      // return the already generated expression
      return args[0];
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
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

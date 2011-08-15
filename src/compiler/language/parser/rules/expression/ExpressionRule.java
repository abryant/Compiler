package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_LIST;
import static compiler.language.parser.ParseType.TUPLE_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.TupleExpressionAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(TUPLE_EXPRESSION);
  private static final Production<ParseType> QNAME_LIST_PRODUCTION = new Production<ParseType>(QNAME_LIST);

  @SuppressWarnings("unchecked")
  public ExpressionRule()
  {
    super(EXPRESSION, PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      // return the already generated expression
      return args[0];
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<QNameElementAST> qnameElements = (ParseList<QNameElementAST>) args[0];
      QNameElementAST[] elements = qnameElements.toArray(new QNameElementAST[0]);
      ExpressionAST[] expressions = new ExpressionAST[elements.length];
      for (int i = 0; i < elements.length; i++)
      {
        expressions[i] = elements[i].toExpression();
      }
      return new TupleExpressionAST(expressions, qnameElements.getLexicalPhrase());
    }
    throw badTypeList();
  }

}

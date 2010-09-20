package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.METHOD_CALL_EXPRESSION;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.PRIMARY;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.MethodCallExpression;
import compiler.language.ast.misc.Parameter;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class MethodCallExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(PRIMARY, PARAMETERS);
  private static final Production QNAME_PRODUCTION = new Production(QNAME_EXPRESSION, PARAMETERS);

  public MethodCallExpressionRule()
  {
    super(METHOD_CALL_EXPRESSION, PRODUCTION, QNAME_PRODUCTION);
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
      // both of the productions produce the same types of results, so we can
      // use the same code to generate the MethodCallExpression
      Expression expression = (Expression) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Parameter> parameters = (ParseList<Parameter>) args[1];
      return new MethodCallExpression(expression, parameters.toArray(new Parameter[0]),
                                      ParseInfo.combine(expression.getParseInfo(), parameters.getParseInfo()));
    }
    throw badTypeList();
  }

}

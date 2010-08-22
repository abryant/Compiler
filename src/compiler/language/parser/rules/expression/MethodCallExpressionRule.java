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
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class MethodCallExpressionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {PRIMARY, PARAMETERS};
  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME_EXPRESSION, PARAMETERS};

  public MethodCallExpressionRule()
  {
    super(METHOD_CALL_EXPRESSION, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION || types == QNAME_PRODUCTION)
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

package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.INSTANCIATION_EXPRESSION;
import static compiler.language.parser.ParseType.METHOD_CALL_EXPRESSION;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class StatementExpressionRule extends Rule
{

  private static final Object[] METHOD_CALL_PRODUCTION = new Object[] {METHOD_CALL_EXPRESSION};
  private static final Object[] INSTANCIATION_PRODUCTION = new Object[] {INSTANCIATION_EXPRESSION};

  public StatementExpressionRule()
  {
    super(STATEMENT_EXPRESSION, METHOD_CALL_PRODUCTION, INSTANCIATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == METHOD_CALL_PRODUCTION || types == INSTANCIATION_PRODUCTION)
    {
      // return the already created StatementExpression
      return args[0];
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.INSTANCIATION_EXPRESSION;
import static compiler.language.parser.ParseType.METHOD_CALL_EXPRESSION;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;

import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class StatementExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> METHOD_CALL_PRODUCTION = new Production<ParseType>(METHOD_CALL_EXPRESSION);
  private static final Production<ParseType> INSTANCIATION_PRODUCTION = new Production<ParseType>(INSTANCIATION_EXPRESSION);

  @SuppressWarnings("unchecked")
  public StatementExpressionRule()
  {
    super(STATEMENT_EXPRESSION, METHOD_CALL_PRODUCTION, INSTANCIATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (METHOD_CALL_PRODUCTION.equals(production) || INSTANCIATION_PRODUCTION.equals(production))
    {
      // return the already created StatementExpressionAST
      return args[0];
    }
    throw badTypeList();
  }

}

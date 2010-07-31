package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.PARAMETER_LIST;
import static compiler.language.parser.ParseType.RPAREN;

import compiler.language.ast.Parameter;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ParametersRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {LPAREN, RPAREN};
  private static final Object[] PRODUCTION = new Object[] {LPAREN, PARAMETER_LIST, RPAREN};

  public ParametersRule()
  {
    super(PARAMETERS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new Parameter[0];
    }
    if (types == PRODUCTION)
    {
      // the list of parameters has already been built for us by the PARAMETER_LIST rule, so return it
      return args[1];
    }
    throw badTypeList();
  }

}

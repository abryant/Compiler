package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER;
import static compiler.language.parser.ParseType.PRIMARY_NOT_QNAME;
import static compiler.language.parser.ParseType.PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME;

import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PrimaryNotQNameRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME};
  private static final Object[] ARRAY_INSTANCIATION_PRODUCTION = new Object[] {ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER};

  public PrimaryNotQNameRule()
  {
    super(PRIMARY_NOT_QNAME, PRODUCTION, ARRAY_INSTANCIATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION || types == ARRAY_INSTANCIATION_PRODUCTION)
    {
      // return the existing Expression
      return args[0];
    }
    throw badTypeList();
  }

}

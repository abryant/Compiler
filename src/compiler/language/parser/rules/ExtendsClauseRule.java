package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.REFERENCE_TYPE;

import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ExtendsClauseRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {EXTENDS_KEYWORD, REFERENCE_TYPE};

  public ExtendsClauseRule()
  {
    super(EXTENDS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return null;
    }
    if (types == PRODUCTION)
    {
      return args[1];
    }
    throw badTypeList();
  }

}

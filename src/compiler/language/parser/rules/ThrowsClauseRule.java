package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.THROWS_KEYWORD;
import static compiler.language.parser.ParseType.THROWS_LIST;

import compiler.language.ast.type.PointerType;
import compiler.parser.Rule;

/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThrowsClauseRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {THROWS_KEYWORD, THROWS_LIST};

  public ThrowsClauseRule()
  {
    super(THROWS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new PointerType[0];
    }
    if (types == PRODUCTION)
    {
      // just return the result of the THROWS_LIST argument, as it has already built the list we need here
      return args[1];
    }
    throw badTypeList();
  }

}

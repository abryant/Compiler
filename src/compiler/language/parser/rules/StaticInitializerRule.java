package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.STATIC_INITIALIZER;
import static compiler.language.parser.ParseType.STATIC_KEYWORD;

import compiler.language.ast.Block;
import compiler.language.ast.StaticInitializer;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StaticInitializerRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {STATIC_KEYWORD, BLOCK};

  public StaticInitializerRule()
  {
    super(STATIC_INITIALIZER, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new StaticInitializer((Block) args[1]);
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.CHARACTER_KEYWORD;
import static compiler.language.parser.ParseType.CHARACTER_TYPE;

import compiler.language.ast.CharacterType;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class CharacterTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {CHARACTER_KEYWORD};

  public CharacterTypeRule()
  {
    super(CHARACTER_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new CharacterType();
    }
    throw badTypeList();
  }

}

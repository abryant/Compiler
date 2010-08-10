package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.CHARACTER_KEYWORD;
import static compiler.language.parser.ParseType.CHARACTER_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.CharacterType;
import compiler.parser.ParseException;
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
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      return new CharacterType((ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}

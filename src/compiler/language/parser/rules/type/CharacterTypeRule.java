package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.CHARACTER_KEYWORD;
import static compiler.language.parser.ParseType.CHARACTER_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.CharacterType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class CharacterTypeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(CHARACTER_KEYWORD);

  public CharacterTypeRule()
  {
    super(CHARACTER_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new CharacterType((ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}

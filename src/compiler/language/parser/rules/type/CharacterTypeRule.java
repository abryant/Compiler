package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.CHARACTER_KEYWORD;
import static compiler.language.parser.ParseType.CHARACTER_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.CharacterTypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class CharacterTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(CHARACTER_KEYWORD);

  @SuppressWarnings("unchecked")
  public CharacterTypeRule()
  {
    super(CHARACTER_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new CharacterTypeAST((ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}

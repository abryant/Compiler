package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.MODIFIER;
import static compiler.language.parser.ParseType.MODIFIER_NOT_SYNCHRONIZED;
import static compiler.language.parser.ParseType.SYNCHRONIZED_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.ModifierTypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ModifierRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(MODIFIER_NOT_SYNCHRONIZED);
  private static final Production<ParseType> SYNCHRONIZED_PRODUCTION = new Production<ParseType>(SYNCHRONIZED_KEYWORD);

  @SuppressWarnings("unchecked")
  public ModifierRule()
  {
    super(MODIFIER, PRODUCTION, SYNCHRONIZED_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return args[0];
    }
    if (SYNCHRONIZED_PRODUCTION.equals(production))
    {
      return new ModifierAST(ModifierTypeAST.SYNCHRONIZED, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}

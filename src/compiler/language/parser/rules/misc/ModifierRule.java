package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.MODIFIER;
import static compiler.language.parser.ParseType.MODIFIER_NOT_SYNCHRONIZED;
import static compiler.language.parser.ParseType.SYNCHRONIZED_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Modifier;
import compiler.language.ast.misc.ModifierType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifierRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(MODIFIER_NOT_SYNCHRONIZED);
  private static final Production SYNCHRONIZED_PRODUCTION = new Production(SYNCHRONIZED_KEYWORD);

  public ModifierRule()
  {
    super(MODIFIER, PRODUCTION, SYNCHRONIZED_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return args[0];
    }
    if (SYNCHRONIZED_PRODUCTION.equals(production))
    {
      return new Modifier(ModifierType.SYNCHRONIZED, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}

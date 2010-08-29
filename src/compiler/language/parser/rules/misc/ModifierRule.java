package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.MODIFIER;
import static compiler.language.parser.ParseType.MODIFIER_NOT_SYNCHRONIZED;
import static compiler.language.parser.ParseType.SYNCHRONIZED_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Modifier;
import compiler.language.ast.misc.ModifierType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifierRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {MODIFIER_NOT_SYNCHRONIZED};
  private static final Object[] SYNCHRONIZED_PRODUCTION = new Object[] {SYNCHRONIZED_KEYWORD};

  public ModifierRule()
  {
    super(MODIFIER, PRODUCTION, SYNCHRONIZED_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      return args[0];
    }
    if (types == SYNCHRONIZED_PRODUCTION)
    {
      return new Modifier(ModifierType.SYNCHRONIZED, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.MODIFIER;
import static compiler.language.parser.ParseType.MODIFIERS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Modifier;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifiersRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION = new Production(MODIFIER);
  private static final Production CONTINUATION_PRODUCTION = new Production(MODIFIERS, MODIFIER);

  public ModifiersRule()
  {
    super(MODIFIERS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      Modifier modifier = (Modifier) args[0];
      return new ParseList<Modifier>(modifier, modifier.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Modifier> list = (ParseList<Modifier>) args[0];
      Modifier modifier = (Modifier) args[1];
      list.addLast(modifier, ParseInfo.combine(list.getParseInfo(), modifier.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

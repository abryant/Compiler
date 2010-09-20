package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.MODIFIERS_NOT_SYNCHRONIZED;
import static compiler.language.parser.ParseType.MODIFIER_NOT_SYNCHRONIZED;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Modifier;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifiersNotSynchronizedRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION        = new Production(MODIFIER_NOT_SYNCHRONIZED);
  private static final Production CONTINUATION_PRODUCTION = new Production(MODIFIERS_NOT_SYNCHRONIZED, MODIFIER_NOT_SYNCHRONIZED);

  public ModifiersNotSynchronizedRule()
  {
    super(MODIFIERS_NOT_SYNCHRONIZED, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
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

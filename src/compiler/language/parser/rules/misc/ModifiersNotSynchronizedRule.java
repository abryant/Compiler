package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.MODIFIERS_NOT_SYNCHRONIZED;
import static compiler.language.parser.ParseType.MODIFIER_NOT_SYNCHRONIZED;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Modifier;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifiersNotSynchronizedRule extends Rule
{

  private static final Object[] START_PRODUCTION        = new Object[] {MODIFIER_NOT_SYNCHRONIZED};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {MODIFIERS_NOT_SYNCHRONIZED, MODIFIER_NOT_SYNCHRONIZED};

  public ModifiersNotSynchronizedRule()
  {
    super(MODIFIERS_NOT_SYNCHRONIZED, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      Modifier modifier = (Modifier) args[0];
      return new ParseList<Modifier>(modifier, modifier.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
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

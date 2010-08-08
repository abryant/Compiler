package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.MODIFIER;
import static compiler.language.parser.ParseType.MODIFIERS;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.Modifier;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifiersRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {MODIFIER};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {MODIFIERS, MODIFIER};

  public ModifiersRule()
  {
    super(MODIFIERS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
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

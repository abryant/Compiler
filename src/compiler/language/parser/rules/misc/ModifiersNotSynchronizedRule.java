package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.MODIFIERS_NOT_SYNCHRONIZED;
import static compiler.language.parser.ParseType.MODIFIER_NOT_SYNCHRONIZED;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ModifiersNotSynchronizedRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION        = new Production<ParseType>(MODIFIER_NOT_SYNCHRONIZED);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(MODIFIERS_NOT_SYNCHRONIZED, MODIFIER_NOT_SYNCHRONIZED);

  @SuppressWarnings("unchecked")
  public ModifiersNotSynchronizedRule()
  {
    super(MODIFIERS_NOT_SYNCHRONIZED, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      ModifierAST modifier = (ModifierAST) args[0];
      return new ParseList<ModifierAST>(modifier, modifier.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ModifierAST> list = (ParseList<ModifierAST>) args[0];
      ModifierAST modifier = (ModifierAST) args[1];
      list.addLast(modifier, ParseInfo.combine(list.getParseInfo(), modifier.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

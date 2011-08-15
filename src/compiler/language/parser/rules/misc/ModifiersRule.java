package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.MODIFIER;
import static compiler.language.parser.ParseType.MODIFIERS;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ModifiersRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(MODIFIER);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(MODIFIERS, MODIFIER);

  @SuppressWarnings("unchecked")
  public ModifiersRule()
  {
    super(MODIFIERS, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      ModifierAST modifier = (ModifierAST) args[0];
      return new ParseList<ModifierAST>(modifier, modifier.getLexicalPhrase());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ModifierAST> list = (ParseList<ModifierAST>) args[0];
      ModifierAST modifier = (ModifierAST) args[1];
      list.addLast(modifier, LexicalPhrase.combine(list.getLexicalPhrase(), modifier.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.MODIFIER;
import static compiler.language.parser.ParseType.MODIFIERS;

import compiler.language.ast.Modifier;
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
      return new Modifier[] {(Modifier) args[0]};
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      Modifier[] oldModifiers = (Modifier[]) args[0];
      Modifier[] newModifiers = new Modifier[oldModifiers.length + 1];
      System.arraycopy(oldModifiers, 0, newModifiers, 0, oldModifiers.length);
      newModifiers[oldModifiers.length] = (Modifier) args[1];
      return newModifiers;
    }
    throw badTypeList();
  }

}

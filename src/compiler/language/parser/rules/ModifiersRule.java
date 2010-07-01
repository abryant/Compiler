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

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {MODIFIERS, MODIFIER};

  public ModifiersRule()
  {
    super(MODIFIERS, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new Modifier[0];
    }
    if (types == PRODUCTION)
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

package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ACCESS_SPECIFIER;
import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.CONSTRUCTOR;
import static compiler.language.parser.ParseType.MODIFIERS;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;

import compiler.language.ast.AccessSpecifier;
import compiler.language.ast.ArgumentList;
import compiler.language.ast.Block;
import compiler.language.ast.Constructor;
import compiler.language.ast.Modifier;
import compiler.language.ast.Name;
import compiler.language.ast.PointerType;
import compiler.parser.Rule;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ConstructorRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {ACCESS_SPECIFIER, MODIFIERS, NAME, ARGUMENTS, THROWS_CLAUSE, BLOCK};

  public ConstructorRule()
  {
    super(CONSTRUCTOR, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new Constructor((AccessSpecifier) args[0], (Modifier[]) args[1], (Name) args[2], (ArgumentList) args[3], (PointerType[]) args[4], (Block) args[5]);
    }
    throw badTypeList();
  }

}

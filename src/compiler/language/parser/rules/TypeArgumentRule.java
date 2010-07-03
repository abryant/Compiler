package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT;

import compiler.language.ast.Name;
import compiler.language.ast.PointerType;
import compiler.language.ast.TypeArgument;
import compiler.parser.Rule;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentRule extends Rule
{

  private static final Object[] NAME_PRODUCTION = new Object[] {NAME};
  private static final Object[] EXTENDS_PRODUCTION = new Object[] {NAME, EXTENDS_KEYWORD, POINTER_TYPE};
  private static final Object[] SUPER_PRODUCTION = new Object[] {NAME, SUPER_KEYWORD, POINTER_TYPE};
  private static final Object[] EXTENDS_SUPER_PRODUCTION = new Object[] {NAME, EXTENDS_KEYWORD, POINTER_TYPE, SUPER_KEYWORD, POINTER_TYPE};
  private static final Object[] SUPER_EXTENDS_PRODUCTION = new Object[] {NAME, SUPER_KEYWORD, POINTER_TYPE, EXTENDS_KEYWORD, POINTER_TYPE};

  public TypeArgumentRule()
  {
    super(TYPE_ARGUMENT, NAME_PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == NAME_PRODUCTION)
    {
      return new TypeArgument((Name) args[0], null, null);
    }
    if (types == EXTENDS_PRODUCTION)
    {
      return new TypeArgument((Name) args[0], (PointerType) args[2], null);
    }
    if (types == SUPER_PRODUCTION)
    {
      return new TypeArgument((Name) args[0], null, (PointerType) args[2]);
    }
    if (types == EXTENDS_SUPER_PRODUCTION)
    {
      return new TypeArgument((Name) args[0], (PointerType) args[2], (PointerType) args[4]);
    }
    if (types == SUPER_EXTENDS_PRODUCTION)
    {
      return new TypeArgument((Name) args[0], (PointerType) args[4], (PointerType) args[2]);
    }
    throw badTypeList();
  }

}

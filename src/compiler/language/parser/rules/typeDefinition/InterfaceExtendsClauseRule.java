package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.INTERFACE_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.INTERFACE_LIST;

import compiler.language.ast.type.PointerType;
import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class InterfaceExtendsClauseRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {EXTENDS_KEYWORD, INTERFACE_LIST};

  public InterfaceExtendsClauseRule()
  {
    super(INTERFACE_EXTENDS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return new PointerType[0];
    }
    if (types == PRODUCTION)
    {
      // just return the result of the interface list argument, as it has already built the list we need here
      return args[1];
    }
    throw badTypeList();
  }

}

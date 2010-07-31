package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.VOID_KEYWORD;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.language.ast.type.VoidType;
import compiler.parser.Rule;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class VoidTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {VOID_KEYWORD};

  public VoidTypeRule()
  {
    super(VOID_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new VoidType();
    }
    throw badTypeList();
  }

}

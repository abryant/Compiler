package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.MEMBER;
import static compiler.language.parser.ParseType.TYPE_DEFINITION;

import compiler.parser.Rule;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberRule extends Rule
{

  private static final Object[] TYPE_DEFINITION_PRODUCTION = new Object[] {TYPE_DEFINITION};

  public MemberRule()
  {
    super(MEMBER, TYPE_DEFINITION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == TYPE_DEFINITION_PRODUCTION)
    {
      // TypeDefinition is a subclass of Member, so just return the TypeDefinition argument
      return args[0];
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.CONSTRUCTOR;
import static compiler.language.parser.ParseType.FIELD;
import static compiler.language.parser.ParseType.MEMBER;
import static compiler.language.parser.ParseType.METHOD;
import static compiler.language.parser.ParseType.PROPERTY;
import static compiler.language.parser.ParseType.STATIC_INITIALIZER;
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

  private static final Object[] FIELD_PRODUCTION = new Object[] {FIELD};
  private static final Object[] PROPERTY_PRODUCTION = new Object[] {PROPERTY};
  private static final Object[] STATIC_INITIALIZER_PRODUCTION = new Object[] {STATIC_INITIALIZER};
  private static final Object[] CONSTRUCTOR_PRODUCTION = new Object[] {CONSTRUCTOR};
  private static final Object[] METHOD_PRODUCTION = new Object[] {METHOD};
  private static final Object[] TYPE_DEFINITION_PRODUCTION = new Object[] {TYPE_DEFINITION};


  public MemberRule()
  {
    super(MEMBER, FIELD_PRODUCTION, PROPERTY_PRODUCTION, STATIC_INITIALIZER_PRODUCTION, CONSTRUCTOR_PRODUCTION, METHOD_PRODUCTION, TYPE_DEFINITION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == FIELD_PRODUCTION || types == PROPERTY_PRODUCTION || types == STATIC_INITIALIZER_PRODUCTION ||
        types == CONSTRUCTOR_PRODUCTION || types == METHOD_PRODUCTION || types == TYPE_DEFINITION_PRODUCTION)
    {
      // All members that can be reduced to a Member are actually subclasses of Member, so just return the argument in any case
      return args[0];
    }
    throw badTypeList();
  }

}

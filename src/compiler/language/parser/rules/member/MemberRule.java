package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.CONSTRUCTOR;
import static compiler.language.parser.ParseType.FIELD;
import static compiler.language.parser.ParseType.MEMBER;
import static compiler.language.parser.ParseType.METHOD;
import static compiler.language.parser.ParseType.PROPERTY;
import static compiler.language.parser.ParseType.STATIC_INITIALIZER;
import static compiler.language.parser.ParseType.TYPE_DEFINITION;

import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class MemberRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production FIELD_PRODUCTION = new Production(FIELD);
  private static final Production PROPERTY_PRODUCTION = new Production(PROPERTY);
  private static final Production STATIC_INITIALIZER_PRODUCTION = new Production(STATIC_INITIALIZER);
  private static final Production CONSTRUCTOR_PRODUCTION = new Production(CONSTRUCTOR);
  private static final Production METHOD_PRODUCTION = new Production(METHOD);
  private static final Production TYPE_DEFINITION_PRODUCTION = new Production(TYPE_DEFINITION);


  public MemberRule()
  {
    super(MEMBER, FIELD_PRODUCTION, PROPERTY_PRODUCTION, STATIC_INITIALIZER_PRODUCTION, CONSTRUCTOR_PRODUCTION, METHOD_PRODUCTION, TYPE_DEFINITION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (FIELD_PRODUCTION.equals(production) || PROPERTY_PRODUCTION.equals(production) || STATIC_INITIALIZER_PRODUCTION.equals(production) ||
        CONSTRUCTOR_PRODUCTION.equals(production) || METHOD_PRODUCTION.equals(production) || TYPE_DEFINITION_PRODUCTION.equals(production))
    {
      // All members that can be reduced to a Member are actually subclasses of Member, so just return the argument in any case
      return args[0];
    }
    throw badTypeList();
  }

}

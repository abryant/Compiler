package compiler.language.parser.rules.member;

import static compiler.language.parser.ParseType.CONSTRUCTOR;
import static compiler.language.parser.ParseType.FIELD;
import static compiler.language.parser.ParseType.MEMBER;
import static compiler.language.parser.ParseType.METHOD;
import static compiler.language.parser.ParseType.PROPERTY;
import static compiler.language.parser.ParseType.STATIC_INITIALIZER;
import static compiler.language.parser.ParseType.TYPE_DEFINITION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.parser.ParseType;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class MemberRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> FIELD_PRODUCTION = new Production<ParseType>(FIELD);
  private static final Production<ParseType> PROPERTY_PRODUCTION = new Production<ParseType>(PROPERTY);
  private static final Production<ParseType> STATIC_INITIALIZER_PRODUCTION = new Production<ParseType>(STATIC_INITIALIZER);
  private static final Production<ParseType> CONSTRUCTOR_PRODUCTION = new Production<ParseType>(CONSTRUCTOR);
  private static final Production<ParseType> METHOD_PRODUCTION = new Production<ParseType>(METHOD);
  private static final Production<ParseType> TYPE_DEFINITION_PRODUCTION = new Production<ParseType>(TYPE_DEFINITION);


  @SuppressWarnings("unchecked")
  public MemberRule()
  {
    super(MEMBER, FIELD_PRODUCTION, PROPERTY_PRODUCTION, STATIC_INITIALIZER_PRODUCTION, CONSTRUCTOR_PRODUCTION, METHOD_PRODUCTION, TYPE_DEFINITION_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (FIELD_PRODUCTION.equals(production) || PROPERTY_PRODUCTION.equals(production) || STATIC_INITIALIZER_PRODUCTION.equals(production) ||
        CONSTRUCTOR_PRODUCTION.equals(production) || METHOD_PRODUCTION.equals(production) || TYPE_DEFINITION_PRODUCTION.equals(production))
    {
      // All members that can be reduced to a MemberAST are actually subclasses of MemberAST, so just return the argument in any case
      return args[0];
    }
    throw badTypeList();
  }

}

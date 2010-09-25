package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.BOOLEAN_TYPE;
import static compiler.language.parser.ParseType.CHARACTER_TYPE;
import static compiler.language.parser.ParseType.FLOATING_TYPE;
import static compiler.language.parser.ParseType.INTEGER_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;

import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PrimitiveTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> BOOLEAN_PRODUCTION = new Production<ParseType>(BOOLEAN_TYPE);
  private static final Production<ParseType> INTEGER_PRODUCTION = new Production<ParseType>(INTEGER_TYPE);
  private static final Production<ParseType> CHARACTER_PRODUCTION = new Production<ParseType>(CHARACTER_TYPE);
  private static final Production<ParseType> FLOATING_PRODUCTION = new Production<ParseType>(FLOATING_TYPE);

  @SuppressWarnings("unchecked")
  public PrimitiveTypeRule()
  {
    super(PRIMITIVE_TYPE, BOOLEAN_PRODUCTION, INTEGER_PRODUCTION, CHARACTER_PRODUCTION, FLOATING_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (BOOLEAN_PRODUCTION.equals(production) || INTEGER_PRODUCTION.equals(production) || CHARACTER_PRODUCTION.equals(production) || FLOATING_PRODUCTION.equals(production))
    {
      // All primitive types that can be reduced to a PrimitiveTypeAST are actually subclasses of PrimitiveTypeAST, so just return the argument in any case
      return args[0];
    }
    throw badTypeList();
  }

}

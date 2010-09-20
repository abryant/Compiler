package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.BOOLEAN_TYPE;
import static compiler.language.parser.ParseType.CHARACTER_TYPE;
import static compiler.language.parser.ParseType.FLOATING_TYPE;
import static compiler.language.parser.ParseType.INTEGER_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;

import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class PrimitiveTypeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production BOOLEAN_PRODUCTION = new Production(BOOLEAN_TYPE);
  private static final Production INTEGER_PRODUCTION = new Production(INTEGER_TYPE);
  private static final Production CHARACTER_PRODUCTION = new Production(CHARACTER_TYPE);
  private static final Production FLOATING_PRODUCTION = new Production(FLOATING_TYPE);

  public PrimitiveTypeRule()
  {
    super(PRIMITIVE_TYPE, BOOLEAN_PRODUCTION, INTEGER_PRODUCTION, CHARACTER_PRODUCTION, FLOATING_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (BOOLEAN_PRODUCTION.equals(production) || INTEGER_PRODUCTION.equals(production) || CHARACTER_PRODUCTION.equals(production) || FLOATING_PRODUCTION.equals(production))
    {
      // All primitive types that can be reduced to a PrimitiveType are actually subclasses of PrimitiveType, so just return the argument in any case
      return args[0];
    }
    throw badTypeList();
  }

}

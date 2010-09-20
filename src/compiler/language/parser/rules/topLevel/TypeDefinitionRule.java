package compiler.language.parser.rules.topLevel;

import static compiler.language.parser.ParseType.CLASS_DEFINITION;
import static compiler.language.parser.ParseType.ENUM_DEFINITION;
import static compiler.language.parser.ParseType.INTERFACE_DEFINITION;
import static compiler.language.parser.ParseType.TYPE_DEFINITION;

import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeDefinitionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> CLASS_PRODUCTION = new Production<ParseType>(CLASS_DEFINITION);
  private static final Production<ParseType> INTERFACE_PRODUCTION = new Production<ParseType>(INTERFACE_DEFINITION);
  private static final Production<ParseType> ENUM_PRODUCTION = new Production<ParseType>(ENUM_DEFINITION);

  @SuppressWarnings("unchecked")
  public TypeDefinitionRule()
  {
    super(TYPE_DEFINITION, CLASS_PRODUCTION, INTERFACE_PRODUCTION, ENUM_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (CLASS_PRODUCTION.equals(production) || INTERFACE_PRODUCTION.equals(production) || ENUM_PRODUCTION.equals(production))
    {
      // ClassDefinition, InterfaceDefinition and EnumDefinition are all subclasses of TypeDefinition, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}

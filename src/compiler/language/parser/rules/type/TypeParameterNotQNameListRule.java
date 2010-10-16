package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.type.NormalTypeParameterAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeParameterNotQNameListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NORMAL_PRODUCTION = new Production<ParseType>(TYPE_NOT_QNAME_LIST);
  private static final Production<ParseType> WILDCARD_PRODUCTION = new Production<ParseType>(WILDCARD_TYPE_PARAMETER);

  @SuppressWarnings("unchecked")
  public TypeParameterNotQNameListRule()
  {
    super(TYPE_PARAMETER_NOT_QNAME_LIST, NORMAL_PRODUCTION, WILDCARD_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (NORMAL_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      return new NormalTypeParameterAST(type, type.getParseInfo());
    }
    if (WILDCARD_PRODUCTION.equals(production))
    {
      // a wildcard type parameter is actually a subclass of TypeParameterAST, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}

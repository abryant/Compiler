package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER;

import compiler.language.ast.type.NormalTypeParameter;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterNotQNameListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production NORMAL_PRODUCTION = new Production(TYPE_NOT_QNAME_LIST);
  private static final Production WILDCARD_PRODUCTION = new Production(WILDCARD_TYPE_PARAMETER);

  public TypeParameterNotQNameListRule()
  {
    super(TYPE_PARAMETER_NOT_QNAME_LIST, NORMAL_PRODUCTION, WILDCARD_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (NORMAL_PRODUCTION.equals(production))
    {
      Type type = (Type) args[0];
      return new NormalTypeParameter(type, type.getParseInfo());
    }
    if (WILDCARD_PRODUCTION.equals(production))
    {
      // a wildcard type parameter is actually a subclass of TypeParameter, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}

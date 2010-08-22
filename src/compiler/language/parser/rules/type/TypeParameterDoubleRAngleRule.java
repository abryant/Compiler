package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.type.NormalTypeParameter;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterDoubleRAngleRule extends Rule
{

  private static final Object[] TYPE_PRODUCTION = new Object[] {TYPE_DOUBLE_RANGLE};
  private static final Object[] WILDCARD_PRODUCTION = new Object[] {WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE};

  public TypeParameterDoubleRAngleRule()
  {
    super(TYPE_PARAMETER_DOUBLE_RANGLE, TYPE_PRODUCTION, WILDCARD_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == TYPE_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<Type>> oldContainer = (ParseContainer<ParseContainer<Type>>) args[0];
      Type type = oldContainer.getItem().getItem();
      TypeParameter parameter = new NormalTypeParameter(type, type.getParseInfo());
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(parameter, oldContainer.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<TypeParameter>>(firstContainer, oldContainer.getParseInfo());
    }
    if (types == WILDCARD_PRODUCTION)
    {
      // the rule for WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE has already created
      // a ParseContainer<ParseContainer<TypeParameter>>, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

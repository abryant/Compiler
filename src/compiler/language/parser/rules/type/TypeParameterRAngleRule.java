package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_PARAMETER_RANGLE;
import static compiler.language.parser.ParseType.TYPE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.type.NormalTypeParameter;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeParameter;
import compiler.language.ast.type.WildcardTypeParameter;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterRAngleRule extends Rule
{

  private static final Object[] NORMAL_PRODUCTION = new Object[] {TYPE_RANGLE};
  private static final Object[] WILDCARD_PRODUCTION = new Object[] {WILDCARD_TYPE_PARAMETER_RANGLE};

  public TypeParameterRAngleRule()
  {
    super(TYPE_PARAMETER_RANGLE, NORMAL_PRODUCTION, WILDCARD_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == NORMAL_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<Type> container = (ParseContainer<Type>) args[0];
      TypeParameter typeParameter = new NormalTypeParameter(container.getItem(), container.getItem().getParseInfo());
      return new ParseContainer<TypeParameter>(typeParameter, container.getParseInfo());
    }
    if (types == WILDCARD_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<WildcardTypeParameter> container = (ParseContainer<WildcardTypeParameter>) args[0];
      return new ParseContainer<TypeParameter>(container.getItem(), container.getParseInfo());
    }
    throw badTypeList();
  }

}

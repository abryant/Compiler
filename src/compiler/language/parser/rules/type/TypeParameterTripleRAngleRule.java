package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_PARAMETER_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_TRIPLE_RANGLE;

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
public class TypeParameterTripleRAngleRule extends Rule
{

  private static final Object[] TYPE_PRODUCTION = new Object[] {TYPE_TRIPLE_RANGLE};
  private static final Object[] WILDCARD_PRODUCTION = new Object[] {WILDCARD_TYPE_PARAMETER_TRIPLE_RANGLE};

  public TypeParameterTripleRAngleRule()
  {
    super(TYPE_PARAMETER_TRIPLE_RANGLE, TYPE_PRODUCTION, WILDCARD_PRODUCTION);
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
      // repackage the Type into a ParseContainer<ParseContainer<ParseContainer<TypeParameter>>>
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<Type>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<Type>>>) args[0];
      Type type = oldContainer.getItem().getItem().getItem();
      TypeParameter parameter = new NormalTypeParameter(type, type.getParseInfo());
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(parameter, oldContainer.getItem().getItem().getParseInfo());
      ParseContainer<ParseContainer<TypeParameter>> secondContainer = new ParseContainer<ParseContainer<TypeParameter>>(firstContainer, oldContainer.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<ParseContainer<TypeParameter>>>(secondContainer, oldContainer.getParseInfo());
    }
    if (types == WILDCARD_PRODUCTION)
    {
      // the ParseContainer<ParseContainer<ParseContainer<TypeParameter>>> has already been built be the wildcard rule, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

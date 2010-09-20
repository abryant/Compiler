package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_PARAMETER_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_TRIPLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.type.NormalTypeParameter;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeParameter;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterTripleRAngleRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production TYPE_PRODUCTION = new Production(TYPE_TRIPLE_RANGLE);
  private static final Production WILDCARD_PRODUCTION = new Production(WILDCARD_TYPE_PARAMETER_TRIPLE_RANGLE);

  public TypeParameterTripleRAngleRule()
  {
    super(TYPE_PARAMETER_TRIPLE_RANGLE, TYPE_PRODUCTION, WILDCARD_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (TYPE_PRODUCTION.equals(production))
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
    if (WILDCARD_PRODUCTION.equals(production))
    {
      // the ParseContainer<ParseContainer<ParseContainer<TypeParameter>>> has already been built be the wildcard rule, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

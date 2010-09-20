package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_PARAMETER_RANGLE;
import static compiler.language.parser.ParseType.TYPE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.type.NormalTypeParameter;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeParameter;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeParameterRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> NORMAL_PRODUCTION = new Production<ParseType>(TYPE_RANGLE);
  private static final Production<ParseType> WILDCARD_PRODUCTION = new Production<ParseType>(WILDCARD_TYPE_PARAMETER_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeParameterRAngleRule()
  {
    super(TYPE_PARAMETER_RANGLE, NORMAL_PRODUCTION, WILDCARD_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (NORMAL_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<Type> container = (ParseContainer<Type>) args[0];
      TypeParameter typeParameter = new NormalTypeParameter(container.getItem(), container.getItem().getParseInfo());
      return new ParseContainer<TypeParameter>(typeParameter, container.getParseInfo());
    }
    if (WILDCARD_PRODUCTION.equals(production))
    {
      // the rule for WILDCARD_TYPE_PARAMETER_RANGLE has already created
      // a ParseContainer<TypeParameter>, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

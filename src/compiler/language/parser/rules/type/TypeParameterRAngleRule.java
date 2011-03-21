package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_PARAMETER_RANGLE;
import static compiler.language.parser.ParseType.TYPE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.type.NormalTypeParameterAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (NORMAL_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<TypeAST> container = (ParseContainer<TypeAST>) args[0];
      TypeParameterAST typeParameter = new NormalTypeParameterAST(container.getItem(), container.getItem().getParseInfo());
      return new ParseContainer<TypeParameterAST>(typeParameter, container.getParseInfo());
    }
    if (WILDCARD_PRODUCTION.equals(production))
    {
      // the rule for WILDCARD_TYPE_PARAMETER_RANGLE has already created
      // a ParseContainer<TypeParameterAST>, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

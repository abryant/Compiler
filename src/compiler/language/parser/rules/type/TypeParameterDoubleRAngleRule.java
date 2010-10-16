package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.type.NormalTypeParameterAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseType;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeParameterDoubleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_PRODUCTION = new Production<ParseType>(TYPE_DOUBLE_RANGLE);
  private static final Production<ParseType> WILDCARD_PRODUCTION = new Production<ParseType>(WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeParameterDoubleRAngleRule()
  {
    super(TYPE_PARAMETER_DOUBLE_RANGLE, TYPE_PRODUCTION, WILDCARD_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TYPE_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<TypeAST>> oldContainer = (ParseContainer<ParseContainer<TypeAST>>) args[0];
      TypeAST type = oldContainer.getItem().getItem();
      TypeParameterAST parameter = new NormalTypeParameterAST(type, type.getParseInfo());
      ParseContainer<TypeParameterAST> firstContainer = new ParseContainer<TypeParameterAST>(parameter, oldContainer.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<TypeParameterAST>>(firstContainer, oldContainer.getParseInfo());
    }
    if (WILDCARD_PRODUCTION.equals(production))
    {
      // the rule for WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE has already created
      // a ParseContainer<ParseContainer<TypeParameterAST>>, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

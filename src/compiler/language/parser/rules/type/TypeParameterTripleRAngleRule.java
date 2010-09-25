package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_PARAMETER_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_TRIPLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.type.NormalTypeParameterAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeParameterTripleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_PRODUCTION = new Production<ParseType>(TYPE_TRIPLE_RANGLE);
  private static final Production<ParseType> WILDCARD_PRODUCTION = new Production<ParseType>(WILDCARD_TYPE_PARAMETER_TRIPLE_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeParameterTripleRAngleRule()
  {
    super(TYPE_PARAMETER_TRIPLE_RANGLE, TYPE_PRODUCTION, WILDCARD_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TYPE_PRODUCTION.equals(production))
    {
      // repackage the TypeAST into a ParseContainer<ParseContainer<ParseContainer<TypeParameterAST>>>
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<TypeAST>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<TypeAST>>>) args[0];
      TypeAST type = oldContainer.getItem().getItem().getItem();
      TypeParameterAST parameter = new NormalTypeParameterAST(type, type.getParseInfo());
      ParseContainer<TypeParameterAST> firstContainer = new ParseContainer<TypeParameterAST>(parameter, oldContainer.getItem().getItem().getParseInfo());
      ParseContainer<ParseContainer<TypeParameterAST>> secondContainer = new ParseContainer<ParseContainer<TypeParameterAST>>(firstContainer, oldContainer.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<ParseContainer<TypeParameterAST>>>(secondContainer, oldContainer.getParseInfo());
    }
    if (WILDCARD_PRODUCTION.equals(production))
    {
      // the ParseContainer<ParseContainer<ParseContainer<TypeParameterAST>>> has already been built be the wildcard rule, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

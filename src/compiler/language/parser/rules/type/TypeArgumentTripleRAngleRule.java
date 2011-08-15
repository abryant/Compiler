package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_ARGUMENT_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_ARGUMENT_TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.type.NormalTypeArgumentAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeArgumentTripleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_PRODUCTION = new Production<ParseType>(TYPE_TRIPLE_RANGLE);
  private static final Production<ParseType> WILDCARD_PRODUCTION = new Production<ParseType>(WILDCARD_TYPE_ARGUMENT_TRIPLE_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeArgumentTripleRAngleRule()
  {
    super(TYPE_ARGUMENT_TRIPLE_RANGLE, TYPE_PRODUCTION, WILDCARD_PRODUCTION);
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
      // repackage the TypeAST into a ParseContainer<ParseContainer<ParseContainer<TypeArgumentAST>>>
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<TypeAST>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<TypeAST>>>) args[0];
      TypeAST type = oldContainer.getItem().getItem().getItem();
      TypeArgumentAST argument = new NormalTypeArgumentAST(type, type.getLexicalPhrase());
      ParseContainer<TypeArgumentAST> firstContainer = new ParseContainer<TypeArgumentAST>(argument, oldContainer.getItem().getItem().getLexicalPhrase());
      ParseContainer<ParseContainer<TypeArgumentAST>> secondContainer = new ParseContainer<ParseContainer<TypeArgumentAST>>(firstContainer, oldContainer.getItem().getLexicalPhrase());
      return new ParseContainer<ParseContainer<ParseContainer<TypeArgumentAST>>>(secondContainer, oldContainer.getLexicalPhrase());
    }
    if (WILDCARD_PRODUCTION.equals(production))
    {
      // the ParseContainer<ParseContainer<ParseContainer<TypeArgumentAST>>> has already been built be the wildcard rule, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

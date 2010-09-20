package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST_RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentListRAngleRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production TYPE_ARGUMENT_PRODUCTION = new Production(TYPE_ARGUMENT_RANGLE);
  private static final Production LIST_END_PRODUCTION = new Production(TYPE_ARGUMENT_LIST, COMMA, TYPE_ARGUMENT_RANGLE);

  public TypeArgumentListRAngleRule()
  {
    super(TYPE_ARGUMENT_LIST_RANGLE, TYPE_ARGUMENT_PRODUCTION, LIST_END_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (TYPE_ARGUMENT_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<TypeArgument> typeArgument = (ParseContainer<TypeArgument>) args[0];
      ParseList<TypeArgument> list = new ParseList<TypeArgument>(typeArgument.getItem(), typeArgument.getItem().getParseInfo());
      return new ParseContainer<ParseList<TypeArgument>>(list, typeArgument.getParseInfo());
    }
    if (LIST_END_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> list = (ParseList<TypeArgument>) args[0];
      ParseInfo listParseInfo = list.getParseInfo();
      @SuppressWarnings("unchecked")
      ParseContainer<TypeArgument> typeArgument = (ParseContainer<TypeArgument>) args[2];
      list.addLast(typeArgument.getItem(), ParseInfo.combine(listParseInfo, (ParseInfo) args[1], typeArgument.getItem().getParseInfo()));
      return new ParseContainer<ParseList<TypeArgument>>(list, ParseInfo.combine(listParseInfo, (ParseInfo) args[1], typeArgument.getParseInfo()));
    }
    throw badTypeList();
  }

}

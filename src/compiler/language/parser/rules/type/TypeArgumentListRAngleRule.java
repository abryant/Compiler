package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST_RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeArgument;
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
public final class TypeArgumentListRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_ARGUMENT_PRODUCTION = new Production<ParseType>(TYPE_ARGUMENT_RANGLE);
  private static final Production<ParseType> LIST_END_PRODUCTION = new Production<ParseType>(TYPE_ARGUMENT_LIST, COMMA, TYPE_ARGUMENT_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeArgumentListRAngleRule()
  {
    super(TYPE_ARGUMENT_LIST_RANGLE, TYPE_ARGUMENT_PRODUCTION, LIST_END_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
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

package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST_RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentListRAngleRule extends Rule
{

  private static final Object[] TYPE_ARGUMENT_PRODUCTION = new Object[] {TYPE_ARGUMENT_RANGLE};
  private static final Object[] LIST_END_PRODUCTION = new Object[] {TYPE_ARGUMENT_LIST, COMMA, TYPE_ARGUMENT_RANGLE};

  public TypeArgumentListRAngleRule()
  {
    super(TYPE_ARGUMENT_LIST_RANGLE, TYPE_ARGUMENT_PRODUCTION, LIST_END_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == TYPE_ARGUMENT_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<TypeArgument> typeArgument = (ParseContainer<TypeArgument>) args[0];
      ParseList<TypeArgument> list = new ParseList<TypeArgument>(typeArgument.getItem(), typeArgument.getItem().getParseInfo());
      return new ParseContainer<ParseList<TypeArgument>>(list, typeArgument.getParseInfo());
    }
    if (types == LIST_END_PRODUCTION)
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

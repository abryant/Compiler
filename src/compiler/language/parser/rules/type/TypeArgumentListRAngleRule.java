package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST_RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TYPE_ARGUMENT_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<TypeArgumentAST> typeArgument = (ParseContainer<TypeArgumentAST>) args[0];
      ParseList<TypeArgumentAST> list = new ParseList<TypeArgumentAST>(typeArgument.getItem(), typeArgument.getItem().getParseInfo());
      return new ParseContainer<ParseList<TypeArgumentAST>>(list, typeArgument.getParseInfo());
    }
    if (LIST_END_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> list = (ParseList<TypeArgumentAST>) args[0];
      ParseInfo listParseInfo = list.getParseInfo();
      @SuppressWarnings("unchecked")
      ParseContainer<TypeArgumentAST> typeArgument = (ParseContainer<TypeArgumentAST>) args[2];
      list.addLast(typeArgument.getItem(), ParseInfo.combine(listParseInfo, (ParseInfo) args[1], typeArgument.getItem().getParseInfo()));
      return new ParseContainer<ParseList<TypeArgumentAST>>(list, ParseInfo.combine(listParseInfo, (ParseInfo) args[1], typeArgument.getParseInfo()));
    }
    throw badTypeList();
  }

}

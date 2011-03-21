package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeArgumentListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(TYPE_ARGUMENT);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(TYPE_ARGUMENT_LIST, COMMA, TYPE_ARGUMENT);

  @SuppressWarnings("unchecked")
  public TypeArgumentListRule()
  {
    super(TYPE_ARGUMENT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      TypeArgumentAST typeArgument = (TypeArgumentAST) args[0];
      return new ParseList<TypeArgumentAST>(typeArgument, typeArgument.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> list = (ParseList<TypeArgumentAST>) args[0];
      TypeArgumentAST newTypeArgument = (TypeArgumentAST) args[2];
      list.addLast(newTypeArgument, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], newTypeArgument.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

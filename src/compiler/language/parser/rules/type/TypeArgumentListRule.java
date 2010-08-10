package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {TYPE_ARGUMENT};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {TYPE_ARGUMENT_LIST, COMMA, TYPE_ARGUMENT};

  public TypeArgumentListRule()
  {
    super(TYPE_ARGUMENT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      TypeArgument typeArgument = (TypeArgument) args[0];
      return new ParseList<TypeArgument>(typeArgument, typeArgument.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> list = (ParseList<TypeArgument>) args[0];
      TypeArgument newTypeArgument = (TypeArgument) args[2];
      list.addLast(newTypeArgument, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], newTypeArgument.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

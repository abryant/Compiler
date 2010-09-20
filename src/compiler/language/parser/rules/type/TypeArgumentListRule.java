package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeArgument;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgumentListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION = new Production(TYPE_ARGUMENT);
  private static final Production CONTINUATION_PRODUCTION = new Production(TYPE_ARGUMENT_LIST, COMMA, TYPE_ARGUMENT);

  public TypeArgumentListRule()
  {
    super(TYPE_ARGUMENT_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      TypeArgument typeArgument = (TypeArgument) args[0];
      return new ParseList<TypeArgument>(typeArgument, typeArgument.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
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

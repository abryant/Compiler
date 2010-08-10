package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TupleType;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LPAREN, TYPE_LIST, RPAREN};

  public TupleTypeRule()
  {
    super(TUPLE_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Type> typeList = (ParseList<Type>) args[1];
      return new TupleType(typeList.toArray(new Type[0]), ParseInfo.combine((ParseInfo) args[0], typeList.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}

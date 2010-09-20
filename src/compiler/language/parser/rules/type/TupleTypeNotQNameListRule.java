package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_LIST_NOT_QNAME_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TupleType;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleTypeNotQNameListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(LPAREN, TYPE_LIST_NOT_QNAME_LIST, RPAREN);

  public TupleTypeNotQNameListRule()
  {
    super(TUPLE_TYPE_NOT_QNAME_LIST, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Type> list = (ParseList<Type>) args[1];
      return new TupleType(list.toArray(new Type[0]),
                           ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}

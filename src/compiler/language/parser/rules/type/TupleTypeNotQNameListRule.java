package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_LIST_NOT_QNAME_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TupleTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TupleTypeNotQNameListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(LPAREN, TYPE_LIST_NOT_QNAME_LIST, RPAREN);

  @SuppressWarnings("unchecked")
  public TupleTypeNotQNameListRule()
  {
    super(TUPLE_TYPE_NOT_QNAME_LIST, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> list = (ParseList<TypeAST>) args[1];
      return new TupleTypeAST(list.toArray(new TypeAST[0]),
                           ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}

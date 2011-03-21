package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.AMPERSAND;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeBoundListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION        = new Production<ParseType>(POINTER_TYPE);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(TYPE_BOUND_LIST, AMPERSAND, POINTER_TYPE);

  @SuppressWarnings("unchecked")
  public TypeBoundListRule()
  {
    super(TYPE_BOUND_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      return new ParseList<PointerTypeAST>(type, type.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> list = (ParseList<PointerTypeAST>) args[0];
      PointerTypeAST type = (PointerTypeAST) args[2];
      list.addLast(type, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], type.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

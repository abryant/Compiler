package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.AMPERSAND;
import static compiler.language.parser.ParseType.POINTER_TYPE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseType;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeBoundListRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION      = new Production<ParseType>(POINTER_TYPE_RANGLE);
  private static final Production<ParseType> LIST_PRODUCTION = new Production<ParseType>(TYPE_BOUND_LIST, AMPERSAND, POINTER_TYPE_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeBoundListRAngleRule()
  {
    super(TYPE_BOUND_LIST_RANGLE, PRODUCTION, LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<PointerTypeAST> type = (ParseContainer<PointerTypeAST>) args[0];
      ParseList<PointerTypeAST> list = new ParseList<PointerTypeAST>(type.getItem(), type.getItem().getParseInfo());
      return new ParseContainer<ParseList<PointerTypeAST>>(list, type.getParseInfo());
    }
    if (LIST_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> list = (ParseList<PointerTypeAST>) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<PointerTypeAST> type = (ParseContainer<PointerTypeAST>) args[2];
      list.addLast(type.getItem(), ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], type.getItem().getParseInfo()));
      return new ParseContainer<ParseList<PointerTypeAST>>(list, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], type.getParseInfo()));
    }
    throw badTypeList();
  }

}

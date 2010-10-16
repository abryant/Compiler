package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.POINTER_TYPE_RANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameElementAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, RANGLE);
  private static final Production<ParseType> POINTER_TYPE_PRODUCTION = new Production<ParseType>(POINTER_TYPE_RANGLE);
  private static final Production<ParseType> TUPLE_TYPE_PRODUCTION = new Production<ParseType>(TUPLE_TYPE_NOT_QNAME_LIST, RANGLE);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST, RANGLE);

  @SuppressWarnings("unchecked")
  public TypeRAngleRule()
  {
    super(TYPE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production) || TUPLE_TYPE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      return new ParseContainer<TypeAST>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    if (POINTER_TYPE_PRODUCTION.equals(production))
    {
      // change the ParseContainer<PointerTypeAST> for a ParseContainer<TypeAST>
      @SuppressWarnings("unchecked")
      ParseContainer<PointerTypeAST> oldContainer = (ParseContainer<PointerTypeAST>) args[0];
      return new ParseContainer<TypeAST>(oldContainer.getItem(), oldContainer.getParseInfo());
    }
    if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      TypeAST type = element.toType();
      return new ParseContainer<TypeAST>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}

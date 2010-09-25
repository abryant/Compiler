package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.POINTER_TYPE_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameElementAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 20 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeDoubleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION                   = new Production<ParseType>(TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, DOUBLE_RANGLE);
  private static final Production<ParseType> POINTER_TYPE_PRODUCTION      = new Production<ParseType>(POINTER_TYPE_DOUBLE_RANGLE);
  private static final Production<ParseType> TUPLE_TYPE_PRODUCTION        = new Production<ParseType>(TUPLE_TYPE_NOT_QNAME_LIST,            DOUBLE_RANGLE);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST,                    DOUBLE_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeDoubleRAngleRule()
  {
    super(TYPE_DOUBLE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (POINTER_TYPE_PRODUCTION.equals(production))
    {
      // repackage the ParseContainer<ParseContainer<PointerTypeAST>> to a ParseContainer<ParseContainer<TypeAST>>
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<PointerTypeAST>> oldContainer = (ParseContainer<ParseContainer<PointerTypeAST>>) args[0];
      ParseContainer<TypeAST> firstContainer = new ParseContainer<TypeAST>(oldContainer.getItem().getItem(), oldContainer.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<TypeAST>>(firstContainer, oldContainer.getParseInfo());
    }

    // the other three productions are similar, so they can share code once the TypeAST has been found
    TypeAST type;
    if (PRODUCTION.equals(production) || TUPLE_TYPE_PRODUCTION.equals(production))
    {
      type = (TypeAST) args[0];
    }
    else if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      type = element.toType();
    }
    else
    {
      throw badTypeList();
    }

    ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
    ParseInfo firstRAngleInfo = ParseUtil.splitDoubleRAngle(doubleRAngleInfo);
    ParseContainer<TypeAST> firstContainer = new ParseContainer<TypeAST>(type, ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
    return new ParseContainer<ParseContainer<TypeAST>>(firstContainer, ParseInfo.combine(type.getParseInfo(), doubleRAngleInfo));
  }

}

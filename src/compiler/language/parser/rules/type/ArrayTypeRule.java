package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.ARRAY_TYPE;
import static compiler.language.parser.ParseType.LSQUARE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RSQUARE;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.ArrayType;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ArrayTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(TYPE_NOT_QNAME_LIST, LSQUARE, RSQUARE);
  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME, LSQUARE, RSQUARE);
  private static final Production<ParseType> NESTED_QNAME_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST, LSQUARE, RSQUARE);

  @SuppressWarnings("unchecked")
  public ArrayTypeRule()
  {
    super(ARRAY_TYPE, PRODUCTION, QNAME_PRODUCTION, NESTED_QNAME_PRODUCTION);
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
      Type type = (Type) args[0];
      return new ArrayType(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    if (QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      return new ArrayType(new PointerType(qname, qname.getParseInfo()), ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    if (NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      Type baseType = element.toType();
      return new ArrayType(baseType, ParseInfo.combine(element.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeRAngleRule extends Rule
{

  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME, RANGLE};
  private static final Object[] NO_TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME, RANGLE};
  private static final Object[] TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_TRAILING_PARAMS_RANGLE};

  public PointerTypeRAngleRule()
  {
    super(POINTER_TYPE_RANGLE, QNAME_PRODUCTION, NO_TRAILING_PARAMS_PRODUCTION, TRAILING_PARAMS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      PointerType type = new PointerType(qname, qname.getParseInfo());
      return new ParseContainer<PointerType>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    if (types == NO_TRAILING_PARAMS_PRODUCTION)
    {
      PointerType type = (PointerType) args[0];
      return new ParseContainer<PointerType>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    if (types == TRAILING_PARAMS_PRODUCTION)
    {
      // POINTER_TYPE_TRAILING_PARAMS_RANGLE has already built the ParseContainer<PointerType>, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

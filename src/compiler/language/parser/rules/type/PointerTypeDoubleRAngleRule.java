package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QName;
import compiler.language.ast.type.PointerType;
import compiler.language.parser.LanguageParseException;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 20 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeDoubleRAngleRule extends Rule
{

  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME, DOUBLE_RANGLE};
  private static final Object[] NO_TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME, DOUBLE_RANGLE};
  private static final Object[] TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_TRAILING_PARAMS_DOUBLE_RANGLE};

  public PointerTypeDoubleRAngleRule()
  {
    super(POINTER_TYPE_DOUBLE_RANGLE, QNAME_PRODUCTION, NO_TRAILING_PARAMS_PRODUCTION, TRAILING_PARAMS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == TRAILING_PARAMS_PRODUCTION)
    {
      // a ParseContainer<ParseContainer<PointerType>> has already been created, so return it
      return args[0];
    }

    PointerType type;
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      type = new PointerType(qname, qname.getParseInfo());
    }
    else if (types == NO_TRAILING_PARAMS_PRODUCTION)
    {
      type = (PointerType) args[0];
    }
    else
    {
      throw badTypeList();
    }

    ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
    int line = doubleRAngleInfo.getStartLine();
    if (line != doubleRAngleInfo.getEndLine())
    {
      throw new LanguageParseException("Found a DOUBLE_RANGLE \">>\" token which does not start and finish on the same line", doubleRAngleInfo);
    }
    int startPos = doubleRAngleInfo.getStartPos();
    if (doubleRAngleInfo.getEndPos() - startPos != 2)
    {
      throw new LanguageParseException("Found a DOUBLE_RANGLE \">>\" token which is not 2 characters long", doubleRAngleInfo);
    }
    ParseInfo firstAngleInfo = new ParseInfo(line, startPos, line, startPos + 1);
    ParseContainer<PointerType> firstContainer = new ParseContainer<PointerType>(type, ParseInfo.combine(type.getParseInfo(), firstAngleInfo));
    return new ParseContainer<ParseContainer<PointerType>>(firstContainer, ParseInfo.combine(type.getParseInfo(), doubleRAngleInfo));
  }

}

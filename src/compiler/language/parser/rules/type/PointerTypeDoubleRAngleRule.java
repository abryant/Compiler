package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.LanguageParseException;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;

/*
 * Created on 20 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeDoubleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME, DOUBLE_RANGLE);
  private static final Production<ParseType> NO_TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME, DOUBLE_RANGLE);
  private static final Production<ParseType> TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS_DOUBLE_RANGLE);

  @SuppressWarnings("unchecked")
  public PointerTypeDoubleRAngleRule()
  {
    super(POINTER_TYPE_DOUBLE_RANGLE, QNAME_PRODUCTION, NO_TRAILING_PARAMS_PRODUCTION, TRAILING_PARAMS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      // a ParseContainer<ParseContainer<PointerTypeAST>> has already been created, so return it
      return args[0];
    }

    PointerTypeAST type;
    if (QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      type = new PointerTypeAST(qname, qname.getParseInfo());
    }
    else if (NO_TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      type = (PointerTypeAST) args[0];
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
    ParseContainer<PointerTypeAST> firstContainer = new ParseContainer<PointerTypeAST>(type, ParseInfo.combine(type.getParseInfo(), firstAngleInfo));
    return new ParseContainer<ParseContainer<PointerTypeAST>>(firstContainer, ParseInfo.combine(type.getParseInfo(), doubleRAngleInfo));
  }

}

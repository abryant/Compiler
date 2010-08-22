package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;

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
public class PointerTypeTripleRAngleRule extends Rule
{

  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME, TRIPLE_RANGLE};
  private static final Object[] NO_TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME, TRIPLE_RANGLE};
  private static final Object[] TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_TRAILING_PARAMS_TRIPLE_RANGLE};

  public PointerTypeTripleRAngleRule()
  {
    super(POINTER_TYPE_TRIPLE_RANGLE, QNAME_PRODUCTION, NO_TRAILING_PARAMS_PRODUCTION, TRAILING_PARAMS_PRODUCTION);
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
      // return the existing ParseContainer<ParseContainer<ParseContainer<PointerType>>>
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

    ParseInfo tripleRAngleInfo = (ParseInfo) args[1];
    int line = tripleRAngleInfo.getStartLine();
    if (line != tripleRAngleInfo.getEndLine())
    {
      throw new LanguageParseException("Found a TRIPLE_RANGLE \">>>\" token which does not start and finish on the same line", tripleRAngleInfo);
    }
    int startPos = tripleRAngleInfo.getStartPos();
    if (tripleRAngleInfo.getEndPos() - startPos != 3)
    {
      throw new LanguageParseException("Found a TRIPLE_RANGLE \">>>\" token which is not 3 characters long", tripleRAngleInfo);
    }
    ParseInfo firstAngleInfo = new ParseInfo(line, startPos, line, startPos + 1);
    ParseInfo firstTwoAnglesInfo = new ParseInfo(line, startPos, line, startPos + 2);
    ParseContainer<PointerType> firstContainer = new ParseContainer<PointerType>(type, ParseInfo.combine(type.getParseInfo(), firstAngleInfo));
    ParseContainer<ParseContainer<PointerType>> secondContainer =
      new ParseContainer<ParseContainer<PointerType>>(firstContainer, ParseInfo.combine(type.getParseInfo(), firstTwoAnglesInfo));
    return new ParseContainer<ParseContainer<ParseContainer<PointerType>>>(secondContainer, ParseInfo.combine(type.getParseInfo(), tripleRAngleInfo));
  }

}

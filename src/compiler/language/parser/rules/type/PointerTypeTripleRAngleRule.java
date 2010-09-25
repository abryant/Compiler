package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.LanguageParseException;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 20 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeTripleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME, TRIPLE_RANGLE);
  private static final Production<ParseType> NO_TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_NO_TRAILING_PARAMS_NOT_QNAME, TRIPLE_RANGLE);
  private static final Production<ParseType> TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS_TRIPLE_RANGLE);

  @SuppressWarnings("unchecked")
  public PointerTypeTripleRAngleRule()
  {
    super(POINTER_TYPE_TRIPLE_RANGLE, QNAME_PRODUCTION, NO_TRAILING_PARAMS_PRODUCTION, TRAILING_PARAMS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      // return the existing ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>>
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
    ParseContainer<PointerTypeAST> firstContainer = new ParseContainer<PointerTypeAST>(type, ParseInfo.combine(type.getParseInfo(), firstAngleInfo));
    ParseContainer<ParseContainer<PointerTypeAST>> secondContainer =
      new ParseContainer<ParseContainer<PointerTypeAST>>(firstContainer, ParseInfo.combine(type.getParseInfo(), firstTwoAnglesInfo));
    return new ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>>(secondContainer, ParseInfo.combine(type.getParseInfo(), tripleRAngleInfo));
  }

}

package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_ARGS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;

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
  private static final Production<ParseType> NO_TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_NO_TRAILING_ARGS_NOT_QNAME, DOUBLE_RANGLE);
  private static final Production<ParseType> TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_ARGS_DOUBLE_RANGLE);

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
      QName qname = (QName) args[0];
      type = new PointerTypeAST(qname, qname.getLexicalPhrase());
    }
    else if (NO_TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      type = (PointerTypeAST) args[0];
    }
    else
    {
      throw badTypeList();
    }

    LexicalPhrase doubleRAnglePhrase = (LexicalPhrase) args[1];
    LexicalPhrase firstAnglePhrase = ParseUtil.splitDoubleRAngle(doubleRAnglePhrase);
    ParseContainer<PointerTypeAST> firstContainer = new ParseContainer<PointerTypeAST>(type, LexicalPhrase.combine(type.getLexicalPhrase(), firstAnglePhrase));
    return new ParseContainer<ParseContainer<PointerTypeAST>>(firstContainer, LexicalPhrase.combine(type.getLexicalPhrase(), doubleRAnglePhrase));
  }

}

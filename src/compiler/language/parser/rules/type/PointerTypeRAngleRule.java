package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_NO_TRAILING_ARGS_NOT_QNAME;
import static compiler.language.parser.ParseType.POINTER_TYPE_RANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_ARGS_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;

/*
 * Created on 9 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME, RANGLE);
  private static final Production<ParseType> NO_TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_NO_TRAILING_ARGS_NOT_QNAME, RANGLE);
  private static final Production<ParseType> TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_ARGS_RANGLE);

  @SuppressWarnings("unchecked")
  public PointerTypeRAngleRule()
  {
    super(POINTER_TYPE_RANGLE, QNAME_PRODUCTION, NO_TRAILING_PARAMS_PRODUCTION, TRAILING_PARAMS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      PointerTypeAST type = new PointerTypeAST(qname, qname.getLexicalPhrase());
      return new ParseContainer<PointerTypeAST>(type, LexicalPhrase.combine(type.getLexicalPhrase(), (LexicalPhrase) args[1]));
    }
    if (NO_TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      return new ParseContainer<PointerTypeAST>(type, LexicalPhrase.combine(type.getLexicalPhrase(), (LexicalPhrase) args[1]));
    }
    if (TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      // POINTER_TYPE_TRAILING_PARAMS_RANGLE has already built the ParseContainer<PointerTypeAST>, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

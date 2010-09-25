package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.POINTER_TYPE_NOT_QNAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME);
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(POINTER_TYPE_NOT_QNAME);

  @SuppressWarnings("unchecked")
  public PointerTypeRule()
  {
    super(POINTER_TYPE, QNAME_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      return new PointerTypeAST(qname, qname.getParseInfo());
    }
    if (PRODUCTION.equals(production))
    {
      // POINTER_TYPE_NOT_QNAME has already built a PointerTypeAST, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

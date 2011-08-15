package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseType;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(TYPE_NOT_QNAME);
  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME);

  @SuppressWarnings("unchecked")
  public TypeRule()
  {
    super(TYPE, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      // All types are actually subclasses of TypeAST, so just return the argument
      return args[0];
    }
    if (QNAME_PRODUCTION.equals(production))
    {
      // create a new PointerTypeAST from the QNameAST
      QNameAST qname = (QNameAST) args[0];
      return new PointerTypeAST(qname, qname.getLexicalPhrase());
    }
    throw badTypeList();
  }

}

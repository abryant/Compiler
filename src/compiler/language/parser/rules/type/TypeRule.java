package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME;

import compiler.language.ast.misc.QName;
import compiler.language.ast.type.PointerType;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      // All types are actually subclasses of Type, so just return the argument
      return args[0];
    }
    if (QNAME_PRODUCTION.equals(production))
    {
      // create a new PointerType from the QName
      QName qname = (QName) args[0];
      return new PointerType(qname, qname.getParseInfo());
    }
    throw badTypeList();
  }

}

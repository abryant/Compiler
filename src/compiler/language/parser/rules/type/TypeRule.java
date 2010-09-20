package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME;

import compiler.language.ast.misc.QName;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(TYPE_NOT_QNAME);
  private static final Production QNAME_PRODUCTION = new Production(QNAME);

  public TypeRule()
  {
    super(TYPE, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
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

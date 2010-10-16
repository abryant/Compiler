package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.misc.QNameElementAST;
import compiler.language.parser.ParseType;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeNotQNameRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(TYPE_NOT_QNAME_LIST);
  private static final Production<ParseType> QNAME_LIST_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST);

  @SuppressWarnings("unchecked")
  public TypeNotQNameRule()
  {
    super(TYPE_NOT_QNAME, PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
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
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      return element.toType();
    }
    throw badTypeList();
  }

}

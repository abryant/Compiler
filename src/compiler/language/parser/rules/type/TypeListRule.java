package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.TYPE_LIST;

import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QNameElementAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_LIST_PRODUCTION = new Production<ParseType>(ParseType.TYPE_LIST_NOT_QNAME_LIST);
  private static final Production<ParseType> QNAME_LIST_PRODUCTION = new Production<ParseType>(ParseType.QNAME_LIST);

  @SuppressWarnings("unchecked")
  public TypeListRule()
  {
    super(TYPE_LIST, TYPE_LIST_PRODUCTION, QNAME_LIST_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TYPE_LIST_PRODUCTION.equals(production))
    {
      // return the existing ParseList<TypeAST>
      return args[0];
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<QNameElementAST> elements = (ParseList<QNameElementAST>) args[0];
      QNameElementAST[] elems = elements.toArray(new QNameElementAST[0]);
      TypeAST[] typeArray = new TypeAST[elems.length];
      for (int i = 0; i < elems.length; i++)
      {
        typeArray[i] = elems[i].toType();
      }
      return new ParseList<TypeAST>(typeArray, elements.getParseInfo());
    }
    throw badTypeList();
  }

}

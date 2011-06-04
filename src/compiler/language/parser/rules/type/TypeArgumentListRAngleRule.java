package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST_RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.type.NormalTypeArgumentAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeArgumentListRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_ARGUMENT_PRODUCTION       = new Production<ParseType>(TYPE_ARGUMENT_RANGLE);
  private static final Production<ParseType> TYPE_ARGUMENT_LIST_PRODUCTION  = new Production<ParseType>(TYPE_ARGUMENT_NOT_QNAME_LIST, COMMA, TYPE_ARGUMENT_LIST_RANGLE);
  private static final Production<ParseType> QNAME_LIST_PRODUCTION          = new Production<ParseType>(QNAME,                         COMMA, TYPE_ARGUMENT_LIST_RANGLE);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION   = new Production<ParseType>(NESTED_QNAME_LIST,             COMMA, TYPE_ARGUMENT_LIST_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeArgumentListRAngleRule()
  {
    super(TYPE_ARGUMENT_LIST_RANGLE, TYPE_ARGUMENT_PRODUCTION, TYPE_ARGUMENT_LIST_PRODUCTION, QNAME_LIST_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TYPE_ARGUMENT_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<TypeArgumentAST> argument = (ParseContainer<TypeArgumentAST>) args[0];
      ParseList<TypeArgumentAST> list = new ParseList<TypeArgumentAST>(argument.getItem(), argument.getItem().getParseInfo());
      return new ParseContainer<ParseList<TypeArgumentAST>>(list, argument.getParseInfo());
    }
    if (TYPE_ARGUMENT_LIST_PRODUCTION.equals(production))
    {
      TypeArgumentAST argument = (TypeArgumentAST) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<TypeArgumentAST>> container = (ParseContainer<ParseList<TypeArgumentAST>>) args[2];
      ParseList<TypeArgumentAST> list = container.getItem();

      list.addFirst(argument, ParseInfo.combine(argument.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return new ParseContainer<ParseList<TypeArgumentAST>>(list, ParseInfo.combine(argument.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      TypeAST type = new PointerTypeAST(qname, qname.getParseInfo());
      TypeArgumentAST argument = new NormalTypeArgumentAST(type, type.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<TypeArgumentAST>> container = (ParseContainer<ParseList<TypeArgumentAST>>) args[2];
      ParseList<TypeArgumentAST> list = container.getItem();

      list.addFirst(argument, ParseInfo.combine(argument.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return new ParseContainer<ParseList<TypeArgumentAST>>(list, ParseInfo.combine(argument.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      TypeAST type = element.toType();
      TypeArgumentAST argument = new NormalTypeArgumentAST(type, type.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<TypeArgumentAST>> container = (ParseContainer<ParseList<TypeArgumentAST>>) args[2];
      ParseList<TypeArgumentAST> list = container.getItem();

      list.addFirst(argument, ParseInfo.combine(argument.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return new ParseContainer<ParseList<TypeArgumentAST>>(list, ParseInfo.combine(argument.getParseInfo(), (ParseInfo) args[1], container.getParseInfo()));
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_NOT_QNAME_LIST;
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
public final class TypeArgumentListDoubleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_ARGUMENT_PRODUCTION       = new Production<ParseType>(TYPE_ARGUMENT_DOUBLE_RANGLE);
  private static final Production<ParseType> TYPE_ARGUMENT_LIST_PRODUCTION  = new Production<ParseType>(TYPE_ARGUMENT_NOT_QNAME_LIST, COMMA, TYPE_ARGUMENT_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> QNAME_LIST_PRODUCTION          = new Production<ParseType>(QNAME,                         COMMA, TYPE_ARGUMENT_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION   = new Production<ParseType>(NESTED_QNAME_LIST,             COMMA, TYPE_ARGUMENT_LIST_DOUBLE_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeArgumentListDoubleRAngleRule()
  {
    super(TYPE_ARGUMENT_LIST_DOUBLE_RANGLE, TYPE_ARGUMENT_PRODUCTION, TYPE_ARGUMENT_LIST_PRODUCTION, QNAME_LIST_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
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
      ParseContainer<ParseContainer<TypeArgumentAST>> argument = (ParseContainer<ParseContainer<TypeArgumentAST>>) args[0];
      ParseList<TypeArgumentAST> list = new ParseList<TypeArgumentAST>(argument.getItem().getItem(), argument.getItem().getItem().getParseInfo());
      ParseContainer<ParseList<TypeArgumentAST>> firstContainer = new ParseContainer<ParseList<TypeArgumentAST>>(list, argument.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>>(firstContainer, argument.getParseInfo());
    }

    TypeArgumentAST argument;
    if (TYPE_ARGUMENT_LIST_PRODUCTION.equals(production))
    {
      argument = (TypeArgumentAST) args[0];
    }
    else if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      TypeAST type = new PointerTypeAST(qname, qname.getParseInfo());
      argument = new NormalTypeArgumentAST(type, type.getParseInfo());
    }
    else if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      TypeAST type = element.toType();
      argument = new NormalTypeArgumentAST(type, type.getParseInfo());
    }
    else
    {
      throw badTypeList();
    }

    @SuppressWarnings("unchecked")
    ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>> oldContainer = (ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>>) args[2];
    ParseList<TypeArgumentAST> list = oldContainer.getItem().getItem();
    list.addFirst(argument, ParseInfo.combine(argument.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
    ParseContainer<ParseList<TypeArgumentAST>> firstContainer =
           new ParseContainer<ParseList<TypeArgumentAST>>(list,
                 ParseInfo.combine(argument.getParseInfo(), (ParseInfo) args[1], oldContainer.getItem().getParseInfo()));
    return new ParseContainer<ParseContainer<ParseList<TypeArgumentAST>>>(firstContainer,
                 ParseInfo.combine(argument.getParseInfo(), (ParseInfo) args[1], oldContainer.getParseInfo()));
  }

}
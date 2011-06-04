package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_ARGUMENT_LIST;
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
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeArgumentListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_ARGUMENT_PRODUCTION                  = new Production<ParseType>(TYPE_ARGUMENT_NOT_QNAME_LIST);
  private static final Production<ParseType> QNAME_PRODUCTION                          = new Production<ParseType>(QNAME);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION              = new Production<ParseType>(NESTED_QNAME_LIST);
  private static final Production<ParseType> CONTINUATION_TYPE_ARGUMENT_PRODUCTION     = new Production<ParseType>(TYPE_ARGUMENT_NOT_QNAME_LIST, COMMA, TYPE_ARGUMENT_LIST);
  private static final Production<ParseType> CONTINUATION_QNAME_PRODUCTION             = new Production<ParseType>(QNAME,                         COMMA, TYPE_ARGUMENT_LIST);
  private static final Production<ParseType> CONTINUATION_NESTED_QNAME_LIST_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST,             COMMA, TYPE_ARGUMENT_LIST);

  @SuppressWarnings("unchecked")
  public TypeArgumentListRule()
  {
    super(TYPE_ARGUMENT_LIST, TYPE_ARGUMENT_PRODUCTION,              QNAME_PRODUCTION,              NESTED_QNAME_LIST_PRODUCTION,
                               CONTINUATION_TYPE_ARGUMENT_PRODUCTION, CONTINUATION_QNAME_PRODUCTION, CONTINUATION_NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TYPE_ARGUMENT_PRODUCTION.equals(production))
    {
      TypeArgumentAST typeArgument = (TypeArgumentAST) args[0];
      return new ParseList<TypeArgumentAST>(typeArgument, typeArgument.getParseInfo());
    }
    if (QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      TypeAST type = new PointerTypeAST(qname, qname.getParseInfo());
      TypeArgumentAST argument = new NormalTypeArgumentAST(type, type.getParseInfo());
      return new ParseList<TypeArgumentAST>(argument, argument.getParseInfo());
    }
    if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      TypeAST type = element.toType();
      TypeArgumentAST argument = new NormalTypeArgumentAST(type, type.getParseInfo());
      return new ParseList<TypeArgumentAST>(argument, argument.getParseInfo());
    }
    if (CONTINUATION_TYPE_ARGUMENT_PRODUCTION.equals(production))
    {
      TypeArgumentAST newTypeArgument = (TypeArgumentAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> list = (ParseList<TypeArgumentAST>) args[2];
      list.addFirst(newTypeArgument, ParseInfo.combine(newTypeArgument.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (CONTINUATION_QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      PointerTypeAST type = new PointerTypeAST(qname, qname.getParseInfo());
      TypeArgumentAST newTypeArgument = new NormalTypeArgumentAST(type, type.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> list = (ParseList<TypeArgumentAST>) args[2];
      list.addFirst(newTypeArgument, ParseInfo.combine(newTypeArgument.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (CONTINUATION_NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      TypeAST type = element.toType();
      TypeArgumentAST newTypeArgument = new NormalTypeArgumentAST(type, type.getParseInfo());
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> list = (ParseList<TypeArgumentAST>) args[2];
      list.addFirst(newTypeArgument, ParseInfo.combine(newTypeArgument.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}

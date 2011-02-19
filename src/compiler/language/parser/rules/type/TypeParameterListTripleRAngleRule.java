package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.misc.QNameElementAST;
import compiler.language.ast.type.NormalTypeParameterAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseType;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeParameterListTripleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_PARAMETER_PRODUCTION      = new Production<ParseType>(TYPE_PARAMETER_TRIPLE_RANGLE);
  private static final Production<ParseType> TYPE_PARAMETER_LIST_PRODUCTION = new Production<ParseType>(TYPE_PARAMETER_NOT_QNAME_LIST, COMMA, TYPE_PARAMETER_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> QNAME_LIST_PRODUCTION          = new Production<ParseType>(QNAME,                         COMMA, TYPE_PARAMETER_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION   = new Production<ParseType>(NESTED_QNAME_LIST,             COMMA, TYPE_PARAMETER_LIST_TRIPLE_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeParameterListTripleRAngleRule()
  {
    super(TYPE_PARAMETER_LIST_TRIPLE_RANGLE, TYPE_PARAMETER_PRODUCTION, TYPE_PARAMETER_LIST_PRODUCTION, QNAME_LIST_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TYPE_PARAMETER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<TypeParameterAST>>> parameter = (ParseContainer<ParseContainer<ParseContainer<TypeParameterAST>>>) args[0];
      ParseList<TypeParameterAST> list = new ParseList<TypeParameterAST>(parameter.getItem().getItem().getItem(), parameter.getItem().getItem().getItem().getParseInfo());
      ParseContainer<ParseList<TypeParameterAST>> firstContainer = new ParseContainer<ParseList<TypeParameterAST>>(list, parameter.getItem().getItem().getParseInfo());
      ParseContainer<ParseContainer<ParseList<TypeParameterAST>>> secondContainer =
        new ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>(firstContainer, parameter.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>>(secondContainer, parameter.getParseInfo());
    }

    TypeParameterAST parameter;
    if (TYPE_PARAMETER_LIST_PRODUCTION.equals(production))
    {
      parameter = (TypeParameterAST) args[0];
    }
    else if (QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      TypeAST type = new PointerTypeAST(qname, qname.getParseInfo());
      parameter = new NormalTypeParameterAST(type, type.getParseInfo());
    }
    else if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      TypeAST type = element.toType();
      parameter = new NormalTypeParameterAST(type, type.getParseInfo());
    }
    else
    {
      throw badTypeList();
    }

    @SuppressWarnings("unchecked")
    ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>>) args[2];
    ParseList<TypeParameterAST> list = oldContainer.getItem().getItem().getItem();
    list.addFirst(parameter, ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
    ParseContainer<ParseList<TypeParameterAST>> firstContainer =
           new ParseContainer<ParseList<TypeParameterAST>>(list,
                 ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], oldContainer.getItem().getItem().getParseInfo()));
    ParseContainer<ParseContainer<ParseList<TypeParameterAST>>> secondContainer =
           new ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>(firstContainer,
                 ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], oldContainer.getItem().getParseInfo()));
    return new ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>>(secondContainer,
                 ParseInfo.combine(parameter.getParseInfo(), (ParseInfo) args[1], oldContainer.getParseInfo()));
  }

}

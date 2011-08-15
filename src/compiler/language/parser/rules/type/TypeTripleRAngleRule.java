package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeTripleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION                    = new Production<ParseType>(TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, TRIPLE_RANGLE);
  private static final Production<ParseType> POINTER_TYPE_PRODUCTION       = new Production<ParseType>(POINTER_TYPE_TRIPLE_RANGLE);
  private static final Production<ParseType> TUPLE_TYPE_PRODUCTION         = new Production<ParseType>(TUPLE_TYPE_NOT_QNAME_LIST,            TRIPLE_RANGLE);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION  = new Production<ParseType>(NESTED_QNAME_LIST,                    TRIPLE_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeTripleRAngleRule()
  {
    super(TYPE_TRIPLE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (POINTER_TYPE_PRODUCTION.equals(production))
    {
      // repackage the ParseContainers here to contain a TypeAST and not a PointerTypeAST
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>>) args[0];
      ParseContainer<TypeAST> firstContainer = new ParseContainer<TypeAST>(oldContainer.getItem().getItem().getItem(), oldContainer.getItem().getItem().getLexicalPhrase());
      ParseContainer<ParseContainer<TypeAST>> secondContainer = new ParseContainer<ParseContainer<TypeAST>>(firstContainer, oldContainer.getItem().getLexicalPhrase());
      return new ParseContainer<ParseContainer<ParseContainer<TypeAST>>>(secondContainer, oldContainer.getLexicalPhrase());
    }

    TypeAST type;
    if (PRODUCTION.equals(production) || TUPLE_TYPE_PRODUCTION.equals(production))
    {
      type = (TypeAST) args[0];
    }
    else if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      type = element.toType();
    }
    else
    {
      throw badTypeList();
    }

    LexicalPhrase tripleRAnglePhrase = (LexicalPhrase) args[1];
    LexicalPhrase firstRAnglePhrase = ParseUtil.splitTripleRAngleFirst(tripleRAnglePhrase);
    LexicalPhrase firstTwoRAnglesPhrase = ParseUtil.splitTripleRAngleFirstTwo(tripleRAnglePhrase);
    ParseContainer<TypeAST> firstContainer = new ParseContainer<TypeAST>(type, LexicalPhrase.combine(type.getLexicalPhrase(), firstRAnglePhrase));
    ParseContainer<ParseContainer<TypeAST>> secondContainer = new ParseContainer<ParseContainer<TypeAST>>(firstContainer, LexicalPhrase.combine(type.getLexicalPhrase(), firstTwoRAnglesPhrase));
    return new ParseContainer<ParseContainer<ParseContainer<TypeAST>>>(secondContainer, LexicalPhrase.combine(type.getLexicalPhrase(), tripleRAnglePhrase));
  }

}

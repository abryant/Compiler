package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeParameterListRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TYPE_PARAMETER_PRODUCTION = new Production<ParseType>(TYPE_PARAMETER_RANGLE);
  private static final Production<ParseType> LIST_END_PRODUCTION = new Production<ParseType>(TYPE_PARAMETER_LIST, COMMA, TYPE_PARAMETER_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeParameterListRAngleRule()
  {
    super(TYPE_PARAMETER_LIST_RANGLE, TYPE_PARAMETER_PRODUCTION, LIST_END_PRODUCTION);
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
      ParseContainer<TypeParameterAST> typeParameter = (ParseContainer<TypeParameterAST>) args[0];
      ParseList<TypeParameterAST> list = new ParseList<TypeParameterAST>(typeParameter.getItem(), typeParameter.getItem().getLexicalPhrase());
      return new ParseContainer<ParseList<TypeParameterAST>>(list, typeParameter.getLexicalPhrase());
    }
    if (LIST_END_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> list = (ParseList<TypeParameterAST>) args[0];
      LexicalPhrase listLexicalPhrase = list.getLexicalPhrase();
      @SuppressWarnings("unchecked")
      ParseContainer<TypeParameterAST> typeParameter = (ParseContainer<TypeParameterAST>) args[2];
      list.addLast(typeParameter.getItem(), LexicalPhrase.combine(listLexicalPhrase, (LexicalPhrase) args[1], typeParameter.getItem().getLexicalPhrase()));
      return new ParseContainer<ParseList<TypeParameterAST>>(list, LexicalPhrase.combine(listLexicalPhrase, (LexicalPhrase) args[1], typeParameter.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}

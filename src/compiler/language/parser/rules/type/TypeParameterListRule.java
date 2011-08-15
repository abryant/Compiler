package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.TYPE_PARAMETER;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeParameterListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(TYPE_PARAMETER);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(TYPE_PARAMETER_LIST, COMMA, TYPE_PARAMETER);

  @SuppressWarnings("unchecked")
  public TypeParameterListRule()
  {
    super(TYPE_PARAMETER_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      TypeParameterAST typeParameter = (TypeParameterAST) args[0];
      return new ParseList<TypeParameterAST>(typeParameter, typeParameter.getLexicalPhrase());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> list = (ParseList<TypeParameterAST>) args[0];
      TypeParameterAST newTypeParameter = (TypeParameterAST) args[2];
      list.addLast(newTypeParameter, LexicalPhrase.combine(list.getLexicalPhrase(), (LexicalPhrase) args[1], newTypeParameter.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}

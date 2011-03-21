package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.ARROW;
import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;
import static compiler.language.parser.ParseType.TYPE_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.ClosureTypeAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ClosureTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(LBRACE, TYPE_LIST, ARROW, TYPE_LIST, THROWS_CLAUSE, RBRACE);
  private static final Production<ParseType> TYPE_ARGUMENTS_PRODUCTION = new Production<ParseType>(LBRACE, TYPE_ARGUMENTS, TYPE_LIST, ARROW, TYPE_LIST, THROWS_CLAUSE, RBRACE);

  @SuppressWarnings("unchecked")
  public ClosureTypeRule()
  {
    super(CLOSURE_TYPE, PRODUCTION, TYPE_ARGUMENTS_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> parameterTypes = (ParseList<TypeAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[4];
      return new ClosureTypeAST(new TypeArgumentAST[0], parameterTypes.toArray(new TypeAST[0]), returnTypes.toArray(new TypeAST[0]), thrownTypes.toArray(new PointerTypeAST[0]),
                             ParseInfo.combine((ParseInfo) args[0], parameterTypes.getParseInfo(), (ParseInfo) args[2],
                                               returnTypes.getParseInfo(), thrownTypes.getParseInfo(), (ParseInfo) args[5]));
    }
    if (TYPE_ARGUMENTS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> parameterTypes = (ParseList<TypeAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[5];
      return new ClosureTypeAST(typeArguments.toArray(new TypeArgumentAST[0]), parameterTypes.toArray(new TypeAST[0]),
                             returnTypes.toArray(new TypeAST[0]), thrownTypes.toArray(new PointerTypeAST[0]),
                             ParseInfo.combine((ParseInfo) args[0], typeArguments.getParseInfo(), parameterTypes.getParseInfo(), (ParseInfo) args[3],
                                               returnTypes.getParseInfo(), thrownTypes.getParseInfo(), (ParseInfo) args[6]));
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.ARROW;
import static compiler.language.parser.ParseType.CLOSURE_TYPE;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.RBRACE;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;
import static compiler.language.parser.ParseType.TYPE_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.ClosureType;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeArgument;
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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Type> parameterTypes = (ParseList<Type>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Type> returnTypes = (ParseList<Type>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> thrownTypes = (ParseList<PointerType>) args[4];
      return new ClosureType(new TypeArgument[0], parameterTypes.toArray(new Type[0]), returnTypes.toArray(new Type[0]), thrownTypes.toArray(new PointerType[0]),
                             ParseInfo.combine((ParseInfo) args[0], parameterTypes.getParseInfo(), (ParseInfo) args[2],
                                               returnTypes.getParseInfo(), thrownTypes.getParseInfo(), (ParseInfo) args[5]));
    }
    if (TYPE_ARGUMENTS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> typeArguments = (ParseList<TypeArgument>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Type> parameterTypes = (ParseList<Type>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<Type> returnTypes = (ParseList<Type>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> thrownTypes = (ParseList<PointerType>) args[5];
      return new ClosureType(typeArguments.toArray(new TypeArgument[0]), parameterTypes.toArray(new Type[0]),
                             returnTypes.toArray(new Type[0]), thrownTypes.toArray(new PointerType[0]),
                             ParseInfo.combine((ParseInfo) args[0], typeArguments.getParseInfo(), parameterTypes.getParseInfo(), (ParseInfo) args[3],
                                               returnTypes.getParseInfo(), thrownTypes.getParseInfo(), (ParseInfo) args[6]));
    }
    throw badTypeList();
  }

}

package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARGUMENT_LIST;
import static compiler.language.parser.ParseType.ARROW;
import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.CLOSURE_CREATION_EXPRESSION;
import static compiler.language.parser.ParseType.CLOSURE_KEYWORD;
import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.TYPE_ARGUMENTS;
import static compiler.language.parser.ParseType.TYPE_LIST;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.ClosureCreationExpression;
import compiler.language.ast.misc.Argument;
import compiler.language.ast.statement.Block;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeArgument;
import compiler.language.ast.type.VoidType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClosureCreationExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(CLOSURE_KEYWORD, LPAREN, ARGUMENT_LIST, ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production TYPE_ARGUMENTS_PRODUCTION = new Production(CLOSURE_KEYWORD, TYPE_ARGUMENTS, LPAREN, ARGUMENT_LIST, ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production VOID_PRODUCTION = new Production(CLOSURE_KEYWORD, LPAREN, VOID_TYPE, ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production TYPE_ARGUMENTS_VOID_PRODUCTION = new Production(CLOSURE_KEYWORD, TYPE_ARGUMENTS, LPAREN, VOID_TYPE, ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);

  public ClosureCreationExpressionRule()
  {
    super(CLOSURE_CREATION_EXPRESSION, PRODUCTION, TYPE_ARGUMENTS_PRODUCTION,
                                       VOID_PRODUCTION, TYPE_ARGUMENTS_VOID_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<Argument> arguments = (ParseList<Argument>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<Type> returnTypes = (ParseList<Type>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> thrownTypes = (ParseList<PointerType>) args[6];
      Block block = (Block) args[7];
      return new ClosureCreationExpression(new TypeArgument[0], arguments.toArray(new Argument[0]), returnTypes.toArray(new Type[0]),
                                           thrownTypes.toArray(new PointerType[0]), block,
                                           ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], arguments.getParseInfo(),
                                                             (ParseInfo) args[3], returnTypes.getParseInfo(), (ParseInfo) args[5],
                                                             thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (TYPE_ARGUMENTS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> typeArguments = (ParseList<TypeArgument>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Argument> arguments = (ParseList<Argument>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<Type> returnTypes = (ParseList<Type>) args[5];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> thrownTypes = (ParseList<PointerType>) args[7];
      Block block = (Block) args[8];
      return new ClosureCreationExpression(typeArguments.toArray(new TypeArgument[0]), arguments.toArray(new Argument[0]), returnTypes.toArray(new Type[0]),
                                           thrownTypes.toArray(new PointerType[0]), block,
                                           ParseInfo.combine((ParseInfo) args[0], typeArguments.getParseInfo(), (ParseInfo) args[2], arguments.getParseInfo(),
                                                             (ParseInfo) args[4], returnTypes.getParseInfo(), (ParseInfo) args[6],
                                                             thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (VOID_PRODUCTION.equals(production))
    {
      VoidType voidType = (VoidType) args[2];
      @SuppressWarnings("unchecked")
      ParseList<Type> returnTypes = (ParseList<Type>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> thrownTypes = (ParseList<PointerType>) args[6];
      Block block = (Block) args[7];
      return new ClosureCreationExpression(new TypeArgument[0], new Argument[0], returnTypes.toArray(new Type[0]),
                                           thrownTypes.toArray(new PointerType[0]), block,
                                           ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], voidType.getParseInfo(),
                                                             (ParseInfo) args[3], returnTypes.getParseInfo(), (ParseInfo) args[5],
                                                             thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (TYPE_ARGUMENTS_VOID_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgument> typeArguments = (ParseList<TypeArgument>) args[1];
      VoidType voidType = (VoidType) args[3];
      @SuppressWarnings("unchecked")
      ParseList<Type> returnTypes = (ParseList<Type>) args[5];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> thrownTypes = (ParseList<PointerType>) args[7];
      Block block = (Block) args[8];
      return new ClosureCreationExpression(typeArguments.toArray(new TypeArgument[0]), new Argument[0], returnTypes.toArray(new Type[0]),
                                           thrownTypes.toArray(new PointerType[0]), block,
                                           ParseInfo.combine((ParseInfo) args[0], typeArguments.getParseInfo(), (ParseInfo) args[2], voidType.getParseInfo(),
                                                             (ParseInfo) args[4], returnTypes.getParseInfo(), (ParseInfo) args[6],
                                                             thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}

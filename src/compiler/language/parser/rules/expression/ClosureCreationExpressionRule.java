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
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.ClosureCreationExpressionAST;
import compiler.language.ast.misc.ArgumentAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeArgumentAST;
import compiler.language.ast.type.VoidTypeAST;
import compiler.language.parser.ParseType;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ClosureCreationExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION =
    new Production<ParseType>(CLOSURE_KEYWORD,                 LPAREN, ARGUMENT_LIST, ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> TYPE_ARGUMENTS_PRODUCTION =
    new Production<ParseType>(CLOSURE_KEYWORD, TYPE_ARGUMENTS, LPAREN, ARGUMENT_LIST, ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> VOID_PRODUCTION =
    new Production<ParseType>(CLOSURE_KEYWORD,                 LPAREN, VOID_TYPE,     ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> TYPE_ARGUMENTS_VOID_PRODUCTION =
    new Production<ParseType>(CLOSURE_KEYWORD, TYPE_ARGUMENTS, LPAREN, VOID_TYPE,     ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);

  private static final Production<ParseType> PRODUCTION_NO_ARROW =
    new Production<ParseType>(CLOSURE_KEYWORD,                 LPAREN, ARGUMENT_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> TYPE_ARGUMENTS_PRODUCTION_NO_ARROW =
    new Production<ParseType>(CLOSURE_KEYWORD, TYPE_ARGUMENTS, LPAREN, ARGUMENT_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> VOID_PRODUCTION_NO_ARROW =
    new Production<ParseType>(CLOSURE_KEYWORD,                 LPAREN,                RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> TYPE_ARGUMENTS_VOID_PRODUCTION_NO_ARROW =
    new Production<ParseType>(CLOSURE_KEYWORD, TYPE_ARGUMENTS, LPAREN,                RPAREN, THROWS_CLAUSE, BLOCK);

  @SuppressWarnings("unchecked")
  public ClosureCreationExpressionRule()
  {
    super(CLOSURE_CREATION_EXPRESSION, PRODUCTION, TYPE_ARGUMENTS_PRODUCTION,
                                       VOID_PRODUCTION, TYPE_ARGUMENTS_VOID_PRODUCTION,
                                       PRODUCTION_NO_ARROW, TYPE_ARGUMENTS_PRODUCTION_NO_ARROW,
                                       VOID_PRODUCTION_NO_ARROW, TYPE_ARGUMENTS_VOID_PRODUCTION_NO_ARROW);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ArgumentAST> arguments = (ParseList<ArgumentAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[6];
      BlockAST block = (BlockAST) args[7];
      return new ClosureCreationExpressionAST(new TypeArgumentAST[0], arguments.toArray(new ArgumentAST[0]), returnTypes.toArray(new TypeAST[0]),
                                           thrownTypes.toArray(new PointerTypeAST[0]), block,
                                           ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], arguments.getParseInfo(),
                                                             (ParseInfo) args[3], returnTypes.getParseInfo(), (ParseInfo) args[5],
                                                             thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (TYPE_ARGUMENTS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<ArgumentAST> arguments = (ParseList<ArgumentAST>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[5];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[7];
      BlockAST block = (BlockAST) args[8];
      return new ClosureCreationExpressionAST(typeArguments.toArray(new TypeArgumentAST[0]), arguments.toArray(new ArgumentAST[0]), returnTypes.toArray(new TypeAST[0]),
                                           thrownTypes.toArray(new PointerTypeAST[0]), block,
                                           ParseInfo.combine((ParseInfo) args[0], typeArguments.getParseInfo(), (ParseInfo) args[2], arguments.getParseInfo(),
                                                             (ParseInfo) args[4], returnTypes.getParseInfo(), (ParseInfo) args[6],
                                                             thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (VOID_PRODUCTION.equals(production))
    {
      VoidTypeAST voidType = (VoidTypeAST) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[6];
      BlockAST block = (BlockAST) args[7];
      return new ClosureCreationExpressionAST(new TypeArgumentAST[0], new ArgumentAST[0], returnTypes.toArray(new TypeAST[0]),
                                           thrownTypes.toArray(new PointerTypeAST[0]), block,
                                           ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], voidType.getParseInfo(),
                                                             (ParseInfo) args[3], returnTypes.getParseInfo(), (ParseInfo) args[5],
                                                             thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (TYPE_ARGUMENTS_VOID_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[1];
      VoidTypeAST voidType = (VoidTypeAST) args[3];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[5];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[7];
      BlockAST block = (BlockAST) args[8];
      return new ClosureCreationExpressionAST(typeArguments.toArray(new TypeArgumentAST[0]), new ArgumentAST[0], returnTypes.toArray(new TypeAST[0]),
                                           thrownTypes.toArray(new PointerTypeAST[0]), block,
                                           ParseInfo.combine((ParseInfo) args[0], typeArguments.getParseInfo(), (ParseInfo) args[2], voidType.getParseInfo(),
                                                             (ParseInfo) args[4], returnTypes.getParseInfo(), (ParseInfo) args[6],
                                                             thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (PRODUCTION_NO_ARROW.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ArgumentAST> arguments = (ParseList<ArgumentAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[4];
      BlockAST block = (BlockAST) args[5];
      return new ClosureCreationExpressionAST(new TypeArgumentAST[0], arguments.toArray(new ArgumentAST[0]), new TypeAST[0],
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], arguments.getParseInfo(),
                                                                (ParseInfo) args[3], thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (TYPE_ARGUMENTS_PRODUCTION_NO_ARROW.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<ArgumentAST> arguments = (ParseList<ArgumentAST>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[5];
      BlockAST block = (BlockAST) args[6];
      return new ClosureCreationExpressionAST(typeArguments.toArray(new TypeArgumentAST[0]), arguments.toArray(new ArgumentAST[0]), new TypeAST[0],
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              ParseInfo.combine((ParseInfo) args[0], typeArguments.getParseInfo(), (ParseInfo) args[2],
                                                                arguments.getParseInfo(), (ParseInfo) args[4],
                                                                thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (VOID_PRODUCTION_NO_ARROW.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[3];
      BlockAST block = (BlockAST) args[4];
      return new ClosureCreationExpressionAST(new TypeArgumentAST[0], new ArgumentAST[0], new TypeAST[0],
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], (ParseInfo) args[2],
                                                                thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    if (TYPE_ARGUMENTS_VOID_PRODUCTION_NO_ARROW.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeArgumentAST> typeArguments = (ParseList<TypeArgumentAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[4];
      BlockAST block = (BlockAST) args[5];
      return new ClosureCreationExpressionAST(typeArguments.toArray(new TypeArgumentAST[0]), new ArgumentAST[0], new TypeAST[0],
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              ParseInfo.combine((ParseInfo) args[0], typeArguments.getParseInfo(), (ParseInfo) args[2],
                                                                (ParseInfo) args[3], thrownTypes.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}

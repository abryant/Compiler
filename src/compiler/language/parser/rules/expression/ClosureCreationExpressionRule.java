package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARROW;
import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.CLOSURE_CREATION_EXPRESSION;
import static compiler.language.parser.ParseType.CLOSURE_KEYWORD;
import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.PARAMETER_LIST;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.THROWS_CLAUSE;
import static compiler.language.parser.ParseType.TYPE_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;
import static compiler.language.parser.ParseType.VOID_TYPE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.ClosureCreationExpressionAST;
import compiler.language.ast.misc.ParameterAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.ast.type.VoidTypeAST;
import compiler.language.parser.ParseList;
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
    new Production<ParseType>(CLOSURE_KEYWORD,                 LPAREN, PARAMETER_LIST, ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> TYPE_ARGUMENTS_PRODUCTION =
    new Production<ParseType>(CLOSURE_KEYWORD, TYPE_PARAMETERS, LPAREN, PARAMETER_LIST, ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> VOID_PRODUCTION =
    new Production<ParseType>(CLOSURE_KEYWORD,                 LPAREN, VOID_TYPE,     ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> TYPE_ARGUMENTS_VOID_PRODUCTION =
    new Production<ParseType>(CLOSURE_KEYWORD, TYPE_PARAMETERS, LPAREN, VOID_TYPE,     ARROW, TYPE_LIST, RPAREN, THROWS_CLAUSE, BLOCK);

  private static final Production<ParseType> PRODUCTION_NO_ARROW =
    new Production<ParseType>(CLOSURE_KEYWORD,                 LPAREN, PARAMETER_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> TYPE_ARGUMENTS_PRODUCTION_NO_ARROW =
    new Production<ParseType>(CLOSURE_KEYWORD, TYPE_PARAMETERS, LPAREN, PARAMETER_LIST, RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> VOID_PRODUCTION_NO_ARROW =
    new Production<ParseType>(CLOSURE_KEYWORD,                 LPAREN,                RPAREN, THROWS_CLAUSE, BLOCK);
  private static final Production<ParseType> TYPE_ARGUMENTS_VOID_PRODUCTION_NO_ARROW =
    new Production<ParseType>(CLOSURE_KEYWORD, TYPE_PARAMETERS, LPAREN,                RPAREN, THROWS_CLAUSE, BLOCK);

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
      ParseList<ParameterAST> parameters = (ParseList<ParameterAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[6];
      BlockAST block = (BlockAST) args[7];
      return new ClosureCreationExpressionAST(new TypeParameterAST[0], parameters.toArray(new ParameterAST[0]), returnTypes.toArray(new TypeAST[0]),
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], parameters.getLexicalPhrase(),
                                                                (LexicalPhrase) args[3], returnTypes.getLexicalPhrase(), (LexicalPhrase) args[5],
                                                                thrownTypes.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    if (TYPE_ARGUMENTS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> typeParameters = (ParseList<TypeParameterAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> parameters = (ParseList<ParameterAST>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[5];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[7];
      BlockAST block = (BlockAST) args[8];
      return new ClosureCreationExpressionAST(typeParameters.toArray(new TypeParameterAST[0]), parameters.toArray(new ParameterAST[0]), returnTypes.toArray(new TypeAST[0]),
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              LexicalPhrase.combine((LexicalPhrase) args[0], typeParameters.getLexicalPhrase(), (LexicalPhrase) args[2], parameters.getLexicalPhrase(),
                                                                (LexicalPhrase) args[4], returnTypes.getLexicalPhrase(), (LexicalPhrase) args[6],
                                                                thrownTypes.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    if (VOID_PRODUCTION.equals(production))
    {
      VoidTypeAST voidType = (VoidTypeAST) args[2];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[4];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[6];
      BlockAST block = (BlockAST) args[7];
      return new ClosureCreationExpressionAST(new TypeParameterAST[0], new ParameterAST[0], returnTypes.toArray(new TypeAST[0]),
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], voidType.getLexicalPhrase(),
                                                                (LexicalPhrase) args[3], returnTypes.getLexicalPhrase(), (LexicalPhrase) args[5],
                                                                thrownTypes.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    if (TYPE_ARGUMENTS_VOID_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> typeParameters = (ParseList<TypeParameterAST>) args[1];
      VoidTypeAST voidType = (VoidTypeAST) args[3];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> returnTypes = (ParseList<TypeAST>) args[5];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[7];
      BlockAST block = (BlockAST) args[8];
      return new ClosureCreationExpressionAST(typeParameters.toArray(new TypeParameterAST[0]), new ParameterAST[0], returnTypes.toArray(new TypeAST[0]),
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              LexicalPhrase.combine((LexicalPhrase) args[0], typeParameters.getLexicalPhrase(), (LexicalPhrase) args[2], voidType.getLexicalPhrase(),
                                                                (LexicalPhrase) args[4], returnTypes.getLexicalPhrase(), (LexicalPhrase) args[6],
                                                                thrownTypes.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    if (PRODUCTION_NO_ARROW.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> parameters = (ParseList<ParameterAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[4];
      BlockAST block = (BlockAST) args[5];
      return new ClosureCreationExpressionAST(new TypeParameterAST[0], parameters.toArray(new ParameterAST[0]), new TypeAST[0],
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], parameters.getLexicalPhrase(),
                                                                (LexicalPhrase) args[3], thrownTypes.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    if (TYPE_ARGUMENTS_PRODUCTION_NO_ARROW.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> typeParameters = (ParseList<TypeParameterAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> parameters = (ParseList<ParameterAST>) args[3];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[5];
      BlockAST block = (BlockAST) args[6];
      return new ClosureCreationExpressionAST(typeParameters.toArray(new TypeParameterAST[0]), parameters.toArray(new ParameterAST[0]), new TypeAST[0],
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              LexicalPhrase.combine((LexicalPhrase) args[0], typeParameters.getLexicalPhrase(), (LexicalPhrase) args[2],
                                                                parameters.getLexicalPhrase(), (LexicalPhrase) args[4],
                                                                thrownTypes.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    if (VOID_PRODUCTION_NO_ARROW.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[3];
      BlockAST block = (BlockAST) args[4];
      return new ClosureCreationExpressionAST(new TypeParameterAST[0], new ParameterAST[0], new TypeAST[0],
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], (LexicalPhrase) args[2],
                                                                thrownTypes.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    if (TYPE_ARGUMENTS_VOID_PRODUCTION_NO_ARROW.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<TypeParameterAST> typeParameters = (ParseList<TypeParameterAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> thrownTypes = (ParseList<PointerTypeAST>) args[4];
      BlockAST block = (BlockAST) args[5];
      return new ClosureCreationExpressionAST(typeParameters.toArray(new TypeParameterAST[0]), new ParameterAST[0], new TypeAST[0],
                                              thrownTypes.toArray(new PointerTypeAST[0]), block,
                                              LexicalPhrase.combine((LexicalPhrase) args[0], typeParameters.getLexicalPhrase(), (LexicalPhrase) args[2],
                                                                (LexicalPhrase) args[3], thrownTypes.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}

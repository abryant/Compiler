package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER;
import static compiler.language.parser.ParseType.BASIC_PRIMARY;
import static compiler.language.parser.ParseType.BOOLEAN_LITERAL_EXPRESSION;
import static compiler.language.parser.ParseType.CHARACTER_LITERAL;
import static compiler.language.parser.ParseType.CLOSURE_CREATION_EXPRESSION;
import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION_NOT_QNAME;
import static compiler.language.parser.ParseType.FLOATING_LITERAL;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.NIL_KEYWORD;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;
import static compiler.language.parser.ParseType.STRING_LITERAL;
import static compiler.language.parser.ParseType.SUPER_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.THIS_ACCESS_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.CharacterLiteralExpressionAST;
import compiler.language.ast.expression.FloatingLiteralExpressionAST;
import compiler.language.ast.expression.IntegerLiteralExpressionAST;
import compiler.language.ast.expression.NilLiteralExpressionAST;
import compiler.language.ast.expression.StringLiteralExpressionAST;
import compiler.language.ast.terminal.CharacterLiteralAST;
import compiler.language.ast.terminal.FloatingLiteralAST;
import compiler.language.ast.terminal.IntegerLiteralAST;
import compiler.language.ast.terminal.StringLiteralAST;
import compiler.language.parser.ParseType;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BasicPrimaryRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> INTEGER_LITERAL_PRODUCTION = new Production<ParseType>(INTEGER_LITERAL);
  private static final Production<ParseType> FLOATING_LITERAL_PRODUCTION = new Production<ParseType>(FLOATING_LITERAL);
  private static final Production<ParseType> BOOLEAN_LITERAL_PRODUCTION = new Production<ParseType>(BOOLEAN_LITERAL_EXPRESSION);
  private static final Production<ParseType> CHARACTER_LITERAL_PRODUCTION = new Production<ParseType>(CHARACTER_LITERAL);
  private static final Production<ParseType> STRING_LITERAL_PRODUCTION = new Production<ParseType>(STRING_LITERAL);
  private static final Production<ParseType> NIL_LITERAL_PRODUCTION = new Production<ParseType>(NIL_KEYWORD);
  private static final Production<ParseType> FIELD_ACCESS_PRODUCTION = new Production<ParseType>(FIELD_ACCESS_EXPRESSION_NOT_QNAME);
  private static final Production<ParseType> STATEMENT_EXPRESSION_PRODUCTION = new Production<ParseType>(STATEMENT_EXPRESSION);
  private static final Production<ParseType> THIS_ACCESS_PRODUCTION = new Production<ParseType>(THIS_ACCESS_EXPRESSION);
  private static final Production<ParseType> SUPER_ACCESS_PRODUCTION = new Production<ParseType>(SUPER_ACCESS_EXPRESSION);
  private static final Production<ParseType> ARRAY_ACCESS_PRODUCTION = new Production<ParseType>(ARRAY_ACCESS_EXPRESSION);
  private static final Production<ParseType> CLOSURE_CREATION_PRODUCTION = new Production<ParseType>(CLOSURE_CREATION_EXPRESSION);
  private static final Production<ParseType> ARRAY_INSTANCIATION_PRODUCTION = new Production<ParseType>(ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER);

  @SuppressWarnings("unchecked")
  public BasicPrimaryRule()
  {
    super(BASIC_PRIMARY, INTEGER_LITERAL_PRODUCTION,   FLOATING_LITERAL_PRODUCTION,
                         BOOLEAN_LITERAL_PRODUCTION,   NIL_LITERAL_PRODUCTION,
                         CHARACTER_LITERAL_PRODUCTION, STRING_LITERAL_PRODUCTION,
                         FIELD_ACCESS_PRODUCTION,
                         STATEMENT_EXPRESSION_PRODUCTION,
                         THIS_ACCESS_PRODUCTION, SUPER_ACCESS_PRODUCTION,
                         ARRAY_ACCESS_PRODUCTION,
                         CLOSURE_CREATION_PRODUCTION,
                         ARRAY_INSTANCIATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (INTEGER_LITERAL_PRODUCTION.equals(production))
    {
      IntegerLiteralAST literal = (IntegerLiteralAST) args[0];
      return new IntegerLiteralExpressionAST(literal, literal.getParseInfo());
    }
    if (FLOATING_LITERAL_PRODUCTION.equals(production))
    {
      FloatingLiteralAST literal = (FloatingLiteralAST) args[0];
      return new FloatingLiteralExpressionAST(literal, literal.getParseInfo());
    }
    if (CHARACTER_LITERAL_PRODUCTION.equals(production))
    {
      CharacterLiteralAST literal = (CharacterLiteralAST) args[0];
      return new CharacterLiteralExpressionAST(literal, literal.getParseInfo());
    }
    if (STRING_LITERAL_PRODUCTION.equals(production))
    {
      StringLiteralAST literal = (StringLiteralAST) args[0];
      return new StringLiteralExpressionAST(literal, literal.getParseInfo());
    }
    if (NIL_LITERAL_PRODUCTION.equals(production))
    {
      return new NilLiteralExpressionAST((ParseInfo) args[0]);
    }
    if (FIELD_ACCESS_PRODUCTION.equals(production)        || STATEMENT_EXPRESSION_PRODUCTION.equals(production) ||
        THIS_ACCESS_PRODUCTION.equals(production)         || SUPER_ACCESS_PRODUCTION.equals(production)         ||
        ARRAY_ACCESS_PRODUCTION.equals(production)        || CLOSURE_CREATION_PRODUCTION.equals(production)     ||
        ARRAY_INSTANCIATION_PRODUCTION.equals(production) || BOOLEAN_LITERAL_PRODUCTION.equals(production))
    {
      // an ExpressionAST has already been generated, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

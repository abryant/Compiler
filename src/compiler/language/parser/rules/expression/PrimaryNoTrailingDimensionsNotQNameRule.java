package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER;
import static compiler.language.parser.ParseType.CHARACTER_LITERAL;
import static compiler.language.parser.ParseType.CLOSURE_CREATION_EXPRESSION;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION_NOT_QNAME;
import static compiler.language.parser.ParseType.FLOATING_LITERAL;
import static compiler.language.parser.ParseType.INTEGER_LITERAL;
import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.NIL_KEYWORD;
import static compiler.language.parser.ParseType.PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;
import static compiler.language.parser.ParseType.STRING_LITERAL;
import static compiler.language.parser.ParseType.SUPER_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.THIS_ACCESS_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.CharacterLiteralExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FloatingLiteralExpression;
import compiler.language.ast.expression.IntegerLiteralExpression;
import compiler.language.ast.expression.NilLiteralExpression;
import compiler.language.ast.expression.ParenthesisedExpression;
import compiler.language.ast.expression.StringLiteralExpression;
import compiler.language.ast.terminal.CharacterLiteral;
import compiler.language.ast.terminal.FloatingLiteral;
import compiler.language.ast.terminal.IntegerLiteral;
import compiler.language.ast.terminal.StringLiteral;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PrimaryNoTrailingDimensionsNotQNameRule extends Rule
{

  private static final Object[] PARENTHESISED_PRODUCTION = new Object[] {LPAREN, EXPRESSION, RPAREN};
  private static final Object[] INTEGER_LITERAL_PRODUCTION = new Object[] {INTEGER_LITERAL};
  private static final Object[] FLOATING_LITERAL_PRODUCTION = new Object[] {FLOATING_LITERAL};
  private static final Object[] CHARACTER_LITERAL_PRODUCTION = new Object[] {CHARACTER_LITERAL};
  private static final Object[] STRING_LITERAL_PRODUCTION = new Object[] {STRING_LITERAL};
  private static final Object[] NIL_LITERAL_PRODUCTION = new Object[] {NIL_KEYWORD};
  private static final Object[] FIELD_ACCESS_PRODUCTION = new Object[] {FIELD_ACCESS_EXPRESSION_NOT_QNAME};
  private static final Object[] STATEMENT_EXPRESSION_PRODUCTION = new Object[] {STATEMENT_EXPRESSION};
  private static final Object[] THIS_ACCESS_PRODUCTION = new Object[] {THIS_ACCESS_EXPRESSION};
  private static final Object[] SUPER_ACCESS_PRODUCTION = new Object[] {SUPER_ACCESS_EXPRESSION};
  private static final Object[] ARRAY_ACCESS_PRODUCTION = new Object[] {ARRAY_ACCESS_EXPRESSION};
  private static final Object[] CLOSURE_CREATION_PRODUCTION = new Object[] {CLOSURE_CREATION_EXPRESSION};
  private static final Object[] ARRAY_INSTANCIATION_PRODUCTION = new Object[] {ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER};

  public PrimaryNoTrailingDimensionsNotQNameRule()
  {
    super(PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME, PARENTHESISED_PRODUCTION,
                                                    INTEGER_LITERAL_PRODUCTION, FLOATING_LITERAL_PRODUCTION,
                                                    CHARACTER_LITERAL_PRODUCTION, STRING_LITERAL_PRODUCTION,
                                                    NIL_LITERAL_PRODUCTION,
                                                    FIELD_ACCESS_PRODUCTION,
                                                    STATEMENT_EXPRESSION_PRODUCTION,
                                                    THIS_ACCESS_PRODUCTION,
                                                    SUPER_ACCESS_PRODUCTION,
                                                    ARRAY_ACCESS_PRODUCTION,
                                                    CLOSURE_CREATION_PRODUCTION,
                                                    ARRAY_INSTANCIATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PARENTHESISED_PRODUCTION)
    {
      Expression expression = (Expression) args[1];
      return new ParenthesisedExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    if (types == INTEGER_LITERAL_PRODUCTION)
    {
      IntegerLiteral literal = (IntegerLiteral) args[0];
      return new IntegerLiteralExpression(literal, literal.getParseInfo());
    }
    if (types == FLOATING_LITERAL_PRODUCTION)
    {
      FloatingLiteral literal = (FloatingLiteral) args[0];
      return new FloatingLiteralExpression(literal, literal.getParseInfo());
    }
    if (types == CHARACTER_LITERAL_PRODUCTION)
    {
      CharacterLiteral literal = (CharacterLiteral) args[0];
      return new CharacterLiteralExpression(literal, literal.getParseInfo());
    }
    if (types == STRING_LITERAL_PRODUCTION)
    {
      StringLiteral literal = (StringLiteral) args[0];
      return new StringLiteralExpression(literal, literal.getParseInfo());
    }
    if (types == NIL_LITERAL_PRODUCTION)
    {
      return new NilLiteralExpression((ParseInfo) args[0]);
    }
    if (types == FIELD_ACCESS_PRODUCTION || types == STATEMENT_EXPRESSION_PRODUCTION ||
        types == THIS_ACCESS_PRODUCTION  || types == SUPER_ACCESS_PRODUCTION         ||
        types == ARRAY_ACCESS_PRODUCTION || types == CLOSURE_CREATION_PRODUCTION     ||
        types == ARRAY_INSTANCIATION_PRODUCTION)
    {
      // an expression has already been generated, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

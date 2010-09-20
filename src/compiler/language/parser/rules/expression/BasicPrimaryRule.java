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

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.CharacterLiteralExpression;
import compiler.language.ast.expression.FloatingLiteralExpression;
import compiler.language.ast.expression.IntegerLiteralExpression;
import compiler.language.ast.expression.NilLiteralExpression;
import compiler.language.ast.expression.StringLiteralExpression;
import compiler.language.ast.terminal.CharacterLiteral;
import compiler.language.ast.terminal.FloatingLiteral;
import compiler.language.ast.terminal.IntegerLiteral;
import compiler.language.ast.terminal.StringLiteral;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BasicPrimaryRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production INTEGER_LITERAL_PRODUCTION = new Production(INTEGER_LITERAL);
  private static final Production FLOATING_LITERAL_PRODUCTION = new Production(FLOATING_LITERAL);
  private static final Production BOOLEAN_LITERAL_PRODUCTION = new Production(BOOLEAN_LITERAL_EXPRESSION);
  private static final Production CHARACTER_LITERAL_PRODUCTION = new Production(CHARACTER_LITERAL);
  private static final Production STRING_LITERAL_PRODUCTION = new Production(STRING_LITERAL);
  private static final Production NIL_LITERAL_PRODUCTION = new Production(NIL_KEYWORD);
  private static final Production FIELD_ACCESS_PRODUCTION = new Production(FIELD_ACCESS_EXPRESSION_NOT_QNAME);
  private static final Production STATEMENT_EXPRESSION_PRODUCTION = new Production(STATEMENT_EXPRESSION);
  private static final Production THIS_ACCESS_PRODUCTION = new Production(THIS_ACCESS_EXPRESSION);
  private static final Production SUPER_ACCESS_PRODUCTION = new Production(SUPER_ACCESS_EXPRESSION);
  private static final Production ARRAY_ACCESS_PRODUCTION = new Production(ARRAY_ACCESS_EXPRESSION);
  private static final Production CLOSURE_CREATION_PRODUCTION = new Production(CLOSURE_CREATION_EXPRESSION);
  private static final Production ARRAY_INSTANCIATION_PRODUCTION = new Production(ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER);

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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (INTEGER_LITERAL_PRODUCTION.equals(production))
    {
      IntegerLiteral literal = (IntegerLiteral) args[0];
      return new IntegerLiteralExpression(literal, literal.getParseInfo());
    }
    if (FLOATING_LITERAL_PRODUCTION.equals(production))
    {
      FloatingLiteral literal = (FloatingLiteral) args[0];
      return new FloatingLiteralExpression(literal, literal.getParseInfo());
    }
    if (CHARACTER_LITERAL_PRODUCTION.equals(production))
    {
      CharacterLiteral literal = (CharacterLiteral) args[0];
      return new CharacterLiteralExpression(literal, literal.getParseInfo());
    }
    if (STRING_LITERAL_PRODUCTION.equals(production))
    {
      StringLiteral literal = (StringLiteral) args[0];
      return new StringLiteralExpression(literal, literal.getParseInfo());
    }
    if (NIL_LITERAL_PRODUCTION.equals(production))
    {
      return new NilLiteralExpression((ParseInfo) args[0]);
    }
    if (FIELD_ACCESS_PRODUCTION.equals(production)        || STATEMENT_EXPRESSION_PRODUCTION.equals(production) ||
        THIS_ACCESS_PRODUCTION.equals(production)         || SUPER_ACCESS_PRODUCTION.equals(production)         ||
        ARRAY_ACCESS_PRODUCTION.equals(production)        || CLOSURE_CREATION_PRODUCTION.equals(production)     ||
        ARRAY_INSTANCIATION_PRODUCTION.equals(production) || BOOLEAN_LITERAL_PRODUCTION.equals(production))
    {
      // an Expression has already been generated, so return it
      return args[0];
    }
    throw badTypeList();
  }

}

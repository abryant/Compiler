package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNMENT;
import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.BREAK_STATEMENT;
import static compiler.language.parser.ParseType.CONTINUE_STATEMENT;
import static compiler.language.parser.ParseType.DECREMENT;
import static compiler.language.parser.ParseType.DO_STATEMENT;
import static compiler.language.parser.ParseType.EMPTY_STATEMENT;
import static compiler.language.parser.ParseType.FALLTHROUGH_STATEMENT;
import static compiler.language.parser.ParseType.FOR_EACH_STATEMENT;
import static compiler.language.parser.ParseType.FOR_STATEMENT;
import static compiler.language.parser.ParseType.IF_STATEMENT;
import static compiler.language.parser.ParseType.INCREMENT;
import static compiler.language.parser.ParseType.LOCAL_DECLARATION;
import static compiler.language.parser.ParseType.RETURN_STATEMENT;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.STATEMENT;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;
import static compiler.language.parser.ParseType.SWITCH_STATEMENT;
import static compiler.language.parser.ParseType.SYNCHRONIZED_STATEMENT;
import static compiler.language.parser.ParseType.THROW_STATEMENT;
import static compiler.language.parser.ParseType.TRY_STATEMENT;
import static compiler.language.parser.ParseType.WHILE_STATEMENT;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.StatementExpression;
import compiler.language.ast.statement.ExpressionStatement;
import compiler.language.ast.statement.Statement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StatementRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production BLOCK_PRODUCTION                  = new Production(BLOCK);
  private static final Production EMPTY_PRODUCTION                  = new Production(EMPTY_STATEMENT);
  private static final Production LOCAL_DECLARATION_PRODUCTION      = new Production(LOCAL_DECLARATION, SEMICOLON);
  private static final Production ASSIGNMENT_PRODUCTION             = new Production(ASSIGNMENT,        SEMICOLON);
  private static final Production IF_PRODUCTION                     = new Production(IF_STATEMENT);
  private static final Production WHILE_PRODUCTION                  = new Production(WHILE_STATEMENT);
  private static final Production DO_PRODUCTION                     = new Production(DO_STATEMENT);
  private static final Production FOR_PRODUCTION                    = new Production(FOR_STATEMENT);
  private static final Production FOR_EACH_PRODUCTION               = new Production(FOR_EACH_STATEMENT);
  private static final Production SWITCH_PRODUCTION                 = new Production(SWITCH_STATEMENT);
  private static final Production BREAK_PRODUCTION                  = new Production(BREAK_STATEMENT);
  private static final Production CONTINUE_PRODUCTION               = new Production(CONTINUE_STATEMENT);
  private static final Production FALLTHROUGH_PRODUCTION            = new Production(FALLTHROUGH_STATEMENT);
  private static final Production RETURN_PRODUCTION                 = new Production(RETURN_STATEMENT);
  private static final Production SYNCHRONIZED_PRODUCTION           = new Production(SYNCHRONIZED_STATEMENT);
  private static final Production THROW_PRODUCTION                  = new Production(THROW_STATEMENT);
  private static final Production TRY_PRODUCTION                    = new Production(TRY_STATEMENT);
  private static final Production INCREMENT_PRODUCTION              = new Production(INCREMENT,            SEMICOLON);
  private static final Production DECREMENT_PRODUCTION              = new Production(DECREMENT,            SEMICOLON);
  private static final Production STATEMENT_EXPRESSION_PRODUCTION   = new Production(STATEMENT_EXPRESSION, SEMICOLON);

  public StatementRule()
  {
    super(STATEMENT, BLOCK_PRODUCTION,
                     EMPTY_PRODUCTION,
                     LOCAL_DECLARATION_PRODUCTION,
                     ASSIGNMENT_PRODUCTION,
                     IF_PRODUCTION,
                     WHILE_PRODUCTION,
                     DO_PRODUCTION,
                     FOR_PRODUCTION,
                     FOR_EACH_PRODUCTION,
                     SWITCH_PRODUCTION,
                     BREAK_PRODUCTION,
                     CONTINUE_PRODUCTION,
                     FALLTHROUGH_PRODUCTION,
                     RETURN_PRODUCTION,
                     SYNCHRONIZED_PRODUCTION,
                     THROW_PRODUCTION,
                     TRY_PRODUCTION,
                     INCREMENT_PRODUCTION,
                     DECREMENT_PRODUCTION,
                     STATEMENT_EXPRESSION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (BLOCK_PRODUCTION.equals(production)    || EMPTY_PRODUCTION.equals(production)        || IF_PRODUCTION.equals(production)          ||
        WHILE_PRODUCTION.equals(production)    || DO_PRODUCTION.equals(production)           || FOR_PRODUCTION.equals(production)         ||
        FOR_EACH_PRODUCTION.equals(production) || SWITCH_PRODUCTION.equals(production)       || TRY_PRODUCTION.equals(production)         ||
        BREAK_PRODUCTION.equals(production)    || CONTINUE_PRODUCTION.equals(production)     || FALLTHROUGH_PRODUCTION.equals(production) ||
        RETURN_PRODUCTION.equals(production)   || SYNCHRONIZED_PRODUCTION.equals(production) || THROW_PRODUCTION.equals(production))
    {
      // these productions only take one argument and all return a Statement, so return the argument
      return args[0];
    }

    if (LOCAL_DECLARATION_PRODUCTION.equals(production) || ASSIGNMENT_PRODUCTION.equals(production) ||
        INCREMENT_PRODUCTION.equals(production)         || DECREMENT_PRODUCTION.equals(production))
    {
      // add the semicolon's ParseInfo to the statement before returning it
      Statement statement = (Statement) args[0];
      statement.setParseInfo(ParseInfo.combine(statement.getParseInfo(), (ParseInfo) args[1]));
      return statement;
    }

    if (STATEMENT_EXPRESSION_PRODUCTION.equals(production))
    {
      StatementExpression expression = (StatementExpression) args[0];
      return new ExpressionStatement(expression, ParseInfo.combine(expression.getParseInfo(), (ParseInfo) args[1]));
    }

    throw badTypeList();
  }

}

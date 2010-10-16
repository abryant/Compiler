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
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.StatementExpressionAST;
import compiler.language.ast.statement.ExpressionStatementAST;
import compiler.language.ast.statement.StatementAST;
import compiler.language.parser.ParseType;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class StatementRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> BLOCK_PRODUCTION                  = new Production<ParseType>(BLOCK);
  private static final Production<ParseType> EMPTY_PRODUCTION                  = new Production<ParseType>(EMPTY_STATEMENT);
  private static final Production<ParseType> LOCAL_DECLARATION_PRODUCTION      = new Production<ParseType>(LOCAL_DECLARATION, SEMICOLON);
  private static final Production<ParseType> ASSIGNMENT_PRODUCTION             = new Production<ParseType>(ASSIGNMENT,        SEMICOLON);
  private static final Production<ParseType> IF_PRODUCTION                     = new Production<ParseType>(IF_STATEMENT);
  private static final Production<ParseType> WHILE_PRODUCTION                  = new Production<ParseType>(WHILE_STATEMENT);
  private static final Production<ParseType> DO_PRODUCTION                     = new Production<ParseType>(DO_STATEMENT);
  private static final Production<ParseType> FOR_PRODUCTION                    = new Production<ParseType>(FOR_STATEMENT);
  private static final Production<ParseType> FOR_EACH_PRODUCTION               = new Production<ParseType>(FOR_EACH_STATEMENT);
  private static final Production<ParseType> SWITCH_PRODUCTION                 = new Production<ParseType>(SWITCH_STATEMENT);
  private static final Production<ParseType> BREAK_PRODUCTION                  = new Production<ParseType>(BREAK_STATEMENT);
  private static final Production<ParseType> CONTINUE_PRODUCTION               = new Production<ParseType>(CONTINUE_STATEMENT);
  private static final Production<ParseType> FALLTHROUGH_PRODUCTION            = new Production<ParseType>(FALLTHROUGH_STATEMENT);
  private static final Production<ParseType> RETURN_PRODUCTION                 = new Production<ParseType>(RETURN_STATEMENT);
  private static final Production<ParseType> SYNCHRONIZED_PRODUCTION           = new Production<ParseType>(SYNCHRONIZED_STATEMENT);
  private static final Production<ParseType> THROW_PRODUCTION                  = new Production<ParseType>(THROW_STATEMENT);
  private static final Production<ParseType> TRY_PRODUCTION                    = new Production<ParseType>(TRY_STATEMENT);
  private static final Production<ParseType> INCREMENT_PRODUCTION              = new Production<ParseType>(INCREMENT,            SEMICOLON);
  private static final Production<ParseType> DECREMENT_PRODUCTION              = new Production<ParseType>(DECREMENT,            SEMICOLON);
  private static final Production<ParseType> STATEMENT_EXPRESSION_PRODUCTION   = new Production<ParseType>(STATEMENT_EXPRESSION, SEMICOLON);

  @SuppressWarnings("unchecked")
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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (BLOCK_PRODUCTION.equals(production)    || EMPTY_PRODUCTION.equals(production)        || IF_PRODUCTION.equals(production)          ||
        WHILE_PRODUCTION.equals(production)    || DO_PRODUCTION.equals(production)           || FOR_PRODUCTION.equals(production)         ||
        FOR_EACH_PRODUCTION.equals(production) || SWITCH_PRODUCTION.equals(production)       || TRY_PRODUCTION.equals(production)         ||
        BREAK_PRODUCTION.equals(production)    || CONTINUE_PRODUCTION.equals(production)     || FALLTHROUGH_PRODUCTION.equals(production) ||
        RETURN_PRODUCTION.equals(production)   || SYNCHRONIZED_PRODUCTION.equals(production) || THROW_PRODUCTION.equals(production))
    {
      // these productions only take one argument and all return a StatementAST, so return the argument
      return args[0];
    }

    if (LOCAL_DECLARATION_PRODUCTION.equals(production) || ASSIGNMENT_PRODUCTION.equals(production) ||
        INCREMENT_PRODUCTION.equals(production)         || DECREMENT_PRODUCTION.equals(production))
    {
      // add the semicolon's ParseInfo to the statement before returning it
      StatementAST statement = (StatementAST) args[0];
      statement.setParseInfo(ParseInfo.combine(statement.getParseInfo(), (ParseInfo) args[1]));
      return statement;
    }

    if (STATEMENT_EXPRESSION_PRODUCTION.equals(production))
    {
      StatementExpressionAST expression = (StatementExpressionAST) args[0];
      return new ExpressionStatementAST(expression, ParseInfo.combine(expression.getParseInfo(), (ParseInfo) args[1]));
    }

    throw badTypeList();
  }

}

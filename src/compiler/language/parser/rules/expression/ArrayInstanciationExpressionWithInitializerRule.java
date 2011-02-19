package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_INITIALIZER;
import static compiler.language.parser.ParseType.ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER;
import static compiler.language.parser.ParseType.DIMENSIONS;
import static compiler.language.parser.ParseType.NEW_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_NOT_ARRAY_TYPE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.ArrayInstanciationExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.DimensionsAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ArrayInstanciationExpressionWithInitializerRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(NEW_KEYWORD, TYPE_NOT_ARRAY_TYPE, DIMENSIONS, ARRAY_INITIALIZER);

  @SuppressWarnings("unchecked")
  public ArrayInstanciationExpressionWithInitializerRule()
  {
    super(ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER, PRODUCTION);
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
      TypeAST type = (TypeAST) args[1];
      DimensionsAST dimensions = (DimensionsAST) args[2];
      @SuppressWarnings("unchecked")
      ParseList<ExpressionAST> arrayInitializer = (ParseList<ExpressionAST>) args[3];
      return new ArrayInstanciationExpressionAST(type, new ExpressionAST[0], dimensions.getDimensions(), arrayInitializer.toArray(new ExpressionAST[0]),
                                              ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), dimensions.getParseInfo(),
                                                                arrayInitializer.getParseInfo()));
    }
    throw badTypeList();
  }

}

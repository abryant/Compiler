package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER;
import static compiler.language.parser.ParseType.DIMENSIONS;
import static compiler.language.parser.ParseType.DIMENSION_EXPRESSIONS;
import static compiler.language.parser.ParseType.NEW_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_NOT_ARRAY_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.ArrayInstanciationExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.misc.DimensionsAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ArrayInstanciationExpressionNoInitializerRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(NEW_KEYWORD, TYPE_NOT_ARRAY_TYPE, DIMENSION_EXPRESSIONS);
  private static final Production<ParseType> DIMENSIONS_PRODUCTION = new Production<ParseType>(NEW_KEYWORD, TYPE_NOT_ARRAY_TYPE, DIMENSION_EXPRESSIONS, DIMENSIONS);

  @SuppressWarnings("unchecked")
  public ArrayInstanciationExpressionNoInitializerRule()
  {
    super(ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER, PRODUCTION, DIMENSIONS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      @SuppressWarnings("unchecked")
      ParseList<ExpressionAST> dimensionExpressions = (ParseList<ExpressionAST>) args[2];
      return new ArrayInstanciationExpressionAST(type, dimensionExpressions.toArray(new ExpressionAST[0]), dimensionExpressions.size(), null,
                                              ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), dimensionExpressions.getParseInfo()));
    }
    if (DIMENSIONS_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[1];
      @SuppressWarnings("unchecked")
      ParseList<ExpressionAST> dimensionExpressions = (ParseList<ExpressionAST>) args[2];
      DimensionsAST dimensions = (DimensionsAST) args[3];
      int totalDimensions = dimensionExpressions.size() + dimensions.getDimensions();
      return new ArrayInstanciationExpressionAST(type, dimensionExpressions.toArray(new ExpressionAST[0]), totalDimensions, null,
                                              ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), dimensionExpressions.getParseInfo(), dimensions.getParseInfo()));
    }
    throw badTypeList();
  }

}

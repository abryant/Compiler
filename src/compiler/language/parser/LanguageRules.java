package compiler.language.parser;

import compiler.language.parser.rules.expression.AdditiveExpressionRule;
import compiler.language.parser.rules.expression.ArrayAccessExpressionRule;
import compiler.language.parser.rules.expression.ArrayInitializerRule;
import compiler.language.parser.rules.expression.ArrayInstanciationExpressionNoInitializerRule;
import compiler.language.parser.rules.expression.ArrayInstanciationExpressionWithInitializerRule;
import compiler.language.parser.rules.expression.BasicPrimaryRule;
import compiler.language.parser.rules.expression.BitwiseAndExpressionRule;
import compiler.language.parser.rules.expression.BitwiseOrExpressionRule;
import compiler.language.parser.rules.expression.BitwiseXorExpressionRule;
import compiler.language.parser.rules.expression.BooleanAndExpressionRule;
import compiler.language.parser.rules.expression.BooleanLiteralExpressionRule;
import compiler.language.parser.rules.expression.BooleanOrExpressionRule;
import compiler.language.parser.rules.expression.BooleanXorExpressionRule;
import compiler.language.parser.rules.expression.CastExpressionRule;
import compiler.language.parser.rules.expression.ClosureCreationExpressionRule;
import compiler.language.parser.rules.expression.DimensionExpressionRule;
import compiler.language.parser.rules.expression.DimensionExpressionsRule;
import compiler.language.parser.rules.expression.EqualityExpressionRule;
import compiler.language.parser.rules.expression.ExpressionListRule;
import compiler.language.parser.rules.expression.ExpressionNoTupleRule;
import compiler.language.parser.rules.expression.ExpressionRule;
import compiler.language.parser.rules.expression.FieldAccessExpressionNotQNameRule;
import compiler.language.parser.rules.expression.FieldAccessExpressionRule;
import compiler.language.parser.rules.expression.InlineIfExpressionRule;
import compiler.language.parser.rules.expression.InstanciationExpressionRule;
import compiler.language.parser.rules.expression.MethodCallExpressionRule;
import compiler.language.parser.rules.expression.MultiplicativeExpressionRule;
import compiler.language.parser.rules.expression.PrimaryNoTrailingDimensionsNotQNameRule;
import compiler.language.parser.rules.expression.PrimaryRule;
import compiler.language.parser.rules.expression.QNameExpressionRule;
import compiler.language.parser.rules.expression.QNameOrLessThanExpressionRule;
import compiler.language.parser.rules.expression.RelationalExpressionLessThanQNameRule;
import compiler.language.parser.rules.expression.RelationalExpressionNotLessThanQNameRule;
import compiler.language.parser.rules.expression.ShiftExpressionRule;
import compiler.language.parser.rules.expression.StatementExpressionRule;
import compiler.language.parser.rules.expression.SuperAccessExpressionRule;
import compiler.language.parser.rules.expression.ThisAccessExpressionRule;
import compiler.language.parser.rules.expression.TupleExpressionRule;
import compiler.language.parser.rules.expression.TupleIndexExpressionRule;
import compiler.language.parser.rules.expression.UnaryExpressionRule;
import compiler.language.parser.rules.member.AccessSpecifierRule;
import compiler.language.parser.rules.member.ConstructorRule;
import compiler.language.parser.rules.member.FieldRule;
import compiler.language.parser.rules.member.MemberHeaderRule;
import compiler.language.parser.rules.member.MemberListRule;
import compiler.language.parser.rules.member.MemberRule;
import compiler.language.parser.rules.member.MethodRule;
import compiler.language.parser.rules.member.ModifierRule;
import compiler.language.parser.rules.member.ModifiersRule;
import compiler.language.parser.rules.member.NativeSpecifierRule;
import compiler.language.parser.rules.member.PropertyRule;
import compiler.language.parser.rules.member.SinceSpecifierRule;
import compiler.language.parser.rules.member.StaticInitializerRule;
import compiler.language.parser.rules.misc.ArgumentListRule;
import compiler.language.parser.rules.misc.ArgumentRule;
import compiler.language.parser.rules.misc.ArgumentsRule;
import compiler.language.parser.rules.misc.AssigneeListRule;
import compiler.language.parser.rules.misc.AssigneeRule;
import compiler.language.parser.rules.misc.AssignmentOperatorRule;
import compiler.language.parser.rules.misc.DeclarationAssigneeListRule;
import compiler.language.parser.rules.misc.DeclarationAssigneeRule;
import compiler.language.parser.rules.misc.DimensionsRule;
import compiler.language.parser.rules.misc.NestedQNameListRule;
import compiler.language.parser.rules.misc.ParameterListRule;
import compiler.language.parser.rules.misc.ParameterRule;
import compiler.language.parser.rules.misc.ParametersRule;
import compiler.language.parser.rules.misc.QNameListRule;
import compiler.language.parser.rules.misc.QNameRule;
import compiler.language.parser.rules.misc.ThrowsClauseRule;
import compiler.language.parser.rules.misc.ThrowsListRule;
import compiler.language.parser.rules.misc.VersionNumberRule;
import compiler.language.parser.rules.statement.AssignmentRule;
import compiler.language.parser.rules.statement.BlockRule;
import compiler.language.parser.rules.statement.ElseClauseRule;
import compiler.language.parser.rules.statement.ElseIfClauseRule;
import compiler.language.parser.rules.statement.ElseIfClausesRule;
import compiler.language.parser.rules.statement.EmptyStatementRule;
import compiler.language.parser.rules.statement.IfStatementRule;
import compiler.language.parser.rules.statement.LocalDeclarationRule;
import compiler.language.parser.rules.statement.OptionalBlockRule;
import compiler.language.parser.rules.statement.StatementRule;
import compiler.language.parser.rules.statement.StatementsRule;
import compiler.language.parser.rules.topLevel.CompilationUnitRule;
import compiler.language.parser.rules.topLevel.ImportDeclarationRule;
import compiler.language.parser.rules.topLevel.PackageDeclarationRule;
import compiler.language.parser.rules.topLevel.TypeDefinitionRule;
import compiler.language.parser.rules.type.ArrayTypeRule;
import compiler.language.parser.rules.type.BooleanTypeRule;
import compiler.language.parser.rules.type.CharacterTypeRule;
import compiler.language.parser.rules.type.ClosureTypeRule;
import compiler.language.parser.rules.type.FloatingTypeRule;
import compiler.language.parser.rules.type.IntegerTypeRule;
import compiler.language.parser.rules.type.PointerTypeDoubleRAngleRule;
import compiler.language.parser.rules.type.PointerTypeNoTrailingParamsNotQNameRule;
import compiler.language.parser.rules.type.PointerTypeNotQNameRule;
import compiler.language.parser.rules.type.PointerTypeRAngleRule;
import compiler.language.parser.rules.type.PointerTypeRule;
import compiler.language.parser.rules.type.PointerTypeTrailingParamsDoubleRAngleRule;
import compiler.language.parser.rules.type.PointerTypeTrailingParamsRAngleRule;
import compiler.language.parser.rules.type.PointerTypeTrailingParamsRule;
import compiler.language.parser.rules.type.PointerTypeTrailingParamsTripleRAngleRule;
import compiler.language.parser.rules.type.PointerTypeTripleRAngleRule;
import compiler.language.parser.rules.type.PrimitiveTypeRule;
import compiler.language.parser.rules.type.TupleTypeNotQNameListRule;
import compiler.language.parser.rules.type.TupleTypeRule;
import compiler.language.parser.rules.type.TypeArgumentListRAngleRule;
import compiler.language.parser.rules.type.TypeArgumentListRule;
import compiler.language.parser.rules.type.TypeArgumentRAngleRule;
import compiler.language.parser.rules.type.TypeArgumentRule;
import compiler.language.parser.rules.type.TypeArgumentsRule;
import compiler.language.parser.rules.type.TypeDoubleRAngleRule;
import compiler.language.parser.rules.type.TypeListNotQNameListRule;
import compiler.language.parser.rules.type.TypeListRule;
import compiler.language.parser.rules.type.TypeNotArrayTypeRule;
import compiler.language.parser.rules.type.TypeNotPointerTypeNotTupleTypeRule;
import compiler.language.parser.rules.type.TypeNotQNameListRule;
import compiler.language.parser.rules.type.TypeNotQNameRule;
import compiler.language.parser.rules.type.TypeParameterDoubleRAngleRule;
import compiler.language.parser.rules.type.TypeParameterListDoubleRAngleRule;
import compiler.language.parser.rules.type.TypeParameterListRAngleRule;
import compiler.language.parser.rules.type.TypeParameterListRule;
import compiler.language.parser.rules.type.TypeParameterListTripleRAngleRule;
import compiler.language.parser.rules.type.TypeParameterNotQNameListRule;
import compiler.language.parser.rules.type.TypeParameterRAngleRule;
import compiler.language.parser.rules.type.TypeParameterTripleRAngleRule;
import compiler.language.parser.rules.type.TypeParametersRule;
import compiler.language.parser.rules.type.TypeRAngleRule;
import compiler.language.parser.rules.type.TypeRule;
import compiler.language.parser.rules.type.TypeTripleRAngleRule;
import compiler.language.parser.rules.type.VoidTypeRule;
import compiler.language.parser.rules.type.WildcardTypeParameterDoubleRAngleRule;
import compiler.language.parser.rules.type.WildcardTypeParameterRAngleRule;
import compiler.language.parser.rules.type.WildcardTypeParameterRule;
import compiler.language.parser.rules.type.WildcardTypeParameterTripleRAngleRule;
import compiler.language.parser.rules.typeDefinition.ClassDefinitionRule;
import compiler.language.parser.rules.typeDefinition.ClassExtendsClauseRule;
import compiler.language.parser.rules.typeDefinition.EnumConstantListRule;
import compiler.language.parser.rules.typeDefinition.EnumConstantRule;
import compiler.language.parser.rules.typeDefinition.EnumConstantsRule;
import compiler.language.parser.rules.typeDefinition.EnumDefinitionRule;
import compiler.language.parser.rules.typeDefinition.ImplementsClauseRule;
import compiler.language.parser.rules.typeDefinition.InterfaceDefinitionRule;
import compiler.language.parser.rules.typeDefinition.InterfaceExtendsClauseRule;
import compiler.language.parser.rules.typeDefinition.InterfaceListRule;
import compiler.parser.Rule;
import compiler.parser.lalr.LALRRuleSet;

/*
 * Created on 30 Jun 2010
 */

/**
 * Manages the set of rules used by the language parser.
 *
 * @author Anthony Bryant
 */
public class LanguageRules
{

  private static final Rule startRule = new CompilationUnitRule();
  private static final Rule[] rules = new Rule[]
  {
    // top level
    // startRule (does not need to be included here): new CompilationUnitRule(),
    new ImportDeclarationRule(),
    new PackageDeclarationRule(),
    new TypeDefinitionRule(),

    // type definitions
    new ClassDefinitionRule(),
    new ClassExtendsClauseRule(),
    new EnumConstantListRule(),
    new EnumConstantRule(),
    new EnumConstantsRule(),
    new EnumDefinitionRule(),
    new ImplementsClauseRule(),
    new InterfaceDefinitionRule(),
    new InterfaceExtendsClauseRule(),
    new InterfaceListRule(),

    // members
    new AccessSpecifierRule(),
    new ConstructorRule(),
    new FieldRule(),
    new MemberHeaderRule(),
    new MemberListRule(),
    new MemberRule(),
    new MethodRule(),
    new ModifierRule(),
    new ModifiersRule(),
    new NativeSpecifierRule(),
    new PropertyRule(),
    new SinceSpecifierRule(),
    new StaticInitializerRule(),

    // statements
    new AssignmentRule(),
    new BlockRule(),
    new ElseClauseRule(),
    new ElseIfClauseRule(),
    new ElseIfClausesRule(),
    new EmptyStatementRule(),
    new IfStatementRule(),
    new LocalDeclarationRule(),
    new OptionalBlockRule(),
    new StatementRule(),
    new StatementsRule(),

    // expressions
    new AdditiveExpressionRule(),
    new ArrayAccessExpressionRule(),
    new ArrayInitializerRule(),
    new ArrayInstanciationExpressionNoInitializerRule(),
    new ArrayInstanciationExpressionWithInitializerRule(),
    new BasicPrimaryRule(),
    new BitwiseAndExpressionRule(),
    new BitwiseOrExpressionRule(),
    new BitwiseXorExpressionRule(),
    new BooleanAndExpressionRule(),
    new BooleanLiteralExpressionRule(),
    new BooleanOrExpressionRule(),
    new BooleanXorExpressionRule(),
    new CastExpressionRule(),
    new ClosureCreationExpressionRule(),
    new DimensionExpressionRule(),
    new DimensionExpressionsRule(),
    new EqualityExpressionRule(),
    new ExpressionListRule(),
    new ExpressionNoTupleRule(),
    new ExpressionRule(),
    new FieldAccessExpressionNotQNameRule(),
    new FieldAccessExpressionRule(),
    new InlineIfExpressionRule(),
    new InstanciationExpressionRule(),
    new MethodCallExpressionRule(),
    new MultiplicativeExpressionRule(),
    new PrimaryNoTrailingDimensionsNotQNameRule(),
    new PrimaryRule(),
    new QNameExpressionRule(),
    new QNameOrLessThanExpressionRule(),
    new RelationalExpressionLessThanQNameRule(),
    new RelationalExpressionNotLessThanQNameRule(),
    new ShiftExpressionRule(),
    new StatementExpressionRule(),
    new SuperAccessExpressionRule(),
    new ThisAccessExpressionRule(),
    new TupleExpressionRule(),
    new TupleIndexExpressionRule(),
    new UnaryExpressionRule(),

    // types
    new ArrayTypeRule(),
    new BooleanTypeRule(),
    new CharacterTypeRule(),
    new ClosureTypeRule(),
    new FloatingTypeRule(),
    new IntegerTypeRule(),
    new PointerTypeDoubleRAngleRule(),
    new PointerTypeNotQNameRule(),
    new PointerTypeNoTrailingParamsNotQNameRule(),
    new PointerTypeRAngleRule(),
    new PointerTypeRule(),
    new PointerTypeTrailingParamsDoubleRAngleRule(),
    new PointerTypeTrailingParamsRAngleRule(),
    new PointerTypeTrailingParamsRule(),
    new PointerTypeTrailingParamsTripleRAngleRule(),
    new PointerTypeTripleRAngleRule(),
    new PrimitiveTypeRule(),
    new TupleTypeNotQNameListRule(),
    new TupleTypeRule(),
    new TypeArgumentListRAngleRule(),
    new TypeArgumentListRule(),
    new TypeArgumentRAngleRule(),
    new TypeArgumentRule(),
    new TypeArgumentsRule(),
    new TypeDoubleRAngleRule(),
    new TypeListNotQNameListRule(),
    new TypeListRule(),
    new TypeNotArrayTypeRule(),
    new TypeNotPointerTypeNotTupleTypeRule(),
    new TypeNotQNameListRule(),
    new TypeNotQNameRule(),
    new TypeParameterDoubleRAngleRule(),
    new TypeParameterListDoubleRAngleRule(),
    new TypeParameterListRAngleRule(),
    new TypeParameterListRule(),
    new TypeParameterListTripleRAngleRule(),
    new TypeParameterNotQNameListRule(),
    new TypeParameterRAngleRule(),
    new TypeParametersRule(),
    new TypeParameterTripleRAngleRule(),
    new TypeRAngleRule(),
    new TypeRule(),
    new TypeTripleRAngleRule(),
    new VoidTypeRule(),
    new WildcardTypeParameterDoubleRAngleRule(),
    new WildcardTypeParameterRAngleRule(),
    new WildcardTypeParameterRule(),
    new WildcardTypeParameterTripleRAngleRule(),

    // miscellaneous
    new ArgumentListRule(),
    new ArgumentRule(),
    new ArgumentsRule(),
    new AssigneeListRule(),
    new AssigneeRule(),
    new AssignmentOperatorRule(),
    new DeclarationAssigneeListRule(),
    new DeclarationAssigneeRule(),
    new DimensionsRule(),
    new NestedQNameListRule(),
    new ParameterListRule(),
    new ParameterRule(),
    new ParametersRule(),
    new QNameListRule(),
    new QNameRule(),
    new ThrowsClauseRule(),
    new ThrowsListRule(),
    new VersionNumberRule(),
  };

  /**
   * @return an LALRRuleSet containing all of the rules needed to parse this language
   */
  public static LALRRuleSet getRuleSet()
  {
    LALRRuleSet ruleSet = new LALRRuleSet();
    ruleSet.addStartRule(startRule);
    for (Rule rule : rules)
    {
      ruleSet.addRule(rule);
    }
    return ruleSet;
  }
}

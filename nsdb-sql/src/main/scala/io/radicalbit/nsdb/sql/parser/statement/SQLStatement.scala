package io.radicalbit.nsdb.sql.parser.statement

sealed trait SQLStatementType
case object Select extends SQLStatementType

sealed trait SelectedFields
case object AllFields                       extends SelectedFields
case class ListFields(fields: List[String]) extends SelectedFields

sealed trait Expression
case class Condition(expression: Expression)
case class UnaryLogicalExpression(expression: Expression, operator: SingleLogicalOperator) extends Expression
case class TupledLogicalExpression(expression1: Expression, operator: TupledLogicalOperator, expression2: Expression)
    extends Expression
case class ComparisonExpression[T](dimension: String, comparison: ComparisonOperator, value: T) extends Expression
case class RangeExpression[T](dimension: String, value1: T, value2: T)                          extends Expression

sealed trait LogicalOperator
sealed trait SingleLogicalOperator extends LogicalOperator
case object NotOperator            extends SingleLogicalOperator
sealed trait TupledLogicalOperator extends LogicalOperator
case object AndOperator            extends TupledLogicalOperator
case object OrOperator             extends TupledLogicalOperator

sealed trait ComparisonOperator
case object GreaterThanOperator      extends ComparisonOperator
case object GreaterOrEqualToOperator extends ComparisonOperator
case object LessThanOperator         extends ComparisonOperator
case object LessOrEqualToOperator    extends ComparisonOperator

sealed trait OrderOperator {
  def dimension: String
}
case class AscOrderOperator(override val dimension: String)  extends OrderOperator
case class DescOrderOperator(override val dimension: String) extends OrderOperator

case class LimitOperator(value: Int)

case class SelectSQLStatement(metric: String,
                              fields: SelectedFields,
                              condition: Option[Condition] = None,
                              order: Option[OrderOperator] = None,
                              limit: Option[LimitOperator] = None)
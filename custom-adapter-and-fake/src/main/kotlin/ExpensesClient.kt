import actions.GetMyExpenses
import adapter.ExpensesSystem

/**
 * This is a simple domain wrapper for our adapter.ExpensesSystem adapter
 */
class ExpensesClient(private val expensesSystem: ExpensesSystem) {
    fun countExpenses(name: String) = expensesSystem(GetMyExpenses(name)).expenses.size
}

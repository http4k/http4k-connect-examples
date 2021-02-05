package extending_http4k_connect

import actions.AddExpense
import actions.ExpenseReport
import actions.ExpensesAction
import actions.GetMyExpenses
import adapter.ExpensesSystem
import model.Expense
import org.http4k.connect.storage.InMemory
import org.http4k.connect.storage.Storage

class StubExpensesSystem(private val storage: Storage<List<Expense>> = Storage.InMemory()) : ExpensesSystem {
    override fun <R : Any> invoke(action: ExpensesAction<R>): R = when (action) {
        is AddExpense -> addExpense(action, storage) as R
        is GetMyExpenses -> getMyExpenses(action, storage) as R
        else -> error("Unknown action")
    }
}

private fun addExpense(action: AddExpense, storage: Storage<List<Expense>>): Expense {
    val expense = Expense(0, action.name, action.cost)
    storage[expense.name] = (storage[expense.name] ?: emptyList()) + expense
    return expense
}

private fun getMyExpenses(action: GetMyExpenses, storage: Storage<List<Expense>>) =
    ExpenseReport(action.name, storage[action.name] ?: emptyList())

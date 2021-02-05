package extending_http4k_connect

import ExpensesClient
import actions.ExpenseReport
import actions.GetMyExpenses
import actions.getMyExpenses
import adapter.ExpensesSystem
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import io.mockk.every
import io.mockk.mockk
import model.Expense
import org.junit.jupiter.api.Test

/**
 * These tests show mocking FakeExpensesSystem and the ExpensesClient
 */
class ExpensesSystemAdapterMockTests {

    @Test
    fun `can count expenses with client and mock - using convenience method`() {
        val mockExpensesSystem = mockk<ExpensesSystem>()

        every { mockExpensesSystem.getMyExpenses("Bob") } returns
            ExpenseReport("Bob", listOf(Expense(1, "Expense 0", 66)))

        assertThat(ExpensesClient(mockExpensesSystem).countExpenses("Bob"), equalTo(1))
    }

    @Test
    fun `can count expenses with client and mock using invoke()`() {
        val mockExpensesSystem = mockk<ExpensesSystem>()

        every { mockExpensesSystem(any<GetMyExpenses>()) } returns
            ExpenseReport("Bob", listOf(Expense(1, "Expense 0", 66)))

        assertThat(ExpensesClient(mockExpensesSystem).countExpenses("Bob"), equalTo(1))
    }
}

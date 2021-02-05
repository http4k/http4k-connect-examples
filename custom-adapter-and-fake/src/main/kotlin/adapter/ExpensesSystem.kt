package adapter

import actions.ExpensesAction
import org.http4k.connect.Http4kConnectAdapter

/**
 * The main system interface. The Annotation is used by Kapt
 * to generate the friendly extension functions.
 */
@Http4kConnectAdapter
interface ExpensesSystem {
    operator fun <R : Any> invoke(action: ExpensesAction<R>): R

    companion object
}


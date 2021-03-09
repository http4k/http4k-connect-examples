package actions

import org.http4k.connect.github.action.GitHubAction
import org.http4k.connect.kClass
import org.http4k.core.Method.GET
import org.http4k.core.Request
import java.time.Instant

data class GetRepo(val owner: String, val repo: String) : GitHubAction<OrgRepo>(kClass()) {
    override fun toRequest() = Request(GET, "/repos/$owner/$repo")
}

data class OrgRepo(val name: String, val created_at: Instant)

package with_connect

class RecordingGitHubApi(private val delegate: GitHubApi) : GitHubApi {
    val recorded = mutableListOf<GitHubApiAction<*>>()
    override fun <R : Any> invoke(action: GitHubApiAction<R>): R {
        recorded += action
        return delegate(action)
    }
}

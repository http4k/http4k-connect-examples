@file:Suppress("unused")

package addressbook.user

import org.http4k.serverless.ApiGatewayV2LambdaFunction

/**
 * Bind the plugin to an AWS Serverless function
 */
class PlugInLambda : ApiGatewayV2LambdaFunction(UserPlugin())

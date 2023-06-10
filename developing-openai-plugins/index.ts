import * as pulumi from "@pulumi/pulumi";
import * as aws from "@pulumi/aws";
import {RolePolicyAttachment} from "@pulumi/aws/iam";

const defaultRole = new aws.iam.Role("http4k-openai-plugin-default-role", {
    assumeRolePolicy: `{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
`
});

new RolePolicyAttachment("http4k-openai-plugin-default-role-policy",
    {
        role: defaultRole,
        policyArn: aws.iam.ManagedPolicies.AWSLambdaBasicExecutionRole
    });

const api = new aws.apigatewayv2.Api("http4k-openai-plugin-api", {
    protocolType: "HTTP"
});

const logGroupApi = new aws.cloudwatch.LogGroup("http4k-openai-plugin-api-route", {
    name: "http4k-openai-plugin",
});

const apiDefaultStage = new aws.apigatewayv2.Stage("default", {
    apiId: api.id,
    autoDeploy: true,
    name: "$default",
    accessLogSettings: {
        destinationArn: logGroupApi.arn,
        format: `{"requestId": "$context.requestId", "requestTime": "$context.requestTime", "httpMethod": "$context.httpMethod", "httpPath": "$context.path", "status": "$context.status", "integrationError": "$context.integrationErrorMessage"}`
    }
})

const lambdaFunction = new aws.lambda.Function("http4k-openai-plugin", {
    code: new pulumi.asset.FileArchive("build/distributions/developing-openai-plugins.zip"),
    handler: "addressbook.noauth.PlugInLambda",
    role: defaultRole.arn,
    memorySize: 256,
    runtime: "java11",
    timeout: 30,
    environment: {
        variables: {
            "PLUGIN_BASE_URL": apiDefaultStage.invokeUrl
        }
    }
});

const apiGatewayPermission = new aws.lambda.Permission("http4k-openai-plugin-gateway-permission", {
    action: "lambda:InvokeFunction",
    "function": lambdaFunction.name,
    principal: "apigateway.amazonaws.com"
});

const lambdaIntegration = new aws.apigatewayv2.Integration("http4k-openai-plugin-api-lambda-integration", {
    apiId: api.id,
    integrationType: "AWS_PROXY",
    integrationUri: lambdaFunction.arn,
    payloadFormatVersion: "2.0"
});

let serverlessHttp4kApiRoute = "http4k-openai-plugin";
const apiDefaultRole = new aws.apigatewayv2.Route(serverlessHttp4kApiRoute + "-api-route", {
    apiId: api.id,
    routeKey: `$default`,
    target: pulumi.interpolate `integrations/${lambdaIntegration.id}`
});

export const publishedUrl = apiDefaultStage.invokeUrl;

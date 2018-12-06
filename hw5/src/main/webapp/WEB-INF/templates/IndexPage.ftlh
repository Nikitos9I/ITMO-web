<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="confirmation" type="java.lang.String" -->

<#import "common.ftl" as c>

<@c.page>
    <#if message??>
        <p>${message}</p>
    </#if>

    <#if confirmation??>
        <a href="/confirm?secret=">
            Confirm your email
        </a>
    </#if>

    <h1>Welcome to the index page</h1>
</@c.page>

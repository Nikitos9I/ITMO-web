<#import "./common.ftlh" as c>

<@c.page>
<div class="user">
    <#if noSuchUser??>
        <div class="login">No such user</div>
    <#else>
        <div class="id"><b>Id</b>: ${user.id}</div>
        <div class="login"><b>Login</b>: ${user.login}</div>
        <div class="creation"><b>Creation</b>: ${user.creationTime}</div>
    </#if>
</div>
</@c.page>
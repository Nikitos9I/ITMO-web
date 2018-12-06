<#import "./common.ftlh" as c>

<@c.page>
<div class="datatable">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Created At</th>
            <th>Disabled</th>
        </tr>
        </thead>
        <tbody>
    <#list users as user>
    <tr>
        <td><a href="/user/${user.id}">${user.id}</a></td>
        <td>${user.login}</td>
        <td>${user.creationTime}</td>
        <#if user.getDisabled()>
            <td>
                <form method="post" action="/users">
                    <input type="hidden" name="userId" value=${user.id}>
                    <input type="submit" value="Enable">
                </form>
            </td>
        <#else>
            <td>
                <form method="post" action="/users">
                    <input type="hidden" name="userId" value=${user.id}>
                    <input type="submit" value="Disable">
                </form>
            </td>
        </#if>
    </tr>
    </#list>
        </tbody>
    </table>
</div>
</@c.page>

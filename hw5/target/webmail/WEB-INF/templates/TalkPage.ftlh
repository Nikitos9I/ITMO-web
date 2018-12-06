<#import "common.ftl" as c>

<@c.page>
<div class="datatable">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>From</th>
            <th>To</th>
            <th>Text</th>
        </tr>
        </thead>
        <tbody>
        <#list messages as message>
        <tr>
            <td style="width:5rem; overflow: hidden">
                <div class="block">${message.sourceUserName}</div>
            </td>
            <td style="width:5rem; overflow: hidden">
                <div class="block">${message.targetUserName}</div>
            </td>
            <td>
                <div class="block">${message.text}</div>
            </td>
        </tr>
        </#list>
    </table>

    <div class="form-box" style="margin-top:1.5rem">
        <div class="header">Enter your message</div>
        <div class="body">
            <form method="post" action="/talk">
                <input type="hidden" name="action" value="sendMessage">
                <div class="field">
                    <div class="name">
                        <label for="login-email">Target user</label>
                    </div>
                    <div class="value">
                        <input id="login-email" name="target-user"/>
                    </div>
                </div>
                <#if text??>
                    <textarea class="news-text" rows="8" name="text-message">${text}</textarea>
                <#else>
                    <textarea class="news-text" rows="8" name="text-message"></textarea>
                </#if>
                <#if error??>
                    <div class="error" style="margin-top: 1rem">
                        ${error}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Send Message">
                </div>
            </form>
        </div>
    </div>
</div>
</@c.page>
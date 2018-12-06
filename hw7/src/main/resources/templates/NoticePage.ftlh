<#import "/spring.ftl" as spring>
<#import "./common.ftlh" as c>

<#macro error field>
    <@spring.bind field/>
    <#list spring.status.errorMessages as error>
        <div class="error">${error}</div>
        <#break/>
    </#list>
</#macro>

<@c.page>
<div class="add-notice form-box">
    <div class="header">Add notice</div>
    <div class="body">
        <form method="post" action="/notice">
            <div class="field">
                <div class="name">
                    <label for="content">Content</label>
                </div>
                <div class="value">
                    <textarea id="content" name="content" rows="10"></textarea>
                </div>
                <@error 'notice.content'/>
            </div>

            <div class="button-field">
                <input type="submit" value="Send">
            </div>

        </form>
    </div>
</div>
</@c.page>
